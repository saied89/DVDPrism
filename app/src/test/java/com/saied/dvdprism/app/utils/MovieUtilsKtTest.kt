package com.saied.dvdprism.app.utils

import com.saied.dvdprism.common.model.Movie
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MovieUtilsKtTest {
    @Test
    fun `score strings are generated correctly`() {
        val testMovie1 = Movie("", 0L, "", 36, 67, "")
        val testMovie2 = Movie("", 0L, "", null, 67, "")
        val testMovie3 = Movie("", 0L, "", null, null, "")

        assertEquals("36", testMovie1.metaScoreString)
        assertEquals("TBD", testMovie2.metaScoreString)

        assertEquals("6.7", testMovie2.userScoreString)
        assertEquals("TBD", testMovie3.userScoreString)
    }
}