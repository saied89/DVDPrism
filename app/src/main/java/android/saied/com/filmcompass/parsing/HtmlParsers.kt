package android.saied.com.filmcompass.parsing

import android.saied.com.filmcompass.model.Movie
import org.jsoup.Jsoup

typealias HtmlMovieParser = (String) -> List<Movie>

