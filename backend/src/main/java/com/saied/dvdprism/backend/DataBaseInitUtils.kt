package com.saied.dvdprism.backend

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.IndexOptions
import com.mongodb.client.model.Indexes
import com.saied.dvdprism.common.model.Movie

object DataBaseInitUtils {

    fun createUniqueMovieIndices(collection: MongoCollection<Movie>): String =
        collection.createIndex(Indexes.descending("name"), IndexOptions().unique(true))

}
