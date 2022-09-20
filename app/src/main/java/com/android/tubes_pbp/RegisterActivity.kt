package com.android.tubes_pbp

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.android.tubes_pbp.databinding.ActivityRegisterBinding
import com.android.tubes_pbp.user.TubesDB
import com.android.tubes_pbp.user.User
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class RegisterActivity : AppCompatActivity() {
    val db by lazy { TubesDB(this) }
    private var userId: Int = 0
    private lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        getSupportActionBar()?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val cal = Calendar.getInstance()
        val myYear = cal.get(Calendar.YEAR)
        val myMonth = cal.get(Calendar.MONTH)
        val myDay = cal.get(Calendar.DAY_OF_MONTH)

        val intent = Intent(this, LoginActivity::class.java)

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

                CoroutineScope(Dispatchers.IO).launch {
                    db.userDao().addUser(
                        User(0, binding.inputUsername.text.toString(),binding.inputEmail.text.toString(),
                            binding.inputPassword.text.toString(),binding.inputTanggalLahir.text.toString(),binding.inputNomorTelepon.text.toString()  )
                    )
                    finish()
                }


                startActivity(intent)
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

}