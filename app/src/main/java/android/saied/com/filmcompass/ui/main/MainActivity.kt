package android.saied.com.filmcompass.ui.main

import android.os.Bundle
import android.saied.com.filmcompass.R
import android.saied.com.filmcompass.ui.favoriteList.FavoritesActivity
import android.saied.com.filmcompass.utils.fixExitShareElementCallback
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fixExitShareElementCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
        when (item?.itemId) {
            R.id.favs -> {
                FavoritesActivity.launch(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

}
