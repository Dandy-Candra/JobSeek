package com.android.tubes_pbp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.android.tubes_pbp.databinding.RvItemExperienceBinding
import com.android.tubes_pbp.user.Experience
import kotlinx.android.synthetic.main.rv_item_experience.view.*
import java.util.*
import kotlin.collections.ArrayList

class ExperienceAdapter (private var experiences: List<Experience>, private val listener: OnAdapterListener) :
    RecyclerView.Adapter<ExperienceAdapter.ExperienceViewHolder>(), Filterable
{

    private var filteredExperienceList: MutableList<Experience>
    private lateinit var binding: RvItemExperienceBinding
    init {
        filteredExperienceList = ArrayList(experiences)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperienceViewHolder {
        binding = RvItemExperienceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExperienceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExperienceViewHolder, position: Int) {
        val experience = filteredExperienceList[position]
        holder.bind(experience)
        holder.view.experienceClick = listener
    }

    override fun getItemCount() = filteredExperienceList.size

    inner class ExperienceViewHolder( val view: RvItemExperienceBinding) : RecyclerView.ViewHolder(view.root){
        fun bind(experience: Experience) {
            view.experience = experience
        }
    }


    fun setData (list: Array<Experience>){
        this.experiences = list.toList()
        filteredExperienceList = list.toMutableList()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charSequenceString = charSequence.toString()
                val filtered: MutableList<Experience> = java.util.ArrayList()
                if(charSequenceString.isEmpty()){
                    filtered.addAll(experiences)
                }else{
                    for (experience in experiences){
                        println(experience.title.lowercase(Locale.getDefault()))
                        if(experience.title.lowercase(Locale.ROOT).contains(charSequenceString.lowercase(Locale.ROOT))){
                            filtered.add(experience)
                        }

                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filtered
                for (i in filtered){
                    println(i.title)
                }
                return filterResults

            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults( CharSequence: CharSequence, filterResults: FilterResults) {
                filteredExperienceList.clear()
                filteredExperienceList.addAll(filterResults.values as List<Experience>)
                notifyDataSetChanged()
            }
        }
    }

    interface OnAdapterListener {
        fun onClick(experience: Experience)
        fun onEdit(experience: Experience)
    }
}