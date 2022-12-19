package com.android.tubes_pbp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.tubes_pbp.databinding.FragmentLowonganBinding
import com.android.tubes_pbp.databinding.FragmentProfileBinding
import com.android.tubes_pbp.entity.Lowongan



class FragmentLowongan : Fragment() {

    private var _binding: FragmentLowonganBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLowonganBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        val intent = Intent(activity, DetailLowongan::class.java)
        val adapter : RVlowonganAdapter = RVlowonganAdapter(Lowongan.listOfLowongan,object : RVlowonganAdapter.OnAdapterListener{
            override fun onClick(lowongan: Lowongan) {
                val bundle = Bundle()
                bundle.putString("title",lowongan.perusahaan)
                bundle.putString("posisi",lowongan.name)
                bundle.putString("description",lowongan.detail)
                bundle.putInt("photo",lowongan.photo)
                intent.putExtra("detailLowongan",bundle)
                startActivity(intent)
            }
        })

        val rvLowongan : RecyclerView = binding.rvLowongan

        rvLowongan.layoutManager = layoutManager

        rvLowongan.setHasFixedSize(true)

        rvLowongan.adapter = adapter
    }


}