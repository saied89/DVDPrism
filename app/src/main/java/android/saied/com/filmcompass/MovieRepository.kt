package android.saied.com.filmcompass

import android.saied.com.filmcompass.parsing.FetchTask
import io.ktor.client.HttpClient

class MovieRepository(private val httpClient: HttpClient = HttpClient()) {

    suspend fun fetchMovies(taskList: List<FetchTask> = FetchTask.taskList) {
        taskList.forEach {

        }
    }
}