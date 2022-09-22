package com.android.tubes_pbp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private lateinit var btnLogout: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navView : NavigationBarView = findViewById(R.id.bottom_navigation)

        changeFragment(FragmentLowongan())
        val left = layout_fragment.paddingLeft
        val right = layout_fragment.paddingRight


        navView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> {
                    changeFragment(FragmentLowongan())
                    layout_fragment.setPadding(left,0,right,0)
                    true
                }
                R.id.search -> {

                    changeFragment(SearchFragment())
                    layout_fragment.setPadding(left,0,right,0)
                    true
                }
                R.id.favorite -> {

                    changeFragment(FavoriteFragment())
                    layout_fragment.setPadding(left,0,right,0)
                    true
                }
                R.id.profile -> {
                    changeFragment(ProfileFragment())
                    layout_fragment.setPadding(0,0,0,0)
                    true
                }
                R.id.experience ->{

                    changeFragment(SkillFragment())
                    layout_fragment.setPadding(left,0,right,0)
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