package android.saied.com.filmcompass.parsing

import android.saied.com.filmcompass.model.Movie
import org.jsoup.Jsoup

sealed class HtmlSource(val source: String) {
    abstract suspend fun parseMovies(): List<Movie>

    class RottenTomatoSource(source: String) : HtmlSource(source) {
        override suspend fun parseMovies() =
            Jsoup.parse(source)
                .body()
                .select(".clamp-summary-wrap")
                .map { element ->
                    val title = element.select("a.title").html()
                    val dateStr = element.select(".clamp-details span:nth-child(2)").html()
                    val date = parseDate(dateStr).time
                    Movie(title, date)
                }
    }

}