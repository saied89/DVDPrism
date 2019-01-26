package android.saied.com.backend

import android.saied.com.common.model.Movie
import arrow.core.Try
import com.mongodb.MongoBulkWriteException
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.InsertManyOptions
import org.litote.kmongo.deleteMany
import org.litote.kmongo.sort

class MovieRepositoryImp(private val mongoCollection: MongoCollection<Movie>) : MovieRepository {

    init {
        DataBaseInitUtils.createUniqueMovieIndices(mongoCollection)
    }

    override fun initDB(movies: List<Movie>) {
        mongoCollection.deleteMany()
        mongoCollection.insertMany(movies)
    }

    override fun saveMovies(movies: List<Movie>): Try<Unit> {
        val options = InsertManyOptions().ordered(false)
        return try {
            Try.just(mongoCollection.insertMany(movies, options))
        } catch (e: MongoBulkWriteException) {
            Try.raise(e)
        }

//        movies.forEach { movie ->
//            try {
//                mongoCollection.insertOne(movie)
//            } catch (e: Exception) {
//                with(LoggerFactory.getLogger(javaClass)) {
//                    debug("error in insertMany: $e.message")
//                }
//            }
//        }
    }

    override fun getMovies(): List<Movie> =
        mongoCollection.find().sort("{ releaseDate: -1 }").asSequence().toList()

}