package com.android.tubes_pbp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.tubes_pbp.entity.lowongan

class RVlowonganAdapter(private val data: Array<lowongan>) : RecyclerView.Adapter<RVlowonganAdapter.viewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_item_lowongan, parent, false)
        return viewHolder(itemView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem = data[position]
        holder.tvNama.text = currentItem.name
        holder.tvDetails.text = currentItem.detail
        holder.tvGambar.setImageResource(currentItem.photo)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.findViewById(R.id.tv_judul)
        val tvDetails : TextView = itemView.findViewById(R.id.tv_details)
        val tvGambar : ImageView = itemView.findViewById(R.id.tv_image)
    }
}