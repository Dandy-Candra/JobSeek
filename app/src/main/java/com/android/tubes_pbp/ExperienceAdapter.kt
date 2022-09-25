package com.android.tubes_pbp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.tubes_pbp.user.Experience
import kotlinx.android.synthetic.main.rv_item_experience.view.*

class ExperienceAdapter (private val experiences: ArrayList<Experience>, private val listener: OnAdapterListener) :
    RecyclerView.Adapter<ExperienceAdapter.ExperienceViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ExperienceViewHolder {
        return ExperienceViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.rv_item_experience,parent, false)
        )
    }

    override fun onBindViewHolder(holder: ExperienceViewHolder, position:
    Int) {
        val experience = experiences[position]
        holder.view.tv_title.text = experience.title
        holder.view.tv_description.text = experience.description
        holder.view.card_skill.setOnClickListener {
            listener.onClick(experience)
        }
        holder.view.tv_edit.setOnClickListener {
            listener.onEdit(experience)
        }

    }

    override fun getItemCount() = experiences.size

    inner class ExperienceViewHolder( val view: View) : RecyclerView.ViewHolder(view)

    @SuppressLint("NotifyDataSetChanged")
    fun setData (list: List<Experience>){
        experiences.clear()
        experiences.addAll(list)
        notifyDataSetChanged()
    }
    interface OnAdapterListener {
        fun onClick(experience: Experience)
        fun onEdit(experience: Experience)
    }
}