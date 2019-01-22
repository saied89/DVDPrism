package android.saied.com.filmcompass.ui.favoriteList

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.saied.com.filmcompass.R
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_favotire_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesActivity : AppCompatActivity() {

    private val viewModel: FavoritesViewModel by viewModel()

    private val adapter = FavoritesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favotire_list)
        setSupportActionBar(toolbar)
        recyclerView.run {
            layoutManager = LinearLayoutManager(this@FavoritesActivity, RecyclerView.VERTICAL, false)
            adapter = this@FavoritesActivity.adapter
        }
        viewModel.favoritesLiveData.observe(this, Observer {
            adapter.data = it
        })
    }

    companion object {
        fun launch(context: Context) =
            with(context) {
                startActivity(Intent(this, FavoritesActivity::class.java))
            }
    }

}
