package com.saied.dvdprism.backend.fetcher

import com.saied.dvdprism.backend.fetchHtml
import com.saied.dvdprism.common.model.Movie
import com.saied.dvdprism.common.model.parseDate
import arrow.core.Try
import com.google.common.annotations.VisibleForTesting
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

const val metacriticLatestUrl = "https://www.metacritic.com/browse/dvds/release-date/new-releases/date"
const val metacriticUpcomingUrl = "https://www.metacritic.com/browse/dvds/release-date/coming-soon/date"

class MovieFetcher(private val httpClient: HttpClient, val sources: List<MovieSource> = sourceList) {

    suspend fun fetchMovies(): Try<List<Movie>> =
        withContext(Dispatchers.IO) {
            try {
                val res: ArrayList<Movie> = arrayListOf()
                sources.forEach { movieSource ->
                    val htmlStrTry = fetchHtml(movieSource.url, httpClient)
                    val htmlStr: String = //TODO check if there is a better way to propagate try
                        when (htmlStrTry) {
                            is Try.Success -> htmlStrTry.value
                            is Try.Failure -> throw htmlStrTry.exception
                        }
                    res.addAll(movieSource.parse(htmlStr))
                }
                Try.just(res)
            } catch (exp: Exception) {
                Try.raise<List<Movie>>(exp)
            }
        }
}

private val sourceList = listOf(
    MovieSource(metacriticLatestUrl, ::parseMetacriticHtml),
    MovieSource(metacriticUpcomingUrl, ::parseMetacriticHtml)
)

@VisibleForTesting
internal fun parseMetacriticHtml(htmlStr: String): List<Movie> =
    Jsoup.parse(htmlStr)
        .body()
        .select("table.clamp-list tbody tr:not(.spacer)")
        .map { element ->
            val title = element.select(".clamp-summary-wrap a.title h3").html().replace("&amp;", "&")
            val dateStr = element.select(".clamp-summary-wrap .clamp-details span:nth-child(2)").html()
            val date = parseDate(dateStr).time
            val posterUrl = element.select(".clamp-image-wrap a img").first().absUrl("src")
            val metaScore = element.select(".clamp-summary-wrap .browse-score-clamp .clamp-metascore a div")
                .first()
                .html()
                .toIntOrNull()
            val userScore = element.select(".clamp-summary-wrap .browse-score-clamp .clamp-userscore a div")
                .first()
                .html()
                .toFloatOrNull()
                ?.let {
                    (it * 10).toInt()
                }
            val description = element.select(".summary").html()
            Movie(title, date, posterUrl, metaScore, userScore, description)
        }


class MovieSource(val url: String, val parse: (String) -> List<Movie>)


