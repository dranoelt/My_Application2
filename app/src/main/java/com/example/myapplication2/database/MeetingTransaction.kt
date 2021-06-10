package com.example.myapplication2.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import myDB.MeetingDB

class MeetingTransaction(context: Context) {
    private val myDBHelper = myDBHelper(context)

    private val dbwrite = myDBHelper.writableDatabase

    fun viewAllMeeting() : List<Meeting> {
        val meetList:ArrayList<Meeting> = ArrayList<Meeting> ()
        val selectQuery = "SELECT ${MeetingDB.MeetingTable.COLUMN_TITLE}" +
                ",${MeetingDB.MeetingTable.COLUMN_TGL}" +
                ",${MeetingDB.MeetingTable.COLUMN_DESC}" +
                " FROM ${MeetingDB.MeetingTable.TABLE_MEETING}"
        val db = myDBHelper.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var meetTitle: String
        var meetTgl: String
        var meetDesc: String
        if (cursor.moveToFirst()) {
            do {
                meetTitle = cursor.getString(cursor.getColumnIndex(MeetingDB.MeetingTable.COLUMN_TITLE))
                meetTgl = cursor.getString(cursor.getColumnIndex(MeetingDB.MeetingTable.COLUMN_TGL))
                meetDesc = cursor.getString(cursor.getColumnIndex(MeetingDB.MeetingTable.COLUMN_DESC))
                meetList.add(Meeting(meetTitle, meetTgl, meetDesc))
            }while (cursor.moveToNext())
        }
        return meetList
    }

    fun addMeeting(meeting: Meeting): Long {
        val db = myDBHelper.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(MeetingDB.MeetingTable.COLUMN_TITLE, meeting.title)
        contentValues.put(MeetingDB.MeetingTable.COLUMN_TGL, meeting.tgl)
        contentValues.put(MeetingDB.MeetingTable.COLUMN_DESC, meeting.desc)
        val success = db.insert(MeetingDB.MeetingTable.TABLE_MEETING, null, contentValues)
        db.close()
        return success
    }
    fun beginMeetingTransaction() {
        dbwrite.beginTransaction()
    }
    fun successMeetingTransaction() {
        dbwrite.setTransactionSuccessful()
    }
    fun endMeetingTransaction() {
        dbwrite.endTransaction()
    }
    fun addMeetingTransaction(meeting: Meeting): Unit {
        val sqlString = "INSERT INTO ${MeetingDB.MeetingTable.TABLE_MEETING} " +
                "(${MeetingDB.MeetingTable.COLUMN_TITLE}" +
                ",${MeetingDB.MeetingTable.COLUMN_TGL}" +
                ",${MeetingDB.MeetingTable.COLUMN_DESC}) VALUES (?,?,?)"
        val myStatement = dbwrite.compileStatement(sqlString)
        myStatement.bindString(1, meeting.title)
        myStatement.bindString(2, meeting.tgl)
        myStatement.bindString(3, meeting.desc)
        myStatement.execute()
        myStatement.clearBindings()
    }

    fun deleteAll() :Int {
        val db = myDBHelper.writableDatabase
        val success = db.delete(MeetingDB.MeetingTable.TABLE_MEETING, "",null)
        db.close()
        return success
    }
}