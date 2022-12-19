package com.android.tubes_pbp.ulasan

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.tubes_pbp.ExperienceAdapter
import com.android.tubes_pbp.R
import com.android.tubes_pbp.RVlowonganAdapter
import com.android.tubes_pbp.TubesApi.TubesApi
import com.android.tubes_pbp.databinding.ActivityShowUlasanBinding
import com.android.tubes_pbp.user.Experience
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets


class ShowUlasan : AppCompatActivity() {
    private val id = "idKey"
    private val myPreference = "myPref"
    var sharedPreferences: SharedPreferences? = null
    private var adapter: RVUlasanAdapter? = null
    private var queue: RequestQueue? = null

    private lateinit var binding: ActivityShowUlasanBinding

    private var idUser = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowUlasanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        queue = Volley.newRequestQueue(this)


        val intent = Intent(this,InputUpdateUlasan::class.java)
        adapter =
            RVUlasanAdapter( arrayOf(), object : RVUlasanAdapter.OnAdapterListener {
                override fun onDelete(ulasan: Ulasan) {
                    deleteUlasan(ulasan.id)
                }

                override fun onEdit(ulasan: Ulasan) {
                    val bundle = Bundle()
                    bundle.putInt("idCard", ulasan.id)
                    bundle.putString("kritik",ulasan.kritik)
                    bundle.putString("saran",ulasan.saran)
                    bundle.putInt("rating",ulasan.rating)

                    intent.putExtra("bundleCard",bundle)
                    startActivity(intent)
                }

            })

        binding.btnAdd.setOnClickListener {
            startActivity(intent)
        }
    }

    private fun getUlasans(idUser: Int){

        val stringRequest : StringRequest = object:
            StringRequest(Method.GET, TubesApi.GET_BY_ID_URL_ULASAN + idUser, Response.Listener { response ->
                val gson = Gson()

                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONArray("data")
                var ulasans : Array<Ulasan> = gson.fromJson(jsonArray.toString(), Array<Ulasan>::class.java)

                val layoutManager = LinearLayoutManager(this)

                val rvUlasan: RecyclerView = binding.rvUlasan

                rvUlasan.layoutManager = layoutManager

                rvUlasan.setHasFixedSize(true)

                rvUlasan.adapter = adapter

                adapter!!.setData(ulasans)

            }, Response.ErrorListener { error ->

                try {
                    val responseBody =
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
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

        }
        queue!!.add(stringRequest)
    }

    // delete
    private fun deleteUlasan(id: Int){
        val stringRequest : StringRequest = object:
            StringRequest(Method.DELETE, TubesApi.DELETE_URL_ULASAN + id, Response.Listener { response ->
                val gson = Gson()

                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONObject("data")
                var ulasans : Ulasan = gson.fromJson(jsonArray.toString(), Ulasan::class.java)

                if (ulasans != null){
                    Toast.makeText(this, "Delete Success", Toast.LENGTH_SHORT).show()
                    getUlasans(idUser)
                } else {
                    Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show()
                }

            }, Response.ErrorListener { error ->

                try {
                    val responseBody =
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
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

        }
        queue!!.add(stringRequest)
    }

    override fun onStart() {
        super.onStart()
        sharedPreferences = this.getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        idUser = sharedPreferences!!.getString(id, "20")!!.toInt()
        getUlasans(idUser)
    }
}