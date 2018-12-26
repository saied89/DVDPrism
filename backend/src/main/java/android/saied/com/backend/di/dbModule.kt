package android.saied.com.backend.di

import android.saied.com.backend.MovieRepository
import android.saied.com.backend.MovieRepositoryImp
import android.saied.com.common.model.Movie
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
    KMongo.createClient().getDatabase(DB_NAME).getCollection()

private fun provideMovieRepository(collection: MongoCollection<Movie>): MovieRepository =
    MovieRepositoryImp(collection)
