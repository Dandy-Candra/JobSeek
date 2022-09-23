package com.android.tubes_pbp

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentTransaction
import com.android.tubes_pbp.databinding.FragmentProfileBinding
import com.android.tubes_pbp.databinding.FragmentSkillBinding
import com.android.tubes_pbp.user.TubesDB
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    val db by lazy { activity?.let { TubesDB(it) } }
    private val id = "idKey"
    private val myPreference = "myPref"
    var sharedPreferences: SharedPreferences? = null
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnLogOut : Button = view.findViewById(R.id.btnLogout)
        sharedPreferences = activity?.getSharedPreferences(myPreference, Context.MODE_PRIVATE)

        CoroutineScope(Dispatchers.IO).launch {
            val user = db?.userDao()?.getUser(sharedPreferences!!.getString(id,"")!!.toInt())?.get(0)
            binding.username.setText(user?.username)
            binding.email.setText(user?.email)
            binding.noTelp.setText(user?.noTelp)
            binding.tglLahir.setText(user?.date)

            binding.editProfile.setOnClickListener {
                val moveEdit = Intent(activity, EditActivity::class.java)
                startActivity(moveEdit)
                activity?.finish()
            }
        }



        btnLogOut.setOnClickListener {
            activity?.let { it1 ->
                MaterialAlertDialogBuilder(it1)
                    .setTitle("Apakah Anda Ingin Keluar ?")
                    .setNegativeButton("No") { dialog, which ->
                        // Respond to negative button press
                    }
                    .setPositiveButton("yes") { dialog, which ->
                        val moveLogin = Intent(activity, LoginActivity::class.java)
                        startActivity(moveLogin)
                        activity?.finish()
                    }
                    .show()
            }
        }
    }


}