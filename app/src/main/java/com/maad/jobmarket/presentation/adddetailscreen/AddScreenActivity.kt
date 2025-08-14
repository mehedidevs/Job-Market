package com.maad.jobmarket.presentation.adddetailscreen

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.maad.jobmarket.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_screen)

    }
}