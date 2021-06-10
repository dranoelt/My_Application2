package com.example.myapplication2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication2.database.FirstRunSharePref
import com.example.myapplication2.database.Meeting
import com.example.myapplication2.database.MeetingTransaction
import com.example.myapplication2.databinding.ActivityPreLoadBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PreLoad : AppCompatActivity() {

    private lateinit var binding: ActivityPreLoadBinding
    private var order = listOf(
        Meeting("House", "25-02-2019", "Minimalist House"),
        Meeting("Paris House", "02-03-2020", "Luxury with Black Interior"),
        Meeting("Paris Clear House", "05-03-2021", "Minimalist and White"),
        Meeting("Hawaian House", "18-05-2021", "Colorful"),
        Meeting("Private House", "21-06-2021", "Just White and Black"),
        Meeting("House of Forest", "03-06-2021", "Forest Theme"),
        Meeting("Dream Place", "15-11-2021", "Minimalist"),
        Meeting("Blast", "16-12-2021", "Up To You")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreLoadBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        executeLoadData()
    }

    private fun executeLoadData() {
        var myProgress = binding.progressBar
        myProgress.progress = 0
        var database = MeetingTransaction(this@PreLoad)
        doAsync {
            database.beginMeetingTransaction()
            for (meet in order) {
                database.addMeetingTransaction(meet)
                uiThread {
                    myProgress.progress += myProgress.max/order.size
                    Log.w("Progress", "${myProgress.progress}")
                }
            }
            database.successMeetingTransaction()
            database.endMeetingTransaction()
            uiThread {
                finishThisActivity()
            }
        }
    }

    fun finishThisActivity() {
        var myFirstRunSharePref = FirstRunSharePref(this)
        myFirstRunSharePref.firstRun = false
        this.finish()
    }
}