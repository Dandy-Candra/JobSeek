package com.android.tubes_pbp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.tubes_pbp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var btnLogout: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        if(temp==0){
//            left = layout_fragment.paddingLeft
//            right = layout_fragment.paddingRight
//        }

        val navView = binding.bottomNavigation
        navView.itemActiveIndicatorColor = getColorStateList(R.color.white)
        if (intent.getBundleExtra("keyBundle") != null) {
            navView.selectedItemId = R.id.profile
            //   layout_fragment.setPadding(0,0,0,0)
            changeFragment(ProfileFragment())
        } else {
            changeFragment(FragmentLowongan())
        }

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    changeFragment(FragmentLowongan())
//                    layout_fragment.setPadding(left,0,right,0)
                    true
                }
                R.id.search -> {
                    changeFragment(HomeFragment())
//                    layout_fragment.setPadding(left,0,right,0)
                    true
                }
                R.id.favorite -> {

                    changeFragment(FavoriteFragment())
//                    layout_fragment.setPadding(left,0,right,0)
                    true
                }
                R.id.profile -> {
                    changeFragment(ProfileFragment())
//                    layout_fragment.setPadding(0,0,0,0)
                    true
                }
                R.id.experience -> {

                    changeFragment(SkillFragment())
//                    layout_fragment.setPadding(left,0,right,0)
                    true
                }
                else -> false
            }
        }
    }

    private fun changeFragment(fragment: Fragment?) {
        if (fragment != null) {
            supportFragmentManager.beginTransaction().replace(R.id.layout_fragment, fragment)
                .commit()
        }
    }
}