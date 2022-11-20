package com.android.tubes_pbp.user

import androidx.room.Entity
import androidx.room.PrimaryKey


class Experience (
    val id: Int,
    val title: String,
    val description: String,
    val idUser: Int
    )
