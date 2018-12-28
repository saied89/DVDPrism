package android.saied.com.backend

import android.saied.com.common.model.Movie
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import org.junit.jupiter.api.Test

internal class MainKtTest {
    @Test
    fun `test root`() {
        val expectedObject = Movie("saied", 0, "", 0, 0, "")
        withTestApplication({ module() }) {
            handleRequest(HttpMethod.Get, "/").apply {
                kotlin.test.assertEquals(HttpStatusCode.OK, response.status())
                kotlin.test.assertEquals(expectedObject, parseMovie(response.content)!!)
            }
        }
    }

    fun Movie.getJson(): String = ObjectMapper().registerModule(KotlinModule()).writeValueAsString(this)
    fun parseMovie(jsonStr: String?): Movie? = ObjectMapper().registerModule(KotlinModule()).readValue(jsonStr, Movie::class.java)
}