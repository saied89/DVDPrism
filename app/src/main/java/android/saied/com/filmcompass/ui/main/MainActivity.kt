package android.saied.com.filmcompass.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.saied.com.filmcompass.R
import android.widget.Toast
import androidx.lifecycle.Observer
import arrow.core.Try
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.resLiveData.observe(this, Observer { result ->
            when(result){
                is Try.Success -> Toast.makeText(this, result.value.length.toString(), Toast.LENGTH_SHORT).show()
                is Try.Failure -> Toast.makeText(this, result.exception.message, Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.fetchHtml()
    }
}
