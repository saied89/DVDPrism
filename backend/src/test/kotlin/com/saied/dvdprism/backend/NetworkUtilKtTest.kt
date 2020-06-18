package com.saied.dvdprism.backend

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

/*
* $ stands for . in function name
* */
internal class NetworkUtilKtTest {
    @Test
    fun `successful network request results in Try$Success`() = runBlocking {
        val sampleResStr = "Hello, Saied"
        val httpMockEngine = MockEngine { resqData ->
            respond(
                ByteReadChannel(sampleResStr.toByteArray(Charsets.UTF_8)),
                HttpStatusCode.OK
            )
        }
        val res = fetchHtml("arbitary URL", HttpClient(httpMockEngine))
        assert(res.isSuccess())
    }

    @Test
    fun `failed network request results in Try$Failure`() = runBlocking {
        val httpMockEngine = MockEngine {
            respond(
                ByteReadChannel(""),
                HttpStatusCode.Unauthorized
            )
        }
        val res = fetchHtml("arbitary URL", HttpClient(httpMockEngine))
        assert(res.isFailure())
    }


}