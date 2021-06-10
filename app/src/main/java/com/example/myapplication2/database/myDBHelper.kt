package com.example.myapplication2.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import myDB.MeetingDB

class myDBHelper (context: Context) : SQLiteOpenHelper (
    context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_MEETING_TABLE = ("CREATE TABLE " +
                MeetingDB.MeetingTable.TABLE_MEETING + "(" +
                MeetingDB.MeetingTable.COLUMN_ID + " INTEGER PRIMARY KEY," +
                MeetingDB.MeetingTable.COLUMN_TITLE + " TEXT," +
                MeetingDB.MeetingTable.COLUMN_TGL + " TEXT, " +
                MeetingDB.MeetingTable.COLUMN_DESC + " TEXT" + ")")
        db?.execSQL(CREATE_MEETING_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " +
            MeetingDB.MeetingTable.TABLE_MEETING)
        onCreate(db)
    }
    companion object{
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "mypreload.db"
    }
}