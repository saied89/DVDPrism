package android.saied.com.filmcompass.model

import android.graphics.Color
import java.lang.IllegalArgumentException

data class Movie(
    val name: String,
    val releaseDate: Long,
    val posterUrl: String,
    val score: MetacriticScore
)

sealed class MetacriticScore(val score: Int, val color: Int, val className: String) {
    class Positive(score: Int) : MetacriticScore(score, color = Color.parseColor("#66CC33"), className = "positive")
    class Mixed(score: Int) : MetacriticScore(score, color = Color.parseColor("#FFCC33"), className = "mixed")
    class Negative(score: Int) : MetacriticScore(score, color = Color.parseColor("#FF0000"), className = "negative")

    companion object {
        fun byScore(score: Int): MetacriticScore =
            when (score) {
                in 61..100 -> MetacriticScore.Positive(score)
                in 40..60 -> MetacriticScore.Mixed(score)
                in 0..39 -> MetacriticScore.Negative(score)
                else -> throw IllegalArgumentException("Metascore out side the range:$score")
            }
    }
}