package com.saied.dvdprism.backend.di

import com.mongodb.ServerAddress
import com.saied.dvdprism.backend.MovieRepository
import com.saied.dvdprism.backend.MovieRepositoryImp
import com.saied.dvdprism.common.model.Movie
import com.mongodb.client.MongoCollection
import org.koin.dsl.module.module
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

private const val DB_NAME = "movieDB"

val dbModule = module {
    single { provideMongoCollection() }

    single { provideMovieRepository(get()) }
}

private fun provideMongoCollection(): MongoCollection<Movie> =
    KMongo.createClient(ServerAddress("mongo")).getDatabase(DB_NAME).getCollection()

private fun provideMovieRepository(collection: MongoCollection<Movie>): MovieRepository =
    MovieRepositoryImp(collection)
