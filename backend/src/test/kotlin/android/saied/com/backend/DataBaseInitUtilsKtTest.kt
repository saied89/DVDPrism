package android.saied.com.backend

import android.saied.com.common.model.Movie
import arrow.core.Failure
import arrow.core.Try
import com.mongodb.MongoBulkWriteException
import com.mongodb.client.MongoCollection
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

private const val DB_NAME = "TEST"

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DataBaseInitUtilsKtTest {

    private val movieTestCollection: MongoCollection<Movie> = KMongo.createClient().getDatabase(DB_NAME).getCollection()
    private val movieRepository: MovieRepository = MovieRepositoryImp(movieTestCollection)

    @BeforeEach
    fun setupEach() {
        movieTestCollection.drop()
    }

    private fun insertDuplicate(): Try<Unit> {
        val movie1 = dummyMovie
        val movie2 = dummyMovie
        return movieRepository.saveMovies(listOf(movie1, movie2))
    }

    @Test
    fun `without uniqueIndex insertion of duplicate movie succeeds`() {
        val res = insertDuplicate()
        assert(res.isSuccess())
    }

    @Test
    fun `with uniqueIndex insertion of duplicate movie fails`() {
        DataBaseInitUtils.createUniqueMovieIndices(movieTestCollection)
        val res = insertDuplicate()
        assert(res is Failure)
        assert((res as Failure).exception is MongoBulkWriteException)
        assert((res.exception as MongoBulkWriteException).writeErrors.all { it.code == 11000 })
    }
}