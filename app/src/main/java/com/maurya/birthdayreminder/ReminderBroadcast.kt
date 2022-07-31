package com.maurya.birthdayreminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast


class ReminderBroadcast : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        Toast.makeText(context, "Happy BirthDay", Toast.LENGTH_SHORT).show()
//        MainActivity.showDialog(context!!)

    }

}