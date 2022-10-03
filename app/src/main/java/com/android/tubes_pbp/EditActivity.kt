package com.android.tubes_pbp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentTransaction
import com.android.tubes_pbp.databinding.ActivityEditBinding
import com.android.tubes_pbp.databinding.ActivityLoginBinding
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
    private var temp : String? = null
    private var access = true
    var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSupportActionBar()?.hide()
        binding = ActivityEditBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sharedPreferences = getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        val id = sharedPreferences!!.getString(id,"")!!.toInt()
        loadData(id)
        val intent = Intent(this, HomeActivity::class.java)

        binding.topAppBar.setOnMenuItemClickListener { menuItem->
            when(menuItem.itemId){
                R.id.Save -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        val userExist = db.userDao().getUserByUsername(binding.inputUsername.text.toString())
                        if(userExist != null && temp != binding.inputUsername.text.toString()) {
                            access = false
                        }

                        withContext(Dispatchers.Main) {
                            if (access) {
                            CoroutineScope(Dispatchers.IO).launch {
                                db?.userDao()?.updateUser(
                                    User(
                                        id,
                                        binding.inputUsername.text.toString(),
                                        binding.inputEmail.text.toString(),
                                        binding.inputPassword.text.toString(),
                                        binding.inputTanggalLahir.text.toString(),
                                        binding.inputNomorTelepon.text.toString()
                                    )
                                )
                            }
                            finish()

                            }else{
                                    binding.layoutUsername.setError("Username Already Exist!")
                                    access = true
                            }

                        }
                    }

                    true
                }
                else -> false
            }

        }




    }

    fun loadData(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            val user = db?.userDao()?.getUser(id)?.get(0)


            withContext(Dispatchers.Main){
                temp = user?.username
                binding.inputUsername.setText(temp)
                binding.inputEmail.setText(user?.email)
                binding.inputNomorTelepon.setText(user?.noTelp)
                binding.inputTanggalLahir.setText(user?.date)
                binding.inputPassword.setText(user?.password)
            }

        }
    }
}