package android.saied.com.filmcompass.Network

import android.saied.com.filmcompass.fetchHtml
import android.saied.com.filmcompass.model.Movie
import android.saied.com.filmcompass.parsing.parseDate
import arrow.core.Try
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.lang.Exception

private const val rottenTomatoUrl: String = "https://www.rottentomatoes.com/browse/dvd-streaming-new/"
private const val metacriticUrl: String = "https://www.metacritic.com/browse/dvds/release-date/new-releases/date"

sealed class MovieFetcher(protected val url: String, protected val httpClient: HttpClient) {

    abstract suspend fun fetchMovies(): Try<List<Movie>>

    class RottenTomatoFetcher(httpClient: HttpClient = HttpClient()) : MovieFetcher(rottenTomatoUrl, httpClient) {
        override suspend fun fetchMovies(): Try<List<Movie>> =
            withContext<Try<List<Movie>>>(Dispatchers.IO) {
                try {
                    val res = fetchHtml(url, httpClient)
                    val htmlStr: String = //TODO check if there is a better way to propagate try
                        when (res) {
                            is Try.Success -> res.value
                            is Try.Failure -> throw res.exception
                        }
                    Try.just(parseRottenTomatoHtml(htmlStr))
                } catch (exp: Exception) {
                    Try.raise(exp)
                }
            }
    }

    class MetacriticFetcher(httpClient: HttpClient = HttpClient()) : MovieFetcher(metacriticUrl, httpClient) {
        override suspend fun fetchMovies(): Try<List<Movie>> =
            withContext<Try<List<Movie>>>(Dispatchers.IO) {
                try {
                    val res = fetchHtml(url, httpClient)
                    val htmlStr: String = //TODO check if there is a better way to propagate try
                        when (res) {
                            is Try.Success -> res.value
                            is Try.Failure -> throw res.exception
                        }
                    Try.just(parseMetacriticHtml(htmlStr))
                } catch (exp: Exception) {
                    Try.raise(exp)
                }
            }
    }

    protected fun parseRottenTomatoHtml(htmlStr: String) =
        Jsoup.parse(htmlStr)
            .body()
            .select("div.mb-movie")
            .map { element ->
                val title = element.select("h3.movieTitle").html()
                val dateStr = element.select("p.release-date").html()
                val date = parseDate(dateStr).time
                Movie(title, date)
            }

    protected fun parseMetacriticHtml(htmlStr: String) =
        Jsoup.parse(htmlStr)
            .body()
            .select(".clamp-summary-wrap")
            .map { element ->
                val title = element.select("a.title h3").html()
                val dateStr = element.select(".clamp-details span:nth-child(2)").html()
                val date = parseDate(dateStr).time
                Movie(title, date)
            }


}