package android.saied.com.filmcompass.db.model

import android.saied.com.common.model.Movie
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