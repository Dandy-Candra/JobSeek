package com.android.tubes_pbp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.tubes_pbp.databinding.RvItemLowonganBinding
import com.android.tubes_pbp.entity.Lowongan
import com.android.tubes_pbp.user.Experience
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class RVlowonganAdapter(private val data: Array<Lowongan>, private val listener: OnAdapterListener) : RecyclerView.Adapter<RVlowonganAdapter.viewHolder>()  {
    private lateinit var binding: RvItemLowonganBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        binding = RvItemLowonganBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem = data[position]
        Glide.with(holder.itemView.context)
            .load(currentItem.photo)
            .into(holder.imgPhoto)
        holder.bind(currentItem)
        holder.binding.lowonganClick = listener
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class viewHolder( val binding: RvItemLowonganBinding) : RecyclerView.ViewHolder(binding.root)
    {
        var imgPhoto: ImageView = itemView.findViewById(R.id.tv_image)
        fun bind(lowongan: Lowongan) {
            binding.lowongan = lowongan
        }
    }
    interface OnAdapterListener {
        fun onClick(lowongan: Lowongan)

    }
}