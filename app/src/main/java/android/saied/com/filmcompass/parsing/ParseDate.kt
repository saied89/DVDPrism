package android.saied.com.filmcompass.parsing

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private const val METACRITIC_DATE_PATTERN = "MMMM d, yyyy"

private val dateFormat: DateFormat = SimpleDateFormat(METACRITIC_DATE_PATTERN, Locale.US)

fun parseDate(dateString: String): Date = dateFormat.parse(dateString)

fun formatDate(date: Date): String = dateFormat.format(date)