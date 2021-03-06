package com.saied.dvdprism.common.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class ParseDateKtTest {

    @Test
    fun `date is parsed correctly`() {
        val dateStr = "September 30, 2018"
        val date = parseDate(dateStr)

        val res = formatDate(date)

        assertEquals(dateStr, res)
    }
}