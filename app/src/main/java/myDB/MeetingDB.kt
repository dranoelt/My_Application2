package myDB

import android.provider.BaseColumns

object MeetingDB {
    class MeetingTable:BaseColumns {
        companion object {
            val TABLE_MEETING: String = "title"
            val COLUMN_ID: String = "_id"
            val COLUMN_TITLE:String = "title"
            val COLUMN_TGL:String = "tanggal"
            val COLUMN_DESC: String = "desc"
        }
    }
}