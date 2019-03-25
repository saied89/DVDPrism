package com.saied.dvdprism.backend

import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.get
import org.koin.ktor.ext.inject

fun Route.detailless() {
    val repository: MovieRepository by inject()

    get("/detailless") {
        call.respond(repository.getDetaillessMovies())
    }
}