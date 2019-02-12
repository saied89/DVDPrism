package com.saied.dvdprism.backend

import com.saied.dvdprism.common.model.Movie
import com.saied.dvdprism.common.model.OmdbDetails
import com.saied.dvdprism.common.model.OmdbType
import java.util.*

fun Movie.getDvdYear(): Int {
    val calendar = GregorianCalendar()
    calendar.time = Date(releaseDate)
    return calendar.get(Calendar.YEAR)
}

fun Movie.checkMoviesOmdbDetails(omdbDetails: OmdbDetails): Boolean {
    return (name == omdbDetails.title && omdbDetails.type == OmdbType.MOVIE)
}