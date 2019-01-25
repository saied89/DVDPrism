package android.saied.com.backend

import android.saied.com.common.model.Movie
import android.saied.com.common.model.OmdbDetails
import android.saied.com.common.model.OmdbType
import com.mongodb.client.MongoCollection
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.litote.kmongo.KMongo
import org.litote.kmongo.deleteMany
import org.litote.kmongo.getCollection

val dummyOmdbDetails = OmdbDetails(
    "",
    "",
    "",
    "",
    true,
    "",
    "",
    OmdbType.MOVIE,
    1970,
    ""
)

private const val DB_NAME = "TEST"

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MovieRepositoryImpTest {


    val movieTestCollection: MongoCollection<Movie> = KMongo.createClient().getDatabase(DB_NAME).getCollection()
    val movieRepository: MovieRepository = MovieRepositoryImp(movieTestCollection)

    @BeforeEach
    fun cleanDB() {
        movieTestCollection.deleteMany()
    }

    @Test
    fun `save 2 movies to db and check them`() {
        val movie1 = Movie("title1", 0L, "", 0, 0, "", null)
        val movie2 = movie1.copy(name = "title2")

        movieRepository.saveMovies(listOf(movie1, movie2))

        val res = movieRepository.getMovies()
        assertEquals(2, res.size)
        assertEquals(movie1, res[0])
        assertEquals(movie2, res[1])
    }

    @Test
    fun `bulk insert continues after duplicate key`() {
        val movie1 = dummyMovie
        val movie2 = dummyMovie
        val movie4 = dummyMovie
        val movie3 = movie2.copy(name = "title2")

        movieRepository.saveMovies(listOf(movie1, movie2, movie3, movie4))

        val res = movieRepository.getMovies()
        assertEquals(2, res.size)
    }

    @Test
    fun `is able to store a movie with omdbDetails`() {
        val movie = dummyMovie.copy(omdbDetails = dummyOmdbDetails)

        movieRepository.saveMovies(listOf(movie))

        val res = movieRepository.getMovies()
        assertEquals(1, res.size)
        assertNotNull(res[0].omdbDetails)
    }

    //
//    @Test
//    fun `getInfoLessMovies returns only infoless movies`() {
//        val movie1 = Movie("title1", 0L, "", 0, 0, "")
//        val movie2 = movie1.copy(name = "title2")
//        movieRepository.saveMovies(listOf(movie1, movie2))
//
//        val res = movieRepository.getInfoLessMovies()
//        assert(res.size == 1)
//        assert(res[0].equals(movie1))
//    }

//    @Test
//    fun `setOmdbDataOfMovie sets omdbdata`() {
//        val movie1 = Movie(title = "title1", releaseDate = 0L)
//        val omdbData = getSampleOMDBInfo()
//
//        movieRepository.saveMovies(listOf(movie1))
//        val res = movieRepository.setOmdbDataOfMovie(movie1, omdbData)
//
//        assert(res.wasAcknowledged())
//        assertNotNull(movieRepository.getMovies()[0].omdbData)
//    }

//    private fun getSampleOMDBInfo(): OmdbData {
//        val moshi: Moshi = get()
//        val adapter = moshi.adapter(OmdbData::class.java)
//
//        val jsonText = javaClass.getResource("../api/OmdpSample.json").readText()
//        return adapter.fromJson(jsonText)!!
//    }
}