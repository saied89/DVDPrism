package android.saied.com.filmcompass

import android.saied.com.filmcompass.parsing.FetchTask
import arrow.core.Try
import arrow.core.success
import io.ktor.client.HttpClient

class MovieRepository(private val httpClient: HttpClient = HttpClient()) {

    suspend fun fetchMovies(taskList: List<FetchTask> = FetchTask.taskList) {
        taskList.forEach { task ->
            val htmlStr = fetchHtml(task.url, httpClient)
            when (htmlStr) {
                is Try.Success -> {}
                is Try.Failure -> {}
            }
        }
    }
}