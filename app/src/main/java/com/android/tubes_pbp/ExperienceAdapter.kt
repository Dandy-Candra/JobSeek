package com.android.tubes_pbp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.tubes_pbp.databinding.RvItemExperienceBinding
import com.android.tubes_pbp.user.Experience

class ExperienceAdapter(
    private val experiences: ArrayList<Experience>,
    private val listener: OnAdapterListener
) :
    RecyclerView.Adapter<ExperienceAdapter.ExperienceViewHolder>() {
    private lateinit var binding: RvItemExperienceBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperienceViewHolder {
        binding =
            RvItemExperienceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExperienceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExperienceViewHolder, position: Int) {
        val experience = experiences[position]
        holder.bind(experience)
        holder.view.experienceClick = listener

    }

    override fun getItemCount() = experiences.size

    inner class ExperienceViewHolder(val view: RvItemExperienceBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(experience: Experience) {
            view.experience = experience
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<Experience>) {
        experiences.clear()
        experiences.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(experience: Experience)
        fun onEdit(experience: Experience)
    }
}