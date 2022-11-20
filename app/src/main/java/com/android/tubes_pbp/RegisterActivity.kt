package com.android.tubes_pbp

import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.android.tubes_pbp.TubesApi.TubesApi
import com.android.tubes_pbp.databinding.ActivityRegisterBinding
import com.android.tubes_pbp.user.Experience
import com.android.tubes_pbp.user.TubesDB
import com.android.tubes_pbp.user.User
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.nio.charset.StandardCharsets
import java.util.*

class RegisterActivity : AppCompatActivity() {
    val db by lazy { TubesDB(this) }
    private var userId: Int = 0
    private lateinit var binding: ActivityRegisterBinding
    private var access = false

    private val CHANNEL_ID_REGISTER = "channel_notification_01"
    private val notificationId1 = 101
    private var queue: RequestQueue? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        queue = Volley.newRequestQueue(this)
        val cal = Calendar.getInstance()
        val myYear = cal.get(Calendar.YEAR)
        val myMonth = cal.get(Calendar.MONTH)
        val myDay = cal.get(Calendar.DAY_OF_MONTH)



        binding.btnLogin.setOnClickListener {
            startActivity(intent)
        }

        binding.btnSignUp.setOnClickListener {
            if (!binding.inputUsername.text.toString().isEmpty() && !binding.inputPassword.text.toString().isEmpty() && !binding.inputTanggalLahir.text.toString().isEmpty() &&
                !binding.inputEmail.text.toString().isEmpty() && !binding.inputNomorTelepon.text.toString().isEmpty()){
                val bundle = Bundle()
                bundle.putString("username",binding.inputUsername.text.toString())
                bundle.putString("password",binding.inputPassword.text.toString())
                intent.putExtra("registerBundle",bundle)


                createUser(User(0, binding.inputUsername.text.toString(),binding.inputEmail.text.toString(),
                    binding.inputPassword.text.toString(),binding.inputTanggalLahir.text.toString(),binding.inputNomorTelepon.text.toString()))

            } else {
                if (binding.inputUsername.text.toString().isEmpty()){
                    binding.layoutUsername.setError("Username Harus Diisi")
                }
                if (binding.inputPassword.text.toString().isEmpty()){
                    binding.layoutPassword.setError("Password Harus Diisi")
                }
                if (binding.inputTanggalLahir.text.toString().isEmpty()){
                    binding.layoutTanggalLahir.setError("Tanggal Lahir Harus Diiisi")
                }
                if (binding.inputEmail.text.toString().isEmpty()){
                    binding.layoutEmail.setError("Email Harus Diisi")
                }
                if (binding.inputNomorTelepon.text.toString().isEmpty()){
                    binding.layoutNomorTelepon.setError("Nomor Telp Harus Diiisi")
                }
            }
        }

        binding.inputTanggalLahir.setOnFocusChangeListener { view, b ->
            val datePicker =
                this?.let { it1 ->
                    DatePickerDialog(it1, DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
                        binding.inputTanggalLahir.setText("${dayOfMonth}/${(month.toInt() + 1).toString()}/${year}")
                    },myYear,myMonth,myDay)
                }
            if(b){
                datePicker?.show()
            }else{
                datePicker?.hide()
            }

        }

    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notification Register"
            val descriptionText = "Notification Description"

            val channel1 = NotificationChannel(CHANNEL_ID_REGISTER,name, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = descriptionText
            }


            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel1)
        }
    }

    private fun sendNotification1(username: String, bitmap : Bitmap){
        val intent : Intent = Intent (this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this,0,intent,0)
        val broadcastIntent : Intent = Intent(this, NotificationReceiver::class.java)
        broadcastIntent.putExtra("toastMessage","Halo " + username + " Kamu Berhasil Registrasi")
        val actionIntent = PendingIntent.getBroadcast(this,0,broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT)


        val builder = NotificationCompat.Builder(this, CHANNEL_ID_REGISTER)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle("Registrasi Berhasil")
            .setContentText("Halo " + username + " Kamu Berhasil Registrasi")
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setColor(Color.BLUE)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .addAction(R.mipmap.ic_launcher, "Toast", actionIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))



        with(NotificationManagerCompat.from(this)){
            notify(notificationId1, builder.build())
        }



    }

    private fun createUser(user: User){

        val stringRequest: StringRequest =
            object: StringRequest(Method.POST, TubesApi.ADD_URL_USER, Response.Listener { response ->

                val jsonObject = JSONObject(response)

                val status = jsonObject.getInt("status")

                if(status == 1){
                    Toast.makeText(this, "Register User Success", Toast.LENGTH_SHORT).show()
                    val bitmap = BitmapFactory.decodeResource(resources, R.drawable.icon)
                    createNotificationChannel()
                    sendNotification1(binding.inputUsername.text.toString(),Bitmap.createScaledBitmap(bitmap,300,100,false))

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