package com.android.tubes_pbp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.res.ResourcesCompat
import com.android.tubes_pbp.PDF.PDFMaker
import com.android.tubes_pbp.databinding.ActivityDetailLowonganBinding
import com.android.tubes_pbp.databinding.ActivityRegisterBinding
import com.android.tubes_pbp.inputLamaran.inputLamaran
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class DetailLowongan : AppCompatActivity() {
    private lateinit var binding: ActivityDetailLowonganBinding
    private val CHANNEL_ID_SAVE = "channel_notification_save"
    private val notificationId = 103
    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityDetailLowonganBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()

        val bundle = intent.getBundleExtra("detailLowongan")
        binding.title.setText(bundle?.getString("title",""))
        binding.posisi.setText(bundle?.getString("posisi",""))
        binding.description.setText(bundle?.getString("description",""))
        binding.image.setImageResource(bundle?.getInt("photo",0)!!)

        binding.btnLamar.setOnClickListener {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Apakah Anda Ingin Melamar ?")
                    .setNegativeButton("No") { dialog, which ->
                        // Respond to negative button press
                    }
                    .setPositiveButton("yes") { dialog, which ->
                        val intent = Intent(this, inputLamaran::class.java)
                        val bundle = Bundle()
                        bundle.putString("title",binding.title.text.toString())
                        bundle.putString("posisi",binding.posisi.text.toString())
                        bundle.putString("description",binding.description.text.toString())
                        intent.putExtra("detailLamaran",bundle)
                        startActivity(intent)


//                        sendNotification2(binding.title.text.toString(),binding.description.text.toString())
//                        MotionToast.createColorToast(this,
//                            "Lamaran Berhasil",
//                            "Silahkan Tunggu Konfirmasi, Surat Telah terdownload",
//                            MotionToastStyle.SUCCESS,
//                            MotionToast.GRAVITY_BOTTOM,
//                            MotionToast.LONG_DURATION,
//                            ResourcesCompat.getFont(this,R.font.poppins))
//                        PDFMaker.createPdf(this,binding.title.text.toString(),binding.description.text.toString())
                        finish()
                    }
                    .show()

        }

    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Save"
            val descriptionText = "Notification Description"

            val channel1 = NotificationChannel(CHANNEL_ID_SAVE,name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }


            val notificationManager: NotificationManager =
                this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
        }
    }

    private fun sendNotification2(title : String, description : String){

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_SAVE)
            .setSmallIcon(R.drawable.ic_baseline_info_24)
            .setContentTitle("Berhasil Menyimpan Experience!")
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setColor(Color.BLUE)
            .setStyle(
                NotificationCompat.BigTextStyle().bigText("Deskripsi : "+description)
                    .setBigContentTitle("Nama Perusahaan : "+title)
                    .setSummaryText("Hiring Succes"))

        with(NotificationManagerCompat.from(this)){
            notify(notificationId, builder.build())
        }
    }



}