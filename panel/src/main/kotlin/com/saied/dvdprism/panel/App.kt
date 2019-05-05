import com.saied.dvdprism.panel.network.Fetchers
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import react.dom.p
import react.dom.render
import kotlin.browser.document
import kotlin.browser.window

private val job = Job()
private val uiScope = CoroutineScope(Dispatchers.Main + job)

fun main(args: Array<String>) {
    window.onload = {
        val root = document.getElementById("root")
        render(root) {
            uiScope.launch {
                Fetchers.fetchDetaillessMovies(HttpClient())
                    .map {
                        p { +it.title }
                    }
            }
        }
//        Unit
//        render(root) {
//            p { +"saied" }
//        }
    }
}
