package android.saied.com.backend

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockHttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.io.ByteReadChannel
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
/*
* $ stands for . in function name
* */
internal class NetworkUtilKtTest {
    @Test
    fun `successful network request results in Try$Success`() = runBlocking {
        val sampleResStr = "Hello, Saied"
        val httpMockEngine = MockEngine {
            MockHttpResponse(
                call,
                HttpStatusCode.OK,
                ByteReadChannel(sampleResStr.toByteArray(Charsets.UTF_8))
            )
        }
        val res = fetchHtml("arbitary URL", HttpClient(httpMockEngine))
        assert(res.isSuccess())
    }

    @Test
    fun `failed network request results in Try$Failure`() = runBlocking {
        val httpMockEngine = MockEngine {
            MockHttpResponse(
                call,
                HttpStatusCode.Unauthorized
            )
        }
        val res = fetchHtml("arbitary URL", HttpClient(httpMockEngine))
        assert(res.isFailure())
    }


}