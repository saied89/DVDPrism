package android.saied.com.backend

import android.saied.com.common.model.Movie
import arrow.core.Either
import com.mongodb.MongoException
import com.mongodb.client.MongoCollection
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

private const val DB_NAME = "TEST"

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DataBaseInitUtilsKtTest {

    var movieTestCollection: MongoCollection<Movie> = KMongo.createClient().getDatabase(DB_NAME).getCollection()
    var movieRepository: MovieRepository = MovieRepositoryImp(movieTestCollection)

    @BeforeEach
    fun setupEach() {
        movieTestCollection.drop()
        movieTestCollection = KMongo.createClient().getDatabase(DB_NAME).getCollection()
    }

    private fun insertDuplicate(): Either<MongoException, Unit> {
        val movie1 = dummyMovie
        val movie2 = dummyMovie
        val res: Either<MongoException, Unit> =
            try {
                movieRepository.saveMovies(listOf(movie1, movie2))
                Either.right(Unit)
            } catch (exp: MongoException) {
                Either.left(exp)
            }
        return res
    }

    @Test
    fun `without uniqueIndex insertion of duplicate movie succeeds`() {
        val res: Either<MongoException, Unit> =
            insertDuplicate()
        assert(res.isRight()) //Doesn't fail
    }

    @Test
    fun `with uniqueIndex insertion of duplicate movie fails`() {
        DataBaseInitUtils.createUniqueMovieIndices(movieTestCollection)
        val res: Either<MongoException, Unit> =
            insertDuplicate()
        assert(res.isLeft()) //Fails
    }
}