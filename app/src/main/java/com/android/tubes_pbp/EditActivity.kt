package com.android.tubes_pbp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.tubes_pbp.TubesApi.TubesApi
import com.android.tubes_pbp.databinding.ActivityEditBinding
import com.android.tubes_pbp.user.User
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class EditActivity : AppCompatActivity() {
    val db by lazy { TubesDB(this) }
    private lateinit var binding: ActivityEditBinding
    private val id = "idKey"
    private val myPreference = "myPref"
    private var temp : String? = null
    private var access = true
    var sharedPreferences: SharedPreferences? = null
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        queue = Volley.newRequestQueue(this)


        binding = ActivityEditBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        val id = sharedPreferences!!.getString(id,"")!!.toInt()
        loadData()
        val intent = Intent(this, HomeActivity::class.java)

        binding.topAppBar.setOnMenuItemClickListener { menuItem->
            when(menuItem.itemId){
                R.id.Save -> {
                    updateUser(id)
                    true
                }
                else -> false
            }

        }




    }

    fun loadData(){
        val lBundle = intent.getBundleExtra("editBundle")!!
        binding.inputUsername.setText(lBundle.getString("name"))
        binding.inputEmail.setText(lBundle.getString("email"))
        binding.inputNomorTelepon.setText(lBundle.getString("phone"))
        binding.inputTanggalLahir.setText(lBundle.getString("tgllahir"))
    }

    private fun updateUser(id: Int ){
        val user = User(
            id,
            binding.inputUsername.text.toString(),
            binding.inputEmail.text.toString(),
            "11",
            binding.inputTanggalLahir.text.toString(),
            binding.inputNomorTelepon.text.toString()
        )

        val stringRequest: StringRequest =
            object: StringRequest(Method.PUT, TubesApi.UPDATE_URL_USER + id, Response.Listener { response ->
                val jsonObject = JSONObject(response)

                val status = jsonObject.getInt("status")

                if(status == 1){
                    Toast.makeText(this, "Update User Success", Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    binding.layoutUsername.error = "Username already exist"
                }



            }, Response.ErrorListener { error ->
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        this,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception){
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
            }){
                @Throws(AuthFailureError::class)
                override fun getHeaders(): Map<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Accept"] = "application/json"
                    return headers
                }

                @Throws(AuthFailureError::class)
                override fun getBody(): ByteArray {
                    val gson = Gson()
                    val requestBody = gson.toJson(user)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }
            }

        queue!!.add(stringRequest)
    }
}