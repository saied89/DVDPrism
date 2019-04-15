package com.saied.dvdprism.common.model

import java.text.SimpleDateFormat
import java.util.*

private const val METACRITIC_DATE_PATTERN = "MMMM d, yyyy"

private val dateFormat = SimpleDateFormat(METACRITIC_DATE_PATTERN, Locale.US)

fun parseDate(dateString: String): Date = dateFormat.parse(dateString)

fun formatDate(date: Date): String = dateFormat.format(date)