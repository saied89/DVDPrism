package android.saied.com.common.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
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
    @PrimaryKey(autoGenerate = true)
    var dbId: Int = 0

    fun getMetaIndication(): ScoreIndication =
        indicationFromScore(metaScore)

    fun getUserIndication(): ScoreIndication =
        indicationFromScore(userScore)

    fun getPosterUrl(): String = posterUrl_89p.split('-').first()
    fun getPosterUrl250p(): String = posterUrl_89p.replace("-98", "-250h")
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
