package android.saied.com.filmcompass.parsing

import android.saied.com.filmcompass.model.Movie
import org.jsoup.Jsoup

typealias HtmlMovieParser = suspend (String) -> List<Movie>

class FetchTask private constructor(
    val url: String,
    val parser: HtmlMovieParser
) {
    companion object {
        val taskList = listOf<FetchTask>()
    }
}

private val rottentomatoParser: HtmlMovieParser = { htmlStr ->
    Jsoup.parse(htmlStr)
        .body()
        .select(".clamp-summary-wrap")
        .map { element ->
            val title = element.select("a.title").html()
            val dateStr = element.select(".clamp-details span:nth-child(2)").html()
            val date = parseDate(dateStr).time
            Movie(title, date)
        }
}
