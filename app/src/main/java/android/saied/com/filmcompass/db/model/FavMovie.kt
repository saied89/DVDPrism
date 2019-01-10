package android.saied.com.filmcompass.db.model

import android.saied.com.common.model.Movie
import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    foreignKeys = [ForeignKey(
        entity = Movie::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("movieId"),
        onDelete = CASCADE
    )],
    indices = [Index("movieId", unique = true)]
)
data class FavMovie(val movieId: Int = 0) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

