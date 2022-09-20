package com.android.tubes_pbp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.tubes_pbp.databinding.ActivityLoginBinding
import com.android.tubes_pbp.databinding.ActivityRegisterBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    lateinit var lBundle : Bundle


    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if(intent.getBundleExtra("registerBundle")!=null){
            println("masukkk")
            lBundle = intent.getBundleExtra("registerBundle")!!
            binding.inputUsername.setText(lBundle.getString("username"))
            binding.inputPassword.setText(lBundle.getString("password"))
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            if((binding.inputUsername.text.toString() == "admin" && binding.inputPassword.text.toString() == "admin")){
                val moveHome = Intent(this, HomeActivity::class.java)
                startActivity(moveHome)
            }else{
                if(binding.inputUsername.text.toString().isEmpty()){
                    binding.layoutUsername.setError("Username Harus Diisi")
                }else{
                    binding.layoutUsername.setError("Username Salah")
                }

                if(binding.inputPassword.text.toString().isEmpty()){
                    binding.layoutPassword.setError("Password Harus Diisi")
                }else{
                    binding.layoutPassword.setError("Password Salah")
                }



            }
        }

    }
}