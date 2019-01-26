package android.saied.com.backend.fetcher

import android.saied.com.backend.fetchHtml
import android.saied.com.common.model.Movie
import android.saied.com.common.model.parseDate
import arrow.core.Try
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

private const val metacriticUrl: String = "https://www.metacritic.com/browse/dvds/release-date/new-releases/date"

sealed class MovieFetcher(protected val url: String, private val httpClient: HttpClient) {

    abstract fun parseHtml(htmlStr: String): List<Movie>

    suspend fun fetchMovies(): Try<List<Movie>> =
        withContext(Dispatchers.IO) {
            try {
                val res = fetchHtml(url, httpClient)
                val htmlStr: String = //TODO check if there is a better way to propagate try
                    when (res) {
                        is Try.Success -> res.value
                        is Try.Failure -> throw res.exception
                    }
                Try.just(parseHtml(htmlStr))
            } catch (exp: Exception) {
                Try.raise<List<Movie>>(exp)
            }
        }

    class MetacriticFetcher(httpClient: HttpClient = HttpClient()) : MovieFetcher(metacriticUrl, httpClient) {
        override fun parseHtml(htmlStr: String): List<Movie> =
            Jsoup.parse(htmlStr)
                .body()
                .select("table.clamp-list tbody tr:not(.spacer)")
                .map { element ->
                    val title = element.select(".clamp-summary-wrap a.title h3").html()
                    val dateStr = element.select(".clamp-summary-wrap .clamp-details span:nth-child(2)").html()
                    val date = parseDate(dateStr).time
                    val posterUrl = element.select(".clamp-image-wrap a img").first().absUrl("src")
                    val metaScore = element.select(".clamp-summary-wrap .browse-score-clamp .clamp-metascore a div")
                        .first()
                        .html()
                        .toInt()
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
}

}