package com.android.tubes_pbp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.tubes_pbp.databinding.ActivityHomeBinding
import com.android.tubes_pbp.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private val id = "idKey"
    private val myPreference = "myPref"
    private val name = "nameKey"
    var sharedPreferences: SharedPreferences? = null
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var bindingHome: ActivityHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingHome = ActivityHomeBinding.inflate(layoutInflater)
        sharedPreferences = activity?.getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        val intent = Intent(activity, HomeActivity::class.java)

        binding.user.setText(sharedPreferences!!.getString(name,""))

        binding.btnLowongan.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("key", "pindahLowongan")
            intent.putExtra("keyBundle", bundle)
            startActivity(intent)
            activity?.finish()
        }

        binding.btnProfile.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("key", "pindahProfile")
            intent.putExtra("keyBundle", bundle)
            startActivity(intent)
            activity?.finish()


        }


    }

}