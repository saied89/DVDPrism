package android.saied.com.filmcompass.parsing

import android.saied.com.filmcompass.model.Movie
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class FetchTask private constructor(
    val url: String,
    val parser: HtmlMovieParser
) {
    companion object {
        val taskList = listOf<FetchTask>()
    }
}
