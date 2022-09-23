package com.android.tubes_pbp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.tubes_pbp.databinding.FragmentInputExperienceBinding
import com.android.tubes_pbp.entity.lowongan
import com.android.tubes_pbp.user.Experience
import com.android.tubes_pbp.user.TubesDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class InputExperienceFragment : Fragment() {
    val db by lazy { activity?.let { TubesDB(it) } }
    private val id = "idKey"
    private val myPreference = "myPref"
    var sharedPreferences: SharedPreferences? = null
    private var _binding: FragmentInputExperienceBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInputExperienceBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = activity?.getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        val idUser = sharedPreferences!!.getString(id,"")!!.toInt()
        if(arguments?.getString("key")=="update"){
            binding.editTitle.setText(arguments?.getString("title"))
            binding.editDescription.setText(arguments?.getString("description"))
            val id = requireArguments().getInt("id")
            binding.buttonSave.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    db?.experienceDao()?.updateExperience(Experience(id,binding.editTitle.text.toString(),binding.editDescription.text.toString(),idUser))

                    withContext(Dispatchers.Main){
                        val secondFragment = SkillFragment()
                        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
                        transaction.replace(R.id.layout_fragment, secondFragment)
                        transaction.commit()
                    }
                }
            }

            binding.buttonDelete.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    db?.experienceDao()?.deleteExperience(Experience(id,
                        arguments?.getString("title")!!, arguments?.getString("description")!!,idUser
                    ))

                    withContext(Dispatchers.Main){
                        val secondFragment = SkillFragment()
                        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
                        transaction.replace(R.id.layout_fragment, secondFragment)
                        transaction.commit()
                    }
                }
            }
        }else{
            binding.buttonDelete.visibility = View.GONE
            binding.buttonSave.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    db?.experienceDao()?.addExperience(Experience(0,binding.editTitle.text.toString(),binding.editDescription.text.toString(),idUser))

                    withContext(Dispatchers.Main){
                        val secondFragment = SkillFragment()
                        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
                        transaction.replace(R.id.layout_fragment, secondFragment)
                        transaction.commit()
                    }
                }
            }
        }
    }

}