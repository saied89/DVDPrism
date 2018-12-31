package android.saied.com.backend.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import java.util.concurrent.TimeUnit

private const val CONNECT_TIME_OUT: Long = 60

val networkModule = module {
    single {
        HttpClient(OkHttp) {
            engine {
                config {
                    readTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                    connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                }
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
            }
        }
    }
}