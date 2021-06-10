package com.example.myapplication2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication2.database.FirstRunSharePref
import com.example.myapplication2.database.Meeting
import com.example.myapplication2.database.MeetingTransaction
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    var myFirstRun : FirstRunSharePref? = null

    private var myDB: MeetingTransaction? = null

//    private var layoutManager: RecyclerView.LayoutManager? = null
//    private var adapter: RecyclerView.Adapter<myRecyclerAdapter.itemViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myFirstRun = FirstRunSharePref(this)
//        MeetingTransaction(this).deleteAll()
        if (myFirstRun!!.firstRun) {
            val secondIntent = Intent(this, PreLoad::class.java)
            startActivity(secondIntent)
        }
        updateAdapter()
    }

    override fun onResume() {
        super.onResume()
        updateAdapter()
    }

    private fun updateAdapter() {
        doAsync {
            var myRecycler: RecyclerView = findViewById(R.id.recyclerView)
            myRecycler.layoutManager = LinearLayoutManager(this@MainActivity)
            myDB = MeetingTransaction(this@MainActivity)
//            layoutManager = LinearLayoutManager(this@MainActivity)
            var allMeeting: List<Meeting> = myDB!!.viewAllMeeting()
            uiThread {
                if (allMeeting.size > 0) {
//                    adapter = myRecyclerAdapter(allMeeting)
                    myRecycler.adapter = myRecyclerAdapter(allMeeting)
                } else {
                    Toast.makeText(this@MainActivity,"There is no contact in the database. Start adding now", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}