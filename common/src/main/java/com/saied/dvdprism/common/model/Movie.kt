package com.saied.dvdprism.common.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(indices = [Index("name", unique = true)])
data class Movie(
    val name: String,
    val releaseDate: Long,
    val posterUrl_89p: String,
    val metaScore: Int?,    //Null means TBD
    val userScore: Int?,    //Null means TBD
    val description: String,
    @Embedded val omdbDetails: OmdbDetails? = null
) : Serializable {
    @JsonIgnore
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun getMetaIndication(): ScoreIndication =
        indicationFromScore(metaScore)

    fun getUserIndication(): ScoreIndication =
        indicationFromScore(userScore)

    fun getPosterUrl(): String = posterUrl_89p.replace("-98", "")
    fun getPosterUrl250p(): String = posterUrl_89p.replace("-98", "-250h")
}

enum class ScoreIndication(val minScore: Int?, val maxScore: Int?) {
    POSITIVE(61, 100),
    MIXED(40, 60),
    NEGATIVE(0, 39),
    TBD(null, null)
}

private fun indicationFromScore(score: Int?) =
    when (score) {
        null -> ScoreIndication.TBD
        in ScoreIndication.POSITIVE.minScore!!..ScoreIndication.POSITIVE.maxScore!! -> ScoreIndication.POSITIVE
        in ScoreIndication.MIXED.minScore!!..ScoreIndication.MIXED.maxScore!! -> ScoreIndication.MIXED
        in ScoreIndication.NEGATIVE.minScore!!..ScoreIndication.NEGATIVE.maxScore!! -> ScoreIndication.NEGATIVE
        else -> throw IllegalArgumentException("Metascore outside the range:$score")
    }
