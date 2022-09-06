package com.android.tubes_pbp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.navigation.NavigationBarView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navView : NavigationBarView = findViewById(R.id.bottom_navigation)

        navView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> {
                    println("ini Home")
                    true
                }
                R.id.search -> {
                    println("ini Search")
                    true
                }
                R.id.favorite -> {
                    println("ini Favorite")
                    true
                }
                R.id.profile -> {
                    println("ini Profile")
                    true
                }
                else -> false
            }
        }
    }



}