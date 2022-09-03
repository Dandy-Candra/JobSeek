package com.android.tubes_pbp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        getSupportFragmentManager().beginTransaction().replace(R.id.layout_fragment, LoginFragment()).commit()
    }

}