package com.saied.dvdprism.backend

import com.fasterxml.jackson.databind.SerializationFeature
import com.saied.dvdprism.backend.di.appModule
import com.saied.dvdprism.backend.di.dbModule
import com.saied.dvdprism.backend.di.jsonModule
import com.saied.dvdprism.backend.di.networkModule
import com.saied.dvdprism.backend.task.MovieFetcherTask
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.netty.EngineMain
import org.koin.ktor.ext.get
import org.koin.standalone.StandAloneContext.startKoin

fun main(args: Array<String>) {
    startKoin(listOf(dbModule, appModule, networkModule, jsonModule))
    EngineMain.main(args)
}

fun Application.module() {
    install(ContentNegotiation) {
        install(StatusPages) {
            exception<Throwable> {
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to it.message))
            }
        }
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    val repository: MovieRepository = get()
    val fetcherService: MovieFetcherTask = get()
    fetcherService.initRepeatingTask()

    routing {
        get("/") {
            val movies = repository.getMovies()
            call.respond(movies)
        }
    }
}