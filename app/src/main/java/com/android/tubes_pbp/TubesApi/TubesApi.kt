package com.android.tubes_pbp.TubesApi

import android.content.Context
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class TubesApi {
    companion object{
        val BASE_URL = "http://192.168.88.77/API_Tubes/public/api/"

        val GET_ALL_URL_EXPERIENCE = BASE_URL + "experiences/"
        val GET_BY_ID_URL_EXPERIENCE = BASE_URL + "experiences/"
        val ADD_URL_EXPERIENCE = BASE_URL + "experiences"
        val UPDATE_URL_EXPERIENCE = BASE_URL + "experiences/"
        val DELETE_URL_EXPERIENCE = BASE_URL + "experiences/"


        val GET_ALL_URL_USER = BASE_URL + "users"
        val GET_BY_ID_URL_USER = BASE_URL + "users/"
        val ADD_URL_USER = BASE_URL + "users"
        val UPDATE_URL_USER = BASE_URL + "users/"
        val DELETE_URL_USER = BASE_URL + "users/"
        val LOGIN_URL_USER = BASE_URL + "users/login"


    }
}