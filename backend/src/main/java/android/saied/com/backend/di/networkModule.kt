package android.saied.com.backend.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.JacksonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import org.koin.dsl.module.module
import java.util.concurrent.TimeUnit

private const val CONNECT_TIME_OUT: Long = 60

const val HTML_CLIENT = "HTML_CLIENT"
const val JSON_CLIENT = "JSON_CLIENT"

val networkModule = module {

    single(name = HTML_CLIENT) {
        HttpClient(OkHttp) {
            engine {
                config {
                    readTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                    connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                }
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.INFO
            }
        }
    }

    single(name = JSON_CLIENT) {
        HttpClient(OkHttp) {
            install(JsonFeature) {
                serializer = JacksonSerializer()
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.INFO
            }
        }
    }

}