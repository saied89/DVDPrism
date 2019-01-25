package android.saied.com.filmcompass.db

import android.saied.com.common.model.Movie
import android.saied.com.common.model.OmdbTypeConverters
import android.saied.com.filmcompass.db.model.FavMovie
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FavMovie::class, Movie::class], version = 1)
@TypeConverters(OmdbTypeConverters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDAO
//    companion object {
//        @Volatile
//        private var INSTANCE: MovieDatabase? = null
//
//        fun getDatabase(context: Context): MovieDatabase {
//            return INSTANCE ?: synchronized(this) {
//                Room.databaseBuilder(
//                    context.applicationContext,
//                    MovieDatabase::class.java,
//                    "Movie_database"
//                ).build()
//            }
//        }
//    }
}