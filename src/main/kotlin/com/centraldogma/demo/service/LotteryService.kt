package com.centraldogma.demo.service

import com.centraldogma.demo.util.CentralDogmaClient
import com.linecorp.centraldogma.client.CentralDogma
import mu.KotlinLogging
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class LotteryService(centralDogma: CentralDogma) {
    companion object {
        private val log = KotlinLogging.logger(LotteryService::class.java.name)
    }

    private val centralDogmaClient = CentralDogmaClient(
            centralDogma = centralDogma,
            projectName = "demo",
            repositoryName = "lottery",
            jsonPath = "/chance.json",
            dataType = ChanceConfig::class
    )

    private val latestChanceConfig: ChanceConfig
        get() = centralDogmaClient.latestValue

    fun draw(): Boolean {
        val chanceConfig = latestChanceConfig
        log.info { "The chance of winning is ${chanceConfig.chance}%" }
        if (!chanceConfig.isValidChance) {
            log.error { "invalid chance. chance: ${chanceConfig.chance}" }
            throw Exception()
        }
        val random = Random.nextLong(0, 100)
        return random <= chanceConfig.chance
    }

    data class ChanceConfig(val chance: Long) {
        val isValidChance: Boolean = chance in 0..100
    }
}