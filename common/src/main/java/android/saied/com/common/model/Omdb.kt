package android.saied.com.common.model
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable


data class OmdbDetails(
    @JsonProperty("Actors")
    val actors: String,
    @JsonProperty("Awards")
    val awards: String,
    @JsonProperty("BoxOffice")
    val boxOffice: String,
    @JsonProperty("Country")
    val country: String,
    @JsonProperty("DVD")
    val dvd: String,
    @JsonProperty("Director")
    val director: String,
    @JsonProperty("Genre")
    val genre: String,
    @JsonProperty("Language")
    val language: String,
    @JsonProperty("Plot")
    val plot: String,
    @JsonProperty("Poster")
    val poster: String,
    @JsonProperty("Production")
    val production: String,
    @JsonProperty("Rated")
    val rated: String,
//    @JsonProperty("Ratings")
//    val ratings: List<Rating>,
    @JsonProperty("Released")
    val released: String,
    @JsonProperty("Response")
    val response: String,
    @JsonProperty("Runtime")
    val runtime: String,
    @JsonProperty("Title")
    val title: String,
    @JsonProperty("Type")
    val type: String,
    @JsonProperty("Website")
    val website: String,
    @JsonProperty("Writer")
    val writer: String,
    @JsonProperty("Year")
    val year: String,
    @JsonProperty("imdbID")
    val imdbID: String,
    @JsonProperty("imdbRating")
    val imdbRating: String,
    @JsonProperty("imdbVotes")
    val imdbVotes: String
) : Serializable

data class Rating(
    @JsonProperty("Source")
    val source: String,
    @JsonProperty("Value")
    val value: String
) : Serializable