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
        val button : TextView = view.findViewById(R.id.btnLogin)
        val txtUsername : TextInputEditText = view.findViewById(R.id.inputUsername)
        val txtPassword : TextInputEditText = view.findViewById(R.id.inputPassword)
        val txtTanggalLahir : TextInputEditText = view.findViewById(R.id.inputTanggalLahir)
        val layoutTanggalLahir : TextInputLayout = view.findViewById(R.id.layoutTanggalLahir)
        val cal = Calendar.getInstance()
        val myYear = cal.get(Calendar.YEAR)
        val myMonth = cal.get(Calendar.MONTH)
        val myDay = cal.get(Calendar.DAY_OF_MONTH)

        button.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("username",txtUsername.text.toString())
            bundle.putString("password",txtPassword.text.toString())
            val secondFragment = LoginFragment()
            secondFragment.arguments = bundle
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.layout_fragment, secondFragment)
            transaction.commit()
        }

        txtTanggalLahir.setOnFocusChangeListener { view, b ->
            val datePicker =
                activity?.let { it1 ->
                    DatePickerDialog(it1, DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
                        txtTanggalLahir.setText("${dayOfMonth} ${month} ${year}")
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


