package com.centraldogma.demo.databind

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import kotlin.reflect.KClass

object MyObjectMapper {
    val objectMapper = ObjectMapper().registerKotlinModule()
}

fun <T : Any> JsonNode.parseFromJson(type: KClass<T>): T =
        MyObjectMapper.objectMapper.readerFor(type.java).readValue(this)
