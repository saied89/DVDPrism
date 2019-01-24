package android.saied.com.backend.model
import com.fasterxml.jackson.annotation.JsonProperty


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
    val type: String,
    @JsonProperty("Year")
    val year: String,
    @JsonProperty("imdbID")
    val imdbID: String
)