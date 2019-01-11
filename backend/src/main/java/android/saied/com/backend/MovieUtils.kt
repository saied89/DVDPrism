package android.saied.com.backend

import android.saied.com.common.model.Movie
import android.saied.com.common.model.OmdbDetails
import java.util.*

fun Movie.getDvdYear(): Int {
    val calendar = GregorianCalendar()
    calendar.time = Date(releaseDate)
    return calendar.get(Calendar.YEAR)
}

fun Movie.checkMoviesOmdbDetails(omdbDetails: OmdbDetails): Boolean {
    return (name == omdbDetails.title && omdbDetails.type == "movie")
}