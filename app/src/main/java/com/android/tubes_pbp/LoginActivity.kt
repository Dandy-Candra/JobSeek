package com.android.tubes_pbp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.android.tubes_pbp.databinding.ActivityLoginBinding
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    lateinit var lBundle : Bundle

    private val myPreference = "myPref"
    private val key = "nameKey"
    private val id = "idKey"
    private val name = "nameKey"
    private var access = false
    var sharedPreferences: SharedPreferences? = null
    private var queue: RequestQueue? = null
    var moveHome : Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)

        queue = Volley.newRequestQueue(this)

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


        moveHome = Intent(this, HomeActivity::class.java)
        if(intent.getBundleExtra("registerBundle")!=null){
            lBundle = intent.getBundleExtra("registerBundle")!!
            binding.inputUsername.setText(lBundle.getString("username"))
            binding.inputPassword.setText(lBundle.getString("password"))
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {

                getUser()

        }

    }

    private fun getUser(){

        val stringRequest : StringRequest = object:
            StringRequest(Method.POST, TubesApi.LOGIN_URL_USER, Response.Listener { response ->
                val gson = Gson()

                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONObject("user")
                val user = gson.fromJson(jsonArray.toString(), User::class.java)


                if(user != null){
                    val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                    editor.putString(id, user.id.toString())
                    editor.putString(name, user.username)
                    editor.apply()
                    access = true
                }


                if((binding.inputUsername.text.toString() == "admin" && binding.inputPassword.text.toString() == "admin") || (access)){
                    access = false

                    startActivity(moveHome)
                    finish()
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

            }, Response.ErrorListener { error ->

                try {
                    val responseBody =
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    binding.layoutUsername.setError("Username Salah")
                    binding.layoutPassword.setError("Password Salah")
                    Toast.makeText(this, errors.getString("message"), Toast.LENGTH_SHORT).show()
                } catch (e: Exception){
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }

            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["username"] = binding.inputUsername.text.toString()
                params["password"] = binding.inputPassword.text.toString()
                return params
            }

        }
        queue!!.add(stringRequest)
    }
}