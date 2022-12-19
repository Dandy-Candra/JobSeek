package com.android.tubes_pbp.ulasan

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.android.tubes_pbp.R
import com.android.tubes_pbp.SkillFragment
import com.android.tubes_pbp.TubesApi.TubesApi
import com.android.tubes_pbp.databinding.ActivityInputUpdateUlasanBinding
import com.android.tubes_pbp.databinding.ActivityLoginBinding
import com.android.tubes_pbp.user.Experience
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class InputUpdateUlasan : AppCompatActivity() {
    private val id = "idKey"
    private lateinit var binding: ActivityInputUpdateUlasanBinding
    private val myPreference = "myPref"
    var sharedPreferences: SharedPreferences? = null
    private var queue: RequestQueue? = null
    private var idCard = 0
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityInputUpdateUlasanBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()
        queue = Volley.newRequestQueue(this)

        sharedPreferences = this.getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        val idUser = sharedPreferences!!.getString(id,"20")!!.toInt()


        if(intent.getBundleExtra("bundleCard")!=null){
            val bundle = intent.getBundleExtra("bundleCard")
            binding.tvKritik.setText(bundle?.getString("kritik"))
            binding.tvSaran.setText(bundle?.getString("saran"))
            binding.ratingBar.rating = bundle?.getInt("rating")!!.toFloat()
            idCard = bundle!!.getInt("idCard")

        }

        binding.btnConfirm.setOnClickListener {
            if(idCard != 0){
                updateUlasan(idCard, idUser)

            }else{
                createUlasan(idUser)
            }
        }

    }

    private fun createUlasan(idUser: Int){
        val ulasan = Ulasan(
            0,
            idUser,
            binding.tvKritik.text.toString(),
            binding.tvSaran.text.toString(),
            binding.ratingBar.rating.toInt()
        )

        val stringRequest: StringRequest =
            object: StringRequest(Method.POST, TubesApi.ADD_URL_ULASAN, Response.Listener { response ->
                val gson = Gson()

                val jsonObject = JSONObject(response)

                val jsonArray = jsonObject.getJSONObject("data")
                val ulasan = gson.fromJson(jsonArray.toString(), Experience::class.java)

                if(ulasan != null)
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()

                finish()


            }, Response.ErrorListener { error ->
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    if(error.networkResponse.statusCode == 400){
                        val jsonObject = JSONObject(responseBody)
                        val message = jsonObject.getJSONObject("message")
                        for(i in message.keys()){
                            if(i == "kritik"){
                                binding.tvKritik.error = message.getJSONArray(i).getString(0)
                            }
                            if(i == "saran"){
                                binding.tvSaran.error = message.getJSONArray(i).getString(0)
                            }
                        }
                    }else {
                        val errors = JSONObject(responseBody)
                        Toast.makeText(
                            this,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
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
                    val requestBody = gson.toJson(ulasan)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }
            }

        queue!!.add(stringRequest)
    }

    private fun updateUlasan(idCard:Int,idUser: Int){
        val ulasan = Ulasan(
            idCard,
            idUser,
            binding.tvKritik.text.toString(),
            binding.tvSaran.text.toString(),
            binding.ratingBar.rating.toInt()
        )

        val stringRequest: StringRequest =
            object: StringRequest(Method.PUT, TubesApi.UPDATE_URL_ULASAN + idCard, Response.Listener { response ->
                val gson = Gson()

                val jsonObject = JSONObject(response)

                val jsonArray = jsonObject.getJSONObject("data")
                val ulasan = gson.fromJson(jsonArray.toString(), Experience::class.java)

                if(ulasan != null)
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()

                finish()


            }, Response.ErrorListener { error ->
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    if(error.networkResponse.statusCode == 400){
                        val jsonObject = JSONObject(responseBody)
                        val message = jsonObject.getJSONObject("message")
                        for(i in message.keys()){
                            if(i == "kritik"){
                                binding.tvKritik.error = message.getJSONArray(i).getString(0)
                            }
                            if(i == "saran"){
                                binding.tvSaran.error = message.getJSONArray(i).getString(0)
                            }
                        }
                    }else {
                        val errors = JSONObject(responseBody)
                        Toast.makeText(
                            this,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
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
                    val requestBody = gson.toJson(ulasan)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }
            }

        queue!!.add(stringRequest)
    }


}