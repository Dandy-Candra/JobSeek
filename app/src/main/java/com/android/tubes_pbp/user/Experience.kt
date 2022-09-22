package com.android.tubes_pbp.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Experience (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String
    )
