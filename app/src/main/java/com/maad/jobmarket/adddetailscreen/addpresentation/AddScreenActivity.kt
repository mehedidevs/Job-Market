package com.maad.jobmarket.adddetailscreen.addpresentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.maad.jobmarket.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_screen)

    }
}