package com.android.tubes_pbp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.android.tubes_pbp.databinding.FragmentInputExperienceBinding
import com.android.tubes_pbp.user.Experience
import com.android.tubes_pbp.user.TubesDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class InputExperienceFragment : Fragment() {
    val db by lazy { activity?.let { TubesDB(it) } }
    private val id = "idKey"
    private val myPreference = "myPref"
    var sharedPreferences: SharedPreferences? = null
    private var _binding: FragmentInputExperienceBinding? = null
    private val binding get() = _binding!!
    private val CHANNEL_ID_SAVE = "channel_notification_save"
    private val notificationId = 103


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputExperienceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createNotificationChannel()
        sharedPreferences = activity?.getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        val idUser = sharedPreferences!!.getString(id, "")!!.toInt()
        if (arguments?.getString("key") == "update") {
            binding.editTitle.setText(arguments?.getString("title"))
            binding.editDescription.setText(arguments?.getString("description"))
            val id = requireArguments().getInt("id")
            binding.buttonSave.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    db?.experienceDao()?.updateExperience(
                        Experience(
                            id,
                            binding.editTitle.text.toString(),
                            binding.editDescription.text.toString(),
                            idUser
                        )
                    )

                    withContext(Dispatchers.Main) {
                        val secondFragment = SkillFragment()
                        val transaction: FragmentTransaction =
                            requireFragmentManager().beginTransaction()
                        transaction.replace(R.id.layout_fragment, secondFragment)
                        transaction.commit()
                    }
                }
            }

            binding.buttonDelete.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    db?.experienceDao()?.deleteExperience(
                        Experience(
                            id,
                            arguments?.getString("title")!!,
                            arguments?.getString("description")!!,
                            idUser
                        )
                    )

                    withContext(Dispatchers.Main) {
                        val secondFragment = SkillFragment()
                        val transaction: FragmentTransaction =
                            requireFragmentManager().beginTransaction()
                        transaction.replace(R.id.layout_fragment, secondFragment)
                        transaction.commit()
                    }
                }
            }
        } else {
            binding.buttonDelete.visibility = View.GONE
            binding.buttonSave.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    db?.experienceDao()?.addExperience(
                        Experience(
                            0,
                            binding.editTitle.text.toString(),
                            binding.editDescription.text.toString(),
                            idUser
                        )
                    )

                    withContext(Dispatchers.Main) {
                        sendNotification2(
                            binding.editTitle.text.toString(),
                            binding.editDescription.text.toString()
                        )
                        val secondFragment = SkillFragment()
                        val transaction: FragmentTransaction =
                            requireFragmentManager().beginTransaction()
                        transaction.replace(R.id.layout_fragment, secondFragment)
                        transaction.commit()
                    }
                }
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Save"
            val descriptionText = "Notification Description"

            val channel1 = NotificationChannel(
                CHANNEL_ID_SAVE,
                name,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = descriptionText
            }


            val notificationManager: NotificationManager =
                activity?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
        }
    }

    private fun sendNotification2(title: String, description: String) {

        val builder = NotificationCompat.Builder(this.requireActivity(), CHANNEL_ID_SAVE)
            .setSmallIcon(R.drawable.ic_baseline_info_24)
            .setContentTitle("Berhasil Menyimpan Experience!")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setColor(Color.BLUE)
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(description)
                    .setBigContentTitle(title)
                    .setSummaryText("Save Succes")
            )

        with(NotificationManagerCompat.from(this.requireActivity())) {
            notify(notificationId, builder.build())
        }
    }


}