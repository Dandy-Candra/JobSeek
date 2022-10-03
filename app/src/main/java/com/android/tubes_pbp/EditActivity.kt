package com.android.tubes_pbp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.tubes_pbp.databinding.ActivityEditBinding
import com.android.tubes_pbp.user.TubesDB
import com.android.tubes_pbp.user.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditActivity : AppCompatActivity() {

    val db by lazy { TubesDB(this) }
    private lateinit var binding: ActivityEditBinding
    private val id = "idKey"
    private val myPreference = "myPref"
    private var usernameTemp: String = ""
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        val id = sharedPreferences.getString(id, "0")?.toIntOrNull() ?: 0

        loadData(id)
        val intent = Intent(this, HomeActivity::class.java)

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.Save -> {
                    val usernameFromInput = binding.inputUsername.text.toString()
                    val emailFromInput = binding.inputEmail.text.toString()
                    val passwordFromInput = binding.inputPassword.text.toString()
                    val dateBirthFromInput = binding.inputTanggalLahir.text.toString()
                    val phoneNumberFromInput = binding.inputNomorTelepon.text.toString()

                    CoroutineScope(Dispatchers.IO).launch {

                        if (usernameTemp != usernameFromInput) {
                            if (usernameExist(usernameFromInput)) {
                                launch(Dispatchers.Main) {
                                    binding.layoutUsername.error = "Username Already Exist!"
                                }
                            }
                            return@launch
                        }
                        db.userDao().updateUser(
                            User(
                                id = id,
                                username = usernameFromInput,
                                email = emailFromInput,
                                password = passwordFromInput,
                                date = dateBirthFromInput,
                                noTelp = phoneNumberFromInput
                            )
                        )
                        finish()
                        val bundle = Bundle()
                        bundle.putString("key", "iniTerisi")
                        intent.putExtra("keyBundle", bundle)
                        startActivity(intent)

                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun loadData(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = db.userDao().getUser(id)
            if (user != null) {
                usernameTemp = user.username
                launch(Dispatchers.Main) {
                    binding.inputUsername.setText(user.username)
                    binding.inputEmail.setText(user.email)
                    binding.inputNomorTelepon.setText(user.noTelp)
                    binding.inputTanggalLahir.setText(user.date)
                    binding.inputPassword.setText(user.password)
                }
            }

        }
    }

    private suspend fun usernameExist(username: String): Boolean {
        return withContext(Dispatchers.IO) {
            db.userDao().getUserByUsername(username) != null
        }
    }
}