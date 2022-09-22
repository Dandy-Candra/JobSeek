package com.android.tubes_pbp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.tubes_pbp.databinding.FragmentInputExperienceBinding
import com.android.tubes_pbp.databinding.FragmentSkillBinding
import com.android.tubes_pbp.user.Experience
import com.android.tubes_pbp.user.TubesDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SkillFragment : Fragment() {
    val db by lazy { activity?.let { TubesDB(it) } }
    private var _binding: FragmentSkillBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSkillBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()
        val secondFragment = InputExperienceFragment()
        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
        transaction.replace(R.id.layout_fragment, secondFragment)
        val layoutManager = LinearLayoutManager(context)
        val adapter : ExperienceAdapter = ExperienceAdapter(arrayListOf(),object : ExperienceAdapter.OnAdapterListener{
            override fun onClick(experience: Experience) {

            }
            override fun onEdit(experience: Experience) {
                bundle.putString("key","update")
                bundle.putInt("id",experience.id)
                bundle.putString("title",experience.title)
                bundle.putString("description",experience.description)
                secondFragment.arguments = bundle
                transaction.commit()
            }
        })

        val rvLowongan : RecyclerView = binding.rvExperience

        rvLowongan.layoutManager = layoutManager

        rvLowongan.setHasFixedSize(true)

        rvLowongan.adapter = adapter
        CoroutineScope(Dispatchers.IO).launch {
            val experiences = db?.experienceDao()?.getExperiences()
            Log.d("MainActivity","dbResponse: $experiences")
            withContext(Dispatchers.Main){
                adapter.setData(experiences!!)
            }
        }


        binding.btnAdd.setOnClickListener {
            bundle.putString("key","add")
            secondFragment.arguments = bundle
            transaction.commit()

        }


    }

}