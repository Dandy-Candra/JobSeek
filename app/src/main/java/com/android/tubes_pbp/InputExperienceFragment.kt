package com.android.tubes_pbp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.android.tubes_pbp.TubesApi.TubesApi
import com.android.tubes_pbp.databinding.FragmentInputExperienceBinding
import com.android.tubes_pbp.user.Experience
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets


class InputExperienceFragment : Fragment() {
    private val id = "idKey"
    private val myPreference = "myPref"
    var sharedPreferences: SharedPreferences? = null
    private var _binding: FragmentInputExperienceBinding? = null
    private val binding get() = _binding!!
    private val CHANNEL_ID_SAVE = "channel_notification_save"
    private val notificationId = 103
    private var queue: RequestQueue? = null
    private var fActivity: FragmentActivity? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInputExperienceBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createNotificationChannel()
        fActivity = requireActivity()
        queue = Volley.newRequestQueue(requireActivity())
        sharedPreferences = activity?.getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        val idUser = sharedPreferences!!.getString(id,"20")!!.toInt()
        if(arguments?.getString("key")=="update"){
            binding.title.setText("Update Experience")
            binding.editTitle.setText(arguments?.getString("title"))
            binding.editDescription.setText(arguments?.getString("description"))
            val id = requireArguments().getInt("id")
            binding.buttonSave.setOnClickListener {

                updateExperiences(id,idUser)
                val secondFragment = SkillFragment()
                val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.layout_fragment, secondFragment)
                transaction.commit()
            }

            binding.buttonDelete.setOnClickListener {

                deleteExperiences(id)

            }
        }else{
            binding.title.setText("Add Experience")
            binding.buttonDelete.visibility = View.GONE
            binding.buttonSave.setOnClickListener {

                createExperiences(idUser)

                sendNotification2(binding.editTitle.text.toString(),binding.editDescription.text.toString())

            }
        }


    }

    private fun createExperiences(idUser: Int){
        val experience = Experience(
            0,
            binding.editTitle.text.toString(),
            binding.editDescription.text.toString(),
            idUser
        )

        val stringRequest: StringRequest =
            object: StringRequest(Method.POST, TubesApi.ADD_URL_EXPERIENCE, Response.Listener { response ->
                val gson = Gson()

                val jsonObject = JSONObject(response)

                val jsonArray = jsonObject.getJSONObject("data")
                val experience = gson.fromJson(jsonArray.toString(), Experience::class.java)

                if(experience != null)
                    Toast.makeText(fActivity, "Success", Toast.LENGTH_SHORT).show()

                val secondFragment = SkillFragment()
                val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.layout_fragment, secondFragment)
                transaction.commit()


            }, Response.ErrorListener { error ->
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    if(error.networkResponse.statusCode == 400){
                        val jsonObject = JSONObject(responseBody)
                        val message = jsonObject.getJSONObject("message")
                        for(i in message.keys()){
                            if(i == "title"){
                                binding.editTitle.error = message.getJSONArray(i).getString(0)
                            }
                            if(i == "description"){
                                binding.editDescription.error = message.getJSONArray(i).getString(0)
                            }
                        }
                    }else {
                        val errors = JSONObject(responseBody)
                        Toast.makeText(
                            fActivity,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception){
                    Toast.makeText(fActivity, e.message, Toast.LENGTH_SHORT).show()
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
                    val requestBody = gson.toJson(experience)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }
            }

        queue!!.add(stringRequest)
    }

    private fun updateExperiences(id: Int ,idUser: Int){
        val experience = Experience(
            id,
            binding.editTitle.text.toString(),
            binding.editDescription.text.toString(),
            idUser
        )

        val stringRequest: StringRequest =
            object: StringRequest(Method.PUT, TubesApi.UPDATE_URL_EXPERIENCE + id, Response.Listener { response ->
                val gson = Gson()

                val jsonObject = JSONObject(response)

                val jsonArray = jsonObject.getJSONObject("data")
                val experience = gson.fromJson(jsonArray.toString(), Experience::class.java)

                if(experience != null)
                    Toast.makeText(fActivity, "Success Update", Toast.LENGTH_SHORT).show()


            }, Response.ErrorListener { error ->
                try{
                    val responseBody = String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        fActivity,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception){
                    Toast.makeText(fActivity, e.message, Toast.LENGTH_SHORT).show()
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
                    val requestBody = gson.toJson(experience)
                    return requestBody.toByteArray(StandardCharsets.UTF_8)
                }

                override fun getBodyContentType(): String {
                    return "application/json"
                }
            }

        queue!!.add(stringRequest)
    }

    private fun deleteExperiences(id: Int) {
        val stringRequest: StringRequest =
            object : StringRequest(
                Method.DELETE,
                TubesApi.DELETE_URL_EXPERIENCE + id,
                Response.Listener { response ->
                    val gson = Gson()

                    val jsonObject = JSONObject(response)

                    val jsonArray = jsonObject.getJSONObject("data")
                    val experience = gson.fromJson(jsonArray.toString(), Experience::class.java)

                    if (experience != null)
                        Toast.makeText(fActivity, "Success Delete", Toast.LENGTH_SHORT).show()

                    val secondFragment = SkillFragment()
                    val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.layout_fragment, secondFragment)
                    transaction.commit()

                },
                Response.ErrorListener { error ->
                    try {
                        val responseBody =
                            String(error.networkResponse.data, StandardCharsets.UTF_8)
                        val errors = JSONObject(responseBody)
                        Toast.makeText(
                            fActivity,
                            errors.getString("message"),
                            Toast.LENGTH_SHORT
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(fActivity, e.message, Toast.LENGTH_SHORT).show()
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

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Save"
            val descriptionText = "Notification Description"

            val channel1 = NotificationChannel(CHANNEL_ID_SAVE,name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }


            val notificationManager: NotificationManager =
                activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
        }
    }

    private fun sendNotification2(title : String, description : String){

        val builder = NotificationCompat.Builder(this.requireActivity(), CHANNEL_ID_SAVE)
            .setSmallIcon(R.drawable.ic_baseline_info_24)
            .setContentTitle("Berhasil Menyimpan Experience!")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setColor(Color.BLUE)
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(description)
                .setBigContentTitle(title)
                .setSummaryText("Save Succes"))

        with(NotificationManagerCompat.from(this.requireActivity())){
            notify(notificationId, builder.build())
        }
    }


}