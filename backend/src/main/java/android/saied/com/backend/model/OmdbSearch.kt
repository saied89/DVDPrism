package android.saied.com.backend.model
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue


data class OmdbSearch(
    @JsonProperty("Response")
    val response: String,
    @JsonProperty("Search")
    val search: List<Search>,
    @JsonProperty("totalResults")
    val totalResults: String
)

data class Search(
    @JsonProperty("Poster")
    val poster: String,
    @JsonProperty("Title")
    val title: String,
    @JsonProperty("Type")
    val type: SearchResType,
    @JsonProperty("Year")
    val year: String,
    @JsonProperty("imdbID")
    val imdbID: String
)

enum class SearchResType(@JsonValue val typeStr: String) {
    MOVIE("movie"), SERIES("series")
}