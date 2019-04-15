package com.saied.dvdprism.common.model
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class OmdbDetails(
    @JsonProperty("Actors")
    val actors: String,
//    @JsonProperty("Awards")
//    val awards: String,
//    @JsonProperty("BoxOffice")
//    val boxOffice: String,
//    @JsonProperty("Country")
//    val country: String,
//    @JsonProperty("DVD")
//    val dvd: String,
    @JsonProperty("Director")
    val director: String,
    @JsonProperty("Genre")
    val genre: String,
//    @JsonProperty("Language")
//    val language: String,
//    @JsonProperty("Plot")
//    val plot: String,
    @JsonProperty("Poster")
    val poster: String,
//    @JsonProperty("Production")
//    val production: String,
//    @JsonProperty("Rated")
//    val rated: String,
//    @JsonProperty("Released")
//    val released: String,
    @JsonProperty("Response")
    val response: Boolean,
    @JsonProperty("Runtime")
    val runtime: String,
    @JsonProperty("Title")
    val title: String,
    @JsonProperty("Type")
    val type: OmdbType,
//    @JsonProperty("Website")
//    val website: String,
//    @JsonProperty("Writer")
//    val writer: String,
    @JsonProperty("Year")
    val year: Int,
    @JsonProperty("imdbID")
    val imdbID: String
//    @JsonProperty("imdbRating")
//    val imdbRating: String,
//    @JsonProperty("imdbVotes")
//    val imdbVotes: String
) : Serializable

enum class OmdbType(@JsonValue val typeStr: String) {
    MOVIE("movie"), SERIES("series");
}

@TypeConverters
class OmdbTypeConverters {
    @TypeConverter
    fun fromString(str: String) =
        when (str) {
            OmdbType.MOVIE.typeStr -> OmdbType.MOVIE
            OmdbType.SERIES.typeStr -> OmdbType.SERIES
            else -> throw IllegalArgumentException()
        }

    @TypeConverter
    fun toString(type: OmdbType): String = type.typeStr
}
