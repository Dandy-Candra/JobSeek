package com.android.tubes_pbp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

<<<<<<< HEAD
class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        val message = intent.getStringExtra("toastMessage")
=======
class NotificationReceiver : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent) {
        val message = intent.getStringExtra("ToastMessage")
>>>>>>> origin/master
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
}