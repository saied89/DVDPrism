package android.saied.com.filmcompass.db.model

import android.saied.com.common.model.OmdbDetails
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index("name", unique = true)])
data class FavMovie(val name: String) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

