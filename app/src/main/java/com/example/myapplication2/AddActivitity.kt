package com.example.myapplication2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication2.database.Meeting
import com.example.myapplication2.database.MeetingTransaction
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class AddActivitity : AppCompatActivity() {

    private var myDB : MeetingTransaction? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_activitity)
        myDB = MeetingTransaction(this)
        val btn : Button = findViewById(R.id.btn_add)
        val edit_title : TextView = findViewById(R.id.edit_title)
        val edit_tgl : TextView = findViewById(R.id.edit_tgl)
        val edit_desc : TextView = findViewById(R.id.edit_desc)
        btn.setOnClickListener {
            doAsync {
                myDB!!.beginMeetingTransaction()
                myDB!!.addMeetingTransaction(
                    Meeting(
                        "${edit_title.text}",
                        "${edit_tgl.text}",
                        "${edit_desc.text}"
                    )
                )
                myDB!!.successMeetingTransaction()
                myDB!!.endMeetingTransaction()
                uiThread {
                    finish()
                }
            }
        }
    }

    override fun finish() {
        val returnIntent = Intent()
        // setResult(RESULT_OK);
        setResult(
            RESULT_OK,
            returnIntent
        ) //By not passing the intent in the result, the calling activity will get null data.
        super.finish()
    }
}