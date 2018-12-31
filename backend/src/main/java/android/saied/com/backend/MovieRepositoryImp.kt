package android.saied.com.backend

import android.saied.com.common.model.Movie
import com.mongodb.MongoBulkWriteException
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.InsertManyOptions
import org.litote.kmongo.deleteMany
import org.slf4j.LoggerFactory
import java.util.*

class MovieRepositoryImp(private val mongoCollection: MongoCollection<Movie>): MovieRepository {

    init {
        DataBaseInitUtils.createUniqueMovieIndices(mongoCollection)
    }

    override fun initDB(movies: List<Movie>) {
        mongoCollection.deleteMany()
        mongoCollection.insertMany(movies)
    }

    override fun saveMovies(movies: List<Movie>) {
        val options = InsertManyOptions().ordered(false)
        try {
            mongoCollection.insertMany(movies, options)
        } catch (e: MongoBulkWriteException) {
            if (e.writeErrors.any { it.code != 11000 }) //duplicate key error is expected here
                throw e
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

    override fun getMovies(date: Date, page: Int): List<Movie> =
        mongoCollection.find().asSequence().toList()

}