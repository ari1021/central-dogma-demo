package com.centraldogma.demo.util

import com.centraldogma.demo.databind.parseFromJson
import com.linecorp.centraldogma.client.CentralDogma
import com.linecorp.centraldogma.client.Watcher
import com.linecorp.centraldogma.common.Query
import mu.KotlinLogging
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

class CentralDogmaClient<T : Any>(
        centralDogma: CentralDogma,
        projectName: String,
        repositoryName: String,
        jsonPath: String,
        dataType: KClass<T>
) {
    companion object {
        private val log = KotlinLogging.logger(CentralDogmaClient::class.java.name)
    }

    private val watcher: Watcher<T> = centralDogma.fileWatcher(
            projectName,
            repositoryName,
            Query.ofJson(jsonPath),
    ) { jsonNode ->
        jsonNode.parseFromJson(dataType)
    }.also { watcher ->
        try {
            watcher.awaitInitialValue(30, TimeUnit.SECONDS)
        } catch (e: Exception) {
            log.error(e) {
                "Failed to load from Central Dogma. " +
                        "CentralDogma projectName: $projectName, repositoryName: $repositoryName, " +
                        "path: $jsonPath, dataType: $dataType"
            }
            throw e
        }
    }

    val latestValue: T
        get() = this.watcher.latestValue()!!
}
