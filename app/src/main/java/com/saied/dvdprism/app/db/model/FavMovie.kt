package com.saied.dvdprism.app.db.model

import com.saied.dvdprism.common.model.Movie
import androidx.room.*

@Entity(
    foreignKeys = [ForeignKey(
        entity = Movie::class,
        parentColumns = arrayOf("name"),
        childColumns = arrayOf("title")
    )],
    indices = [Index("title", unique = true)]
)
data class FavMovie(val title: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}