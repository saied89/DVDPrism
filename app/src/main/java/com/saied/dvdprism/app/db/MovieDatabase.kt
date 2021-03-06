package com.saied.dvdprism.app.db

import com.saied.dvdprism.common.model.Movie
import com.saied.dvdprism.common.model.OmdbTypeConverters
import com.saied.dvdprism.app.db.model.FavMovie
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [FavMovie::class, Movie::class], version = 1)
@TypeConverters(OmdbTypeConverters::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDAO
}