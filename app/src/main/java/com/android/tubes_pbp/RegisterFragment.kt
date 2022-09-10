package com.android.tubes_pbp

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.util.*


class RegisterFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnLogin : TextView = view.findViewById(R.id.btnLogin)
        val btnSignUp : TextView = view.findViewById(R.id.btnSignUp)
        val txtUsername : TextInputEditText = view.findViewById(R.id.inputUsername)
        val txtPassword : TextInputEditText = view.findViewById(R.id.inputPassword)
        val txtTanggalLahir : TextInputEditText = view.findViewById(R.id.inputTanggalLahir)
        val layoutTanggalLahir : TextInputLayout = view.findViewById(R.id.layoutTanggalLahir)
        val cal = Calendar.getInstance()
        val myYear = cal.get(Calendar.YEAR)
        val myMonth = cal.get(Calendar.MONTH)
        val myDay = cal.get(Calendar.DAY_OF_MONTH)
        val txtEmail : TextInputEditText = view.findViewById(R.id.inputEmail)
        val txtNoTelp : TextInputEditText = view.findViewById(R.id.inputNomorTelepon)
        val layoutUsername : TextInputLayout = view.findViewById(R.id.layoutUsername)
        val layoutPassword : TextInputLayout = view.findViewById(R.id.layoutPassword)
        val layoutNoTelp : TextInputLayout = view.findViewById(R.id.layoutNomorTelepon)
        val layoutEmail : TextInputLayout = view.findViewById(R.id.layoutEmail)

        val secondFragment = LoginFragment()
        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
        transaction.replace(R.id.layout_fragment, secondFragment)

        btnLogin.setOnClickListener {
            transaction.commit()
        }

        btnSignUp.setOnClickListener {
            if (!txtUsername.text.toString().isEmpty() && !txtPassword.text.toString().isEmpty() && !txtTanggalLahir.text.toString().isEmpty() &&
            !txtEmail.text.toString().isEmpty() && !txtNoTelp.text.toString().isEmpty()){
                val bundle = Bundle()
                bundle.putString("username",txtUsername.text.toString())
                bundle.putString("password",txtPassword.text.toString())
                secondFragment.arguments = bundle
                transaction.commit()
            } else {
                if (txtUsername.text.toString().isEmpty()){
                    layoutUsername.setError("Username Harus Diisi")
                }
                if (txtPassword.text.toString().isEmpty()){
                    layoutPassword.setError("Password Harus Diisi")
                }
                if (txtTanggalLahir.text.toString().isEmpty()){
                    layoutTanggalLahir.setError("Tanggal Lahir Harus Diiisi")
                }
                if (txtEmail.text.toString().isEmpty()){
                    layoutEmail.setError("Email Harus Diisi")
                }
                if (txtNoTelp.text.toString().isEmpty()){
                    layoutNoTelp.setError("Nomor Telp Harus Diiisi")
                }
            }
        }

        txtTanggalLahir.setOnFocusChangeListener { view, b ->
            val datePicker =
                activity?.let { it1 ->
                    DatePickerDialog(it1, DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
                        txtTanggalLahir.setText("${dayOfMonth}/${(month.toInt() + 1).toString()}/${year}")
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


