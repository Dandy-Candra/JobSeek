package com.android.tubes_pbp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.tubes_pbp.databinding.RvItemLowonganBinding
import com.android.tubes_pbp.entity.Lowongan

class RVlowonganAdapter(
    private val data: Array<Lowongan>,
    private val listener: OnAdapterListener
) : RecyclerView.Adapter<RVlowonganAdapter.viewHolder>() {
    private lateinit var binding: RvItemLowonganBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        binding = RvItemLowonganBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem = data[position]
        holder.bind(currentItem)
        holder.binding.lowonganClick = listener
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class viewHolder(val binding: RvItemLowonganBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(lowongan: Lowongan) {
            binding.lowongan = lowongan
        }
    }

    interface OnAdapterListener {
        fun onClick(lowongan: Lowongan)

    }
}