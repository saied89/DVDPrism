package android.saied.com.filmcompass.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.saied.com.filmcompass.R
import android.widget.Toast
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.resLiveData.observe(this, Observer {
            Toast.makeText(this, it.length.toString(), Toast.LENGTH_SHORT).show()
        })
        viewModel.fetchHtml()
    }
}
