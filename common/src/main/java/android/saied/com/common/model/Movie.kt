package android.saied.com.common.model

import java.io.Serializable

data class Movie(
    val name: String,
    val releaseDate: Long,
    val posterUrl_89p: String,
    val metaScore: Int
) : Serializable {
    val indication: MetaScore =
        when (metaScore) {
            in 61..100 -> MetaScore.POSITIVE
            in 40..60 -> MetaScore.MIXED
            in 0..39 -> MetaScore.NEGATIVE
            else -> throw IllegalArgumentException("Metascore outside the range:$metaScore")
        }

    val posterUrl: String = posterUrl_89p.split('-').first()
    val posterUrl_250p: String = posterUrl_89p.replace("-98", "-250h")
}

enum class MetaScore {
    POSITIVE,
    MIXED,
    NEGATIVE
}