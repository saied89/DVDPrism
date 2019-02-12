package com.saied.dvdprism.backend.model

import com.saied.dvdprism.backend.di.jsonModule
import com.saied.dvdprism.common.model.OmdbType
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.get
import org.koin.test.KoinTest

internal class OmdbSearchTest : KoinTest {

    @Test
    fun `is parsed correctly`() {
        startKoin(listOf(jsonModule))
        val content = javaClass.classLoader.getResource("searchResult.json").readText()
        val objectMapper: ObjectMapper = get()
        val res = objectMapper.readValue(content, OmdbSearch::class.java)

        assertEquals(10, res.search.size)
        assertEquals(OmdbType.MOVIE, res.search[0].type)
        assertEquals(OmdbType.SERIES, res.search[1].type)
        assertTrue(res.response)
        assertEquals(448, res.totalResults)
        assertEquals("tt0077651", res.search[0].imdbID)
        assertEquals("Halloween", res.search[0].title)
        assertEquals(1978, res.search[0].year)
        assertTrue(res.search[0].poster.isNotEmpty())
        assertTrue(res.search[0].poster.isNotEmpty())
        stopKoin()
    }
}