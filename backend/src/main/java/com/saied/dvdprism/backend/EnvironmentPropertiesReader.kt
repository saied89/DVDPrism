package com.saied.dvdprism.backend

import com.fasterxml.jackson.databind.ObjectMapper


private const val ENVIROMENT_LABEL = "environment"
private const val APIKEY_LABEL = "omdb-apiKey"
class EnvironmentPropertiesReader(
    private val objectMapper: ObjectMapper,
    private val fileName: String = "rest-client.private.env.json"
) {

    fun getOmdbApiKey(): String {
        val propertiesJson = javaClass.classLoader.getResource(fileName).readText()
        val nodeTree = objectMapper.readTree(propertiesJson)
        return nodeTree[ENVIROMENT_LABEL][APIKEY_LABEL].asText()
    }
}