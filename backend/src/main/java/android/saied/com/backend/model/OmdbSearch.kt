package android.saied.com.backend.model

import android.saied.com.common.model.OmdbType
import com.fasterxml.jackson.annotation.JsonProperty

data class OmdbSearch(
    @JsonProperty("Response")
    val response: Boolean,
    @JsonProperty("Search")
    val search: List<Search>,
    @JsonProperty("totalResults")
    val totalResults: Int
)

data class Search(
    @JsonProperty("Poster")
    val poster: String,
    @JsonProperty("Title")
    val title: String,
    @JsonProperty("Type")
    val type: OmdbType,
    @JsonProperty("Year")
    val year: Int,
    @JsonProperty("imdbID")
    val imdbID: String
)