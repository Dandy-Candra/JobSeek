package com.android.tubes_pbp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView

class HomeActivity : AppCompatActivity() {
    private lateinit var btnLogout: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navView : NavigationBarView = findViewById(R.id.bottom_navigation)


        navView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> {
                    changeFragment(FragmentLowongan())
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
                    changeFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    fun changeFragment(fragment: Fragment?){
        if(fragment!=null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.layout_fragment, fragment).commit()
        }
    }



}