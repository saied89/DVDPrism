package android.saied.com.common.model

import java.io.Serializable

data class Movie(
    val name: String,
    val releaseDate: Long,
    val posterUrl_89p: String,
    val metaScore: Int?,
    val userScore: Int?,
    val description: String
) : Serializable {
    val metaIndication: ScoreIndication =
        indicationFromScore(metaScore)

    val userIndication: ScoreIndication =
        indicationFromScore(userScore)

    val posterUrl: String = posterUrl_89p.split('-').first()
    val posterUrl_250p: String = posterUrl_89p.replace("-98", "-250h")
}

enum class ScoreIndication {
    POSITIVE,
    MIXED,
    NEGATIVE,
    TBD
}

private fun indicationFromScore(score: Int?) =
    when (score) {
        null -> ScoreIndication.TBD
        in 61..100 -> ScoreIndication.POSITIVE
        in 40..60 -> ScoreIndication.MIXED
        in 0..39 -> ScoreIndication.NEGATIVE
        else -> throw IllegalArgumentException("Metascore outside the range:$score")
    }
