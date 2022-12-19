package com.android.tubes_pbp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.tubes_pbp.TubesApi.TubesApi
import com.android.tubes_pbp.camera.CameraActivity
import com.android.tubes_pbp.databinding.FragmentProfileBinding
import com.android.tubes_pbp.inputLamaran.inputLamaran
import com.android.tubes_pbp.ulasan.ShowUlasan
import com.android.tubes_pbp.user.User
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.layout_loading.view.*
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class ProfileFragment : Fragment() {

    private val id = "idKey"
    private val myPreference = "myPref"
    var sharedPreferences: SharedPreferences? = null
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var queue: RequestQueue? = null
    private var pass: String = ""
    private var layoutLoading: ConstraintLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutLoading = view.findViewById(R.id.layout_loading)
        val btnLogOut  = binding.btnLogout
        sharedPreferences = activity?.getSharedPreferences(myPreference, Context.MODE_PRIVATE)
        queue = Volley.newRequestQueue(requireActivity())


        btnLogOut.setOnClickListener {
            activity?.let { it1 ->
                MaterialAlertDialogBuilder(it1)
                    .setTitle("Apakah Anda Ingin Keluar ?, Anda dapat memberikan masukan untuk kemajuan aplikasi kami!")
                    .setNegativeButton("Berikan Ulasan") { dialog, which ->
                        val intent = Intent(activity, ShowUlasan::class.java)
                        startActivity(intent)
                    }
                    .setPositiveButton("yes") { dialog, which ->
                        val moveLogin = Intent(activity, LoginActivity::class.java)
                        startActivity(moveLogin)
                        activity?.finish()
                    }
                    .show()
            }
        }


        binding.editProfile.setOnClickListener {

            activity?.let { it1 ->
                MaterialAlertDialogBuilder(it1)
                    .setTitle("Edit Profile")
                    .setNegativeButton("Edit Data") { dialog, which ->
                        val moveEdit = Intent(activity, EditActivity::class.java)

                        val bundle = Bundle()

                        bundle.putString("name", binding.username.text.toString())
                        bundle.putString("email", binding.email.text.toString())
                        bundle.putString("phone", binding.noTelp.text.toString())
                        bundle.putString("tgllahir", binding.tglLahir.text.toString())
                        bundle.putString("password", pass)
                        moveEdit.putExtra("editBundle",bundle)

                        startActivity(moveEdit)
                    }
                    .setPositiveButton("Edit Image") { dialog, which ->
                        val moveCamera = Intent(activity, CameraActivity::class.java)
                        startActivity(moveCamera)
                    }
                    .show()
            }


        }

        binding.btnCekLamaran.setOnClickListener {
            val moveCekLamaran = Intent(activity, inputLamaran::class.java)
            startActivity(moveCekLamaran)
        }
    }

    override fun onStart() {
        super.onStart()
        getUserById(sharedPreferences!!.getString(id,"")!!.toInt())
    }

    private fun getUserById(idUser: Int){
        setLoading(true)
        val stringRequest : StringRequest = object:
            StringRequest(Method.GET, TubesApi.GET_BY_ID_URL_USER + idUser, Response.Listener { response ->

                val gson = Gson()

                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONObject("data")
                var user : User = gson.fromJson(jsonArray.toString(), User::class.java)

                binding.username.setText(user.username)
                binding.email.setText(user.email)
                binding.noTelp.setText(user.noTelp)
                binding.tglLahir.setText(user.date)
                pass = user.password
                setLoading(false)

            }, Response.ErrorListener { error ->
                setLoading(false)
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

    private fun setLoading(isLoading: Boolean){
        if(isLoading){
            activity?.window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )

//            layoutLoading!!.animate().alpha(0f).setDuration(500).withEndAction {
//                layoutLoading!!.visibility = View.VISIBLE
//            }

        }else{
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            layoutLoading!!.animate().alpha(0f).setDuration(250).withEndAction {
                layoutLoading!!.visibility = View.GONE
            }

        }
    }


}