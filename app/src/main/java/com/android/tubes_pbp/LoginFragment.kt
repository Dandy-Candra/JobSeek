package com.android.tubes_pbp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class LoginFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.activity_login, container, false)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button : Button = view.findViewById(R.id.btnRegister)
        val btnLogin : Button = view.findViewById(R.id.btnLogin)
        val etUsername : TextInputEditText = view.findViewById(R.id.inputUsername)
        val etPassword : TextInputEditText = view.findViewById(R.id.inputPassword)


        etUsername.setText(arguments?.getString("username"))
        etPassword.setText(arguments?.getString("password"))



        button.setOnClickListener {
            val secondFragment = RegisterFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.layout_fragment, secondFragment)
            transaction.commit()
        }

        btnLogin.setOnClickListener {
            if(etUsername.text.toString() == "admin" && etPassword.text.toString() == "admin"){
                val moveHome = Intent(activity, HomeActivity::class.java)
                startActivity(moveHome)
            }else{
                etUsername.error ="Username / Password salah"
            }
        }
    }



}