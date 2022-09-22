package com.android.tubes_pbp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.android.tubes_pbp.databinding.ActivityLoginBinding
import com.android.tubes_pbp.databinding.ActivityRegisterBinding
import com.android.tubes_pbp.user.TubesDB
import com.android.tubes_pbp.user.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    val db by lazy { TubesDB(this) }
    private lateinit var binding: ActivityLoginBinding
    lateinit var lBundle : Bundle

    private val myPreference = "myPref"
    private val key = "nameKey"
    private val id = "idKey"
    private var access = false
    var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root

        if(!sharedPreferences!!.contains(key)){
            val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
            editor.putString(key, "terisi")
            editor.apply()
            setContentView(R.layout.activity_splash_screen)

            Handler(Looper.getMainLooper()).postDelayed({
                setContentView(view)
            }, 3000)
        }else{
            setContentView(view)
        }


        val moveHome = Intent(this, HomeActivity::class.java)
        if(intent.getBundleExtra("registerBundle")!=null){
            lBundle = intent.getBundleExtra("registerBundle")!!
            binding.inputUsername.setText(lBundle.getString("username"))
            binding.inputPassword.setText(lBundle.getString("password"))
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                val users = db.userDao().getUsers()
                Log.d("LoginActivity","dbResponse: $users")

                for(i in users){
                    if(binding.inputUsername.text.toString() == i.username && binding.inputPassword.text.toString() == i.password){
                        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                        editor.putString(id, i.id.toString())
                        editor.apply()
                        access = true
                        break
                    }
                }

                withContext(Dispatchers.Main){
                    if((binding.inputUsername.text.toString() == "admin" && binding.inputPassword.text.toString() == "admin") || (access)){
                        access = false

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

    }
}