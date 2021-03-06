package com.saied.dvdprism.app.utils

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.saied.dvdprism.common.model.Movie
import com.saied.dvdprism.common.model.OmdbDetails
import com.saied.dvdprism.common.model.ScoreIndication
import com.saied.dvdprism.app.R

val Movie.metaScoreString: String
    get() = if (this.metaScore != null) this.metaScore.toString() else "TBD"

val Movie.userScoreString: String
    get() = if (userScore != null) ((userScore as Int).toFloat() / 10).toString() else "TBD"

fun ScoreIndication.getColor(context: Context): Int =
    when (this) {
        ScoreIndication.POSITIVE -> R.color.scorePositive
        ScoreIndication.MIXED -> R.color.scoreMixed
        ScoreIndication.NEGATIVE -> R.color.scoreNegative
        ScoreIndication.TBD -> R.color.scoreTBD
    }.let { colorRes ->
        ContextCompat.getColor(context, colorRes)
    }

fun ScoreIndication.getUserScoreBG(context: Context): Drawable =
    when (this) {
        ScoreIndication.POSITIVE -> R.drawable.cicle_positive
        ScoreIndication.MIXED -> R.drawable.cicle_mixed
        ScoreIndication.NEGATIVE -> R.drawable.cicle_negative
        ScoreIndication.TBD -> R.drawable.cicle_tbd
    }.let { colorRes ->
        ContextCompat.getDrawable(context, colorRes)!!
    }

val OmdbDetails.highResPosterUrl: String
    get() = poster.replace("SX300", "SX640")