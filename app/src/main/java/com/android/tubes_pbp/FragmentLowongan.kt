package com.android.tubes_pbp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.tubes_pbp.entity.Lowongan


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
        val intent = Intent(activity, DetailLowongan::class.java)
        val adapter: RVlowonganAdapter = RVlowonganAdapter(Lowongan.listOfLowongan,
            object : RVlowonganAdapter.OnAdapterListener {
                override fun onClick(lowongan: Lowongan) {
                    val bundle = Bundle()
                    bundle.putString("title", lowongan.name)
                    bundle.putString("description", lowongan.detail)
                    bundle.putInt("photo", lowongan.photo)
                    intent.putExtra("detailLowongan", bundle)
                    startActivity(intent)
                }
            })

        val rvLowongan: RecyclerView = view.findViewById(R.id.rv_lowongan)

        rvLowongan.layoutManager = layoutManager

        rvLowongan.setHasFixedSize(true)

        rvLowongan.adapter = adapter
    }


}