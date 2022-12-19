package com.android.tubes_pbp.ulasan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.tubes_pbp.databinding.RvItemUlasanBinding
import com.android.tubes_pbp.user.Experience

class RVUlasanAdapter(private var data: Array<Ulasan>, private val listener: OnAdapterListener) : RecyclerView.Adapter<RVUlasanAdapter.viewHolder>()  {
    private lateinit var binding: RvItemUlasanBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        binding = RvItemUlasanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val currentItem = data[position]
        holder.binding.tvKritik.text = currentItem.kritik
        holder.binding.tvSaran.text = currentItem.saran
        holder.binding.ratingBar.rating = currentItem.rating.toFloat()
        holder.binding.btnDelete.setOnClickListener {
            listener.onDelete(currentItem)
        }
        holder.binding.btnEdit.setOnClickListener {
            listener.onEdit(currentItem)
        }


    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class viewHolder( val binding: RvItemUlasanBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnAdapterListener {
        fun onDelete(ulasan: Ulasan)
        fun onEdit(ulasan: Ulasan)


    }

    fun setData (list: Array<Ulasan>){
        this.data = list
    }

}