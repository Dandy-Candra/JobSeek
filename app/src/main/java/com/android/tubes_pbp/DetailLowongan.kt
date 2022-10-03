package com.android.tubes_pbp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.tubes_pbp.databinding.ActivityDetailLowonganBinding

class DetailLowongan : AppCompatActivity() {
    private lateinit var binding: ActivityDetailLowonganBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityDetailLowonganBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.getBundleExtra("detailLowongan")
        binding.title.setText(bundle?.getString("title", ""))
        binding.description.setText(bundle?.getString("description", ""))
        binding.image.setImageResource(bundle?.getInt("photo", 0)!!)

    }
}