package com.android.tubes_pbp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.tubes_pbp.databinding.FragmentProfileBinding
import com.android.tubes_pbp.feature_login.LoginActivity
import com.android.tubes_pbp.user.TubesDB
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    val db by lazy { TubesDB(requireContext()) }
    private val id = "idKey"
    private val myPreference = "myPref"
    private lateinit var sharedPreferences: SharedPreferences
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnLogOut = binding.btnLogout
        sharedPreferences =
            requireActivity().getSharedPreferences(myPreference, Context.MODE_PRIVATE)

        CoroutineScope(Dispatchers.IO).launch {

            val userId = sharedPreferences.getString(id, "0")?.toIntOrNull() ?: 0
            val user = db.userDao().getUser(userId) ?: return@launch

            binding.username.text = user.username
            binding.email.text = user.email
            binding.noTelp.text = user.noTelp
            binding.tglLahir.text = user.date

            binding.editProfile.setOnClickListener {
                val moveEdit = Intent(requireContext(), EditActivity::class.java)
                startActivity(moveEdit)
                // activity?.finish()
            }
        }



        btnLogOut.setOnClickListener {
            activity?.let { it1 ->
                MaterialAlertDialogBuilder(it1)
                    .setTitle("Apakah Anda Ingin Keluar ?")
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .setPositiveButton("yes") { dialog, which ->
                        val moveLogin = Intent(requireContext(), LoginActivity::class.java)
                        requireContext().startActivity(moveLogin)
                        requireActivity().finish()
                    }
                    .show()
            }
        }
    }

}