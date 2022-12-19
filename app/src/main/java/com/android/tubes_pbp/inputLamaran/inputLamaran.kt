package com.android.tubes_pbp.inputLamaran

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentTransaction
import com.android.tubes_pbp.PDF.PDFMaker
import com.android.tubes_pbp.R
import com.android.tubes_pbp.SkillFragment
import com.android.tubes_pbp.TubesApi.TubesApi
import com.android.tubes_pbp.databinding.ActivityDetailLowonganBinding
import com.android.tubes_pbp.databinding.ActivityInputLamaranBinding
import com.android.tubes_pbp.entity.Lamaran
import com.android.tubes_pbp.user.Experience
import com.android.tubes_pbp.user.User
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_input_lamaran.*
import org.json.JSONObject
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.nio.charset.StandardCharsets

class inputLamaran : AppCompatActivity() {
    private lateinit var binding: ActivityInputLamaranBinding
    private var queue: RequestQueue? = null
    private val id = "idKey"
    private val myPreference = "myPref"
    var sharedPreferences: SharedPreferences? = null
    var idLamaran = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityInputLamaranBinding.inflate(layoutInflater)
        setContentView(binding.root)
        queue = Volley.newRequestQueue(this)

        sharedPreferences = this.getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        val idUser = sharedPreferences!!.getString(id,"20")!!.toInt()
        getUserById(idUser)


        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.btnConfirm.setOnClickListener {
            createExperiences(idUser)
        }

        binding.btnDelete.setOnClickListener {
            deleteExperiences(idLamaran)
        }
        binding.btnConfirm.setOnClickListener {
            if(idLamaran == 0){
                createExperiences(idUser)
                MotionToast.createColorToast(this,
                            "Lamaran Berhasil",
                            "Silahkan Tunggu Konfirmasi, Surat Telah terdownload",
                            MotionToastStyle.SUCCESS,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(this,R.font.poppins))
                PDFMaker.createPdf(this,binding.namaPerusahaan.text.toString(),binding.posisiPekerjaan.text.toString())
            }else{
                updateExperiences(idUser)
            }
        }
    }

    private fun createExperiences(idUser: Int){
        val lamaran = Lamaran(0,
            idUser,
            binding.namaPerusahaan.text.toString(),
            binding.posisiPekerjaan.text.toString(),
            "Sedang Menunggu",
            "11/12/12",
            binding.alasan.text.toString(),
            binding.skill.text.toString().toInt()
        )

        val stringRequest: StringRequest =
            object: StringRequest(Method.POST, TubesApi.ADD_URL_LAMARAN, Response.Listener { response ->
                val gson = Gson()

                val jsonObject = JSONObject(response)

                val jsonArray = jsonObject.getJSONObject("data")
                val lamaran = gson.fromJson(jsonArray.toString(), Lamaran::class.java)

                if(lamaran != null)
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()

                finish()


            }, Response.ErrorListener { error ->
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    if(error.networkResponse.statusCode == 400){
                        val jsonObject = JSONObject(responseBody)
                        val message = jsonObject.getJSONObject("message")
                        for(i in message.keys()){
                            if(i == "alasan"){
                                binding.alasan.error = message.getString(i)
                            }
                            if(i == "skill"){
                                binding.skill.error = message.getString(i)
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
                    val requestBody = gson.toJson(lamaran)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }
            }

        queue!!.add(stringRequest)
    }

    private fun getUserById(idUser: Int){
        val stringRequest : StringRequest = object:
            StringRequest(Method.GET, TubesApi.GET_BY_ID_URL_LAMARAN + idUser, Response.Listener { response ->

                val gson = Gson()

                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONArray("data")
                if(jsonArray.length() > 0){
                    var lamaran : Lamaran = gson.fromJson(jsonArray[0].toString(), Lamaran::class.java)
                    binding.btnCancel.visibility = View.INVISIBLE
                    binding.namaPerusahaan.setText(lamaran.nama_perusahaan)
                    binding.posisiPekerjaan.setText(lamaran.posisi)
                    binding.alasan.setText(lamaran.alasan)
                    binding.skill.setText(lamaran.skill.toString())
                    idLamaran = lamaran.id
                }else{
                    binding.btnDelete.visibility = View.INVISIBLE
                    val bundle = intent.getBundleExtra("detailLamaran")
                    binding.namaPerusahaan.setText(bundle?.getString("title",""))
                    binding.posisiPekerjaan.setText(bundle?.getString("posisi",""))
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

    private fun deleteExperiences(id: Int) {
        val stringRequest: StringRequest =
            object : StringRequest(
                Method.DELETE,
                TubesApi.DELETE_URL_LAMARAN + id,
                Response.Listener { response ->
                    val gson = Gson()

                    val jsonObject = JSONObject(response)

                    val jsonArray = jsonObject.getJSONObject("data")
                    val lamaran = gson.fromJson(jsonArray.toString(), Lamaran::class.java)

                    if (lamaran != null)
                        Toast.makeText(this, "Success Delete", Toast.LENGTH_SHORT).show()

                    finish()

                },
                Response.ErrorListener { error ->
                    try {
                        val responseBody =
                            String(error.networkResponse.data, StandardCharsets.UTF_8)
                        val errors = JSONObject(responseBody)
                        Toast.makeText(
                            this,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
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
            }
        queue!!.add(stringRequest)
    }

    private fun updateExperiences(idUser: Int){
        val lamaran = Lamaran(idLamaran,
            idUser,
            binding.namaPerusahaan.text.toString(),
            binding.posisiPekerjaan.text.toString(),
            "Sedang Menunggu",
            "11/12/12",
            binding.alasan.text.toString(),
            binding.skill.text.toString().toInt()
        )

        val stringRequest: StringRequest =
            object: StringRequest(Method.PUT, TubesApi.UPDATE_URL_LAMARAN + idLamaran, Response.Listener { response ->
                val gson = Gson()

                val jsonObject = JSONObject(response)

                val jsonArray = jsonObject.getJSONObject("data")
                val lamaran = gson.fromJson(jsonArray.toString(), Lamaran::class.java)

                if(lamaran != null)
                    Toast.makeText(this, "Success Update", Toast.LENGTH_SHORT).show()

                finish()


            }, Response.ErrorListener { error ->
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    if(error.networkResponse.statusCode == 400){
                        val jsonObject = JSONObject(responseBody)
                        val message = jsonObject.getJSONObject("message")
                        for(i in message.keys()){
                            if(i == "alasan"){
                                binding.alasan.error = message.getString(i)
                            }
                            if(i == "skill"){
                                binding.skill.error = message.getString(i)
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
                    val requestBody = gson.toJson(lamaran)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }
            }

        queue!!.add(stringRequest)
    }


}