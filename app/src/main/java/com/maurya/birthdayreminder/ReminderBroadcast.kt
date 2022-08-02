package com.maurya.birthdayreminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi


class ReminderBroadcast : BroadcastReceiver() {

    @RequiresApi(api = Build.VERSION_CODES.Q)
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.e("We are in the receiver.", "Yay!")

        Toast.makeText(context, "Happy Birthday", Toast.LENGTH_SHORT).show()

        intent!!.putExtra("extra", "phoneNo")
        starA
    }

}