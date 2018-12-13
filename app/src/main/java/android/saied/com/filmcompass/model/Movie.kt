package android.saied.com.filmcompass.model

import android.graphics.Color
import java.lang.IllegalArgumentException

data class Movie(
    val name: String,
    val releaseDate: Long,
    val posterUrl: String,
    val score: Int
) {
    val indication: MetaScore =
        when (score) {
            in 61..100 -> MetaScore.POSITIVE
            in 40..60 -> MetaScore.MIXED
            in 0..39 -> MetaScore.NEGATIVE
            else -> throw IllegalArgumentException("Metascore outside the range:$score")
        }
}

enum class MetaScore {
    POSITIVE,
    MIXED,
    NEGATIVE
}