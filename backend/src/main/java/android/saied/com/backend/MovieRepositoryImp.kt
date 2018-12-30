package android.saied.com.backend

import android.saied.com.common.model.Movie
import com.mongodb.client.MongoCollection
import org.litote.kmongo.deleteMany
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
        mongoCollection.insertMany(movies)
    }

    override fun getMovies(date: Date, page: Int): List<Movie> =
        mongoCollection.find().asSequence().toList()

}