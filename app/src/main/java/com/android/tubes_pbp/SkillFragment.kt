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
import android.widget.SearchView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.tubes_pbp.TubesApi.TubesApi
import com.android.tubes_pbp.databinding.FragmentSkillBinding
import com.android.tubes_pbp.user.Experience
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets


class SkillFragment : Fragment() {

    private val id = "idKey"
    private var idNotif = 1
    private val myPreference = "myPref"
    var sharedPreferences: SharedPreferences? = null
    private var _binding: FragmentSkillBinding? = null
    private val binding get() = _binding!!
    private val CHANNEL_ID_2 = "channel_notification_skill"
    private var queue: RequestQueue? = null
    private var adapter: ExperienceAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSkillBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createNotificationChannel()
        //this on fragment

        queue = Volley.newRequestQueue(requireActivity())
        sharedPreferences = activity?.getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        val idUser = sharedPreferences!!.getString(id, "")!!.toInt()
        val bundle = Bundle()
        val secondFragment = InputExperienceFragment()
        val transaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.layout_fragment, secondFragment)
        val layoutManager = LinearLayoutManager(context)
        adapter =
            ExperienceAdapter(listOf(), object : ExperienceAdapter.OnAdapterListener {
                override fun onClick(experience: Experience) {
                    sendNotification(idNotif, experience.title, experience.description)
                    idNotif++
                    println(idNotif)
                }

                override fun onEdit(experience: Experience) {
                    bundle.putString("key", "update")
                    bundle.putInt("id", experience.id)
                    bundle.putString("title", experience.title)
                    bundle.putString("description", experience.description)
                    secondFragment.arguments = bundle
                    transaction.commit()
                }
            })

        binding.svExperience?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter!!.filter.filter(p0)
                return false
            }
        })

        val rvLowongan: RecyclerView = binding.rvExperience

        rvLowongan.layoutManager = layoutManager

        rvLowongan.setHasFixedSize(true)

        rvLowongan.adapter = adapter
        getExperiences(idUser)

        binding.srExperience?.setOnRefreshListener (SwipeRefreshLayout.OnRefreshListener { getExperiences(idUser) })



        binding.btnAdd.setOnClickListener {
            bundle.putString("key", "add")
            secondFragment.arguments = bundle
            transaction.commit()

        }

    }

    private fun getExperiences(idUser: Int){
        binding.srExperience!!.isRefreshing = true
        val stringRequest : StringRequest = object:
            StringRequest(Method.GET, TubesApi.GET_BY_ID_URL_EXPERIENCE + idUser, Response.Listener { response ->
                val gson = Gson()

                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONArray("data")
                var experience : Array<Experience> = gson.fromJson(jsonArray.toString(), Array<Experience>::class.java)

                adapter!!.setData(experience)
                adapter!!.filter.filter(binding.svExperience!!.query)
                binding.srExperience!!.isRefreshing = false
            }, Response.ErrorListener { error ->
                binding.srExperience!!.isRefreshing = false
                try {
                    val responseBody =
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(requireActivity(), errors.getString("message"), Toast.LENGTH_SHORT).show()
                } catch (e: Exception){
                    Toast.makeText(requireActivity(), e.message, Toast.LENGTH_SHORT).show()
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



    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "Notification Description"

            val channel2 = NotificationChannel(
                CHANNEL_ID_2,
                name,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel2)
        }
    }


    private fun sendNotification(id: Int, title: String, deskripsi: String) {

        val SUMMARY_ID = 0
        val GROUP_KEY_WORK_EMAIL = "skill_notif"

        val builder = NotificationCompat.Builder(this.requireActivity(), CHANNEL_ID_2)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle(title)
            .setColor(Color.BLUE)
            .setContentText(deskripsi)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setGroup(GROUP_KEY_WORK_EMAIL)

        val summaryNotification = NotificationCompat.Builder(this.requireActivity(), CHANNEL_ID_2)
            //set content text to support devices running API level < 24
            .setSmallIcon(R.drawable.ic_baseline_info_24)
            //build summary info into InboxStyle template
            //specify which group this notification belongs to
            .setGroup(GROUP_KEY_WORK_EMAIL)
            //set this notification as the summary for the group
            .setGroupSummary(true)


        with(NotificationManagerCompat.from(this.requireActivity())) {
            notify(SUMMARY_ID, summaryNotification.build())
            notify(id, builder.build())


        }
    }

}