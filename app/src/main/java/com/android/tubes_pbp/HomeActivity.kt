package com.android.tubes_pbp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import com.android.tubes_pbp.databinding.ActivityHomeBinding
import com.android.tubes_pbp.databinding.ActivityRegisterBinding
import com.google.android.material.navigation.NavigationBarView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.view.*

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var btnLogout: Button
    var left = 0
    var right = 0
    var temp = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(temp==0){
            left = layout_fragment.paddingLeft
            right = layout_fragment.paddingRight
        }


        val navView  = binding.bottomNavigation
        navView.itemActiveIndicatorColor = getColorStateList(R.color.white)
        if(intent.getBundleExtra("keyBundle")!=null){
            navView.selectedItemId = R.id.profile
            layout_fragment.setPadding(0,0,0,0)
            changeFragment(ProfileFragment())
        }else{
            changeFragment(FragmentLowongan())
        }




        navView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home -> {
                    changeFragment(FragmentLowongan())
                    layout_fragment.setPadding(left,0,right,0)
                    true
                }
                R.id.search -> {
                    changeFragment(HomeFragment())
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