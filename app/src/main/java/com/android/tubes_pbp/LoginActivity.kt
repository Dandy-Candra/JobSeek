package com.android.tubes_pbp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.android.tubes_pbp.TubesApi.TubesApi
import com.android.tubes_pbp.databinding.ActivityLoginBinding
import com.android.tubes_pbp.user.User
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets

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

//        if(!sharedPreferences!!.contains(key)){
//            val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
//            editor.putString(key, "terisi")
//            editor.apply()
//            setContentView(R.layout.activity_splash_screen)
//
//            Handler(Looper.getMainLooper()).postDelayed({
//                setContentView(view)
//            }, 3000)
//        }else{
            setContentView(view)
//        }


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

        binding.layoutUsername.error = null
        binding.layoutPassword.error = null


            val stringRequest: StringRequest = object :
                StringRequest(Method.POST, TubesApi.LOGIN_URL_USER, Response.Listener { response ->
                    val gson = Gson()

                    val jsonObject = JSONObject(response)
                    val jsonArray = jsonObject.getJSONObject("user")
                    val user = gson.fromJson(jsonArray.toString(), User::class.java)

                    val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
                    editor.putString(id, user.id.toString())
                    editor.putString(name, user.username)
                    editor.apply()

                    startActivity(moveHome)
                    finish()


                }, Response.ErrorListener { error ->
                    try {
                        val responseBody =
                            String(error.networkResponse.data, StandardCharsets.UTF_8)
                        if(error.networkResponse.statusCode == 401){
                            binding.layoutUsername.setError("Username Salah")
                            binding.layoutPassword.setError("Password Salah")
                        }else if(error.networkResponse.statusCode == 400){
                            val jsonObject = JSONObject(responseBody)
                            val jsonObject1 = jsonObject.getJSONObject("message")
                            for(i in jsonObject1.keys()){

                                if(i == "username"){
                                    binding.layoutUsername.error = jsonObject1.getJSONArray(i).getString(0)
                                }
                                if(i == "password"){
                                    binding.layoutPassword.error = jsonObject1.getJSONArray(i).getString(0)
                                }
                            }
                        }else{
                            val errors = JSONObject(responseBody)
                            Toast.makeText(this, errors.getString("message"), Toast.LENGTH_SHORT).show()
                        }

                    } catch (e: Exception) {
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