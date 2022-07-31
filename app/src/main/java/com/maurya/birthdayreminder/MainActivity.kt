package com.maurya.birthdayreminder

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.maurya.birthdayreminder.databinding.ActivityMainBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var Uhours = 0
    private var Uminute = 0
    private var alarmRequestCode = 101
    private var totalSecond : Long = 0
    var mp: MediaPlayer? = null


    @SuppressLint("UnspecifiedImmutableFlag")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.simpleTimePicker.setIs24HourView(false)

        binding.simpleTimePicker.setOnTimeChangedListener { timePicker, i, i2 ->

            Uhours = i
            Uminute = i2
            binding.textView.text = "$i:$i2"
        }

        binding.button.setOnClickListener {

            alertTime(2022, 7, 31, Uhours, Uminute,0)
            Log.d("TAG", "onCreate: $totalSecond")
            val ALARM_DELAY_IN_SECOND = totalSecond
            val alarmTimeAtUTC = System.currentTimeMillis() + ALARM_DELAY_IN_SECOND * 1000L

            val alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val reminderIntent = Intent(this, ReminderBroadcast::class.java)
            val pendingIntent = PendingIntent.getBroadcast(this@MainActivity, alarmRequestCode, reminderIntent, 0)

            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTimeAtUTC, pendingIntent)

//            mp= MediaPlayer.create(this, R.raw.wakeup_alarm)
//            mp!!.start()

        }
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun alertTime(
        year: Int,
        month: Int,
        date: Int,
        hours: Int,
        minutes: Int,
        seconds: Int
    ) {

        val currentTime: Date = Calendar.getInstance().time

        val bornTime = "$year-$month-$date $hours:$minutes:$seconds"
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        try {
            val newDate = dateFormat.parse(bornTime)
            Log.d("TAG", "showTime: ${newDate!!.time}")
            Log.d("TAG", "showTime: $currentTime")
            val diff = newDate.time - currentTime.time
            val seconds = diff / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24
            if (currentTime.before(newDate)) {
                totalSecond = seconds
                Log.e("Current date", "is Next date")
                Log.e(
                    "Difference: ", " seconds: " + seconds + " minutes: " + minutes
                            + " hours: " + hours + " days: " + days
                )
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    companion object{
        fun showDialog(context: Context){
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.birthday_dialog)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val dialogBtn_remove = dialog.findViewById<Button>(R.id.button2)
            dialogBtn_remove.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
    }

}