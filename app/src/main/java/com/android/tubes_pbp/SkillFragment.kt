package com.android.tubes_pbp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.tubes_pbp.databinding.FragmentSkillBinding
import com.android.tubes_pbp.user.Experience
import com.android.tubes_pbp.user.TubesDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SkillFragment : Fragment() {
    val db by lazy { activity?.let { TubesDB(it) } }
    private val id = "idKey"
    private var idNotif = 1
    private val myPreference = "myPref"
    var sharedPreferences: SharedPreferences? = null
    private var _binding: FragmentSkillBinding? = null
    private val binding get() = _binding!!
    private val CHANNEL_ID_2 = "channel_notification_skill"

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
        sharedPreferences = activity?.getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        val idUser = sharedPreferences!!.getString(id, "")!!.toInt()
        val bundle = Bundle()
        val secondFragment = InputExperienceFragment()
        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
        transaction.replace(R.id.layout_fragment, secondFragment)
        val layoutManager = LinearLayoutManager(context)
        val adapter: ExperienceAdapter =
            ExperienceAdapter(arrayListOf(), object : ExperienceAdapter.OnAdapterListener {
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

        val rvLowongan: RecyclerView = binding.rvExperience

        rvLowongan.layoutManager = layoutManager

        rvLowongan.setHasFixedSize(true)

        rvLowongan.adapter = adapter
        CoroutineScope(Dispatchers.IO).launch {
            val experiences = db?.experienceDao()?.getExperiencesById(idUser)
            Log.d("MainActivity", "dbResponse: $experiences")
            withContext(Dispatchers.Main) {
                adapter.setData(experiences!!)
            }
        }


        binding.btnAdd.setOnClickListener {
            bundle.putString("key", "add")
            secondFragment.arguments = bundle
            transaction.commit()

        }


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