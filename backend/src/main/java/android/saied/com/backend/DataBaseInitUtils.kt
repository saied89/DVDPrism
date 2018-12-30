package android.saied.com.backend

import android.saied.com.common.model.Movie
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.IndexOptions
import com.mongodb.client.model.Indexes

object DataBaseInitUtils {

    fun createUniqueMovieIndices(collection: MongoCollection<Movie>): String =
        collection.createIndex(Indexes.descending("name"), IndexOptions().unique(true))

}
