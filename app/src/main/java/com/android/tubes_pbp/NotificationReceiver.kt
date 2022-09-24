package com.android.tubes_pbp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class NotificationReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent) {
        val message = intent.getStringExtra("ToastMessage")
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}