package com.saied.dvdprism.app.ui.main

import android.os.Bundle
import com.saied.dvdprism.app.R
import com.saied.dvdprism.app.utils.fixExitShareElementCallback
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    @VisibleForTesting
    val viewModel: IMainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fixExitShareElementCallback()
    }

}
