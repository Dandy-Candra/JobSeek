package com.android.tubes_pbp.user

import androidx.room.Entity
import androidx.room.PrimaryKey


 class User (
    val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val date: String,
    val noTelp: String
    )
