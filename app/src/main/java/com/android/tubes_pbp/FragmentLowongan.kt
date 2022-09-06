package com.android.tubes_pbp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.tubes_pbp.entity.lowongan



class FragmentLowongan : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lowongan, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        val adapter : RVlowonganAdapter = RVlowonganAdapter(lowongan.listOfLowongan)

        val rvLowongan : RecyclerView = view.findViewById(R.id.rv_lowongan)

        rvLowongan.layoutManager = layoutManager

        rvLowongan.setHasFixedSize(true)

        rvLowongan.adapter = adapter
    }


}