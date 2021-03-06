package com.example.myapplication2.widgett

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import android.widget.RemoteViewsService.RemoteViewsFactory
import com.example.myapplication2.R
import com.example.myapplication2.database.Meeting
import com.example.myapplication2.database.MeetingTransaction


class ListWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return ListRemoteViewsFactory(this.applicationContext, intent)
    }
}

internal class ListRemoteViewsFactory(context: Context, intent: Intent) :
    RemoteViewsFactory {
    private var myDB : MeetingTransaction? = null
    private val mWidgetItems: MutableList<WidgetItem> = ArrayList<WidgetItem>()
    private val mContext: Context
    private val mAppWidgetId: Int
    override fun onCreate() {
        val allMeeting: List<Meeting> = myDB!!.viewAllMeeting()
        for (i in allMeeting.indices) {
            mWidgetItems.add(WidgetItem("${allMeeting[i].title}", "${allMeeting[i].tgl}", "${allMeeting[i].desc}"))
        }
        try {
            Thread.sleep(3000)
        }catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        mWidgetItems.clear()
    }

    override fun getCount(): Int {
        return mWidgetItems.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        rv.setTextViewText(R.id.widget_title, mWidgetItems[position].w_title)
        rv.setTextViewText(R.id.widget_tgl, mWidgetItems[position].w_tgl)
        rv.setTextViewText(R.id.widget_desc, mWidgetItems[position].w_desc)
        val extras = Bundle()
        extras.putInt(MyWidget.EXTRA_ITEM, position)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.widget_title, fillInIntent)
        try {
            println("Loading view $position")
            Thread.sleep(500)
        }catch (e: InterruptedException) {
            e.printStackTrace()
        }
        return rv
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun onDataSetChanged() {
        mWidgetItems.clear()
        val allMeeting: List<Meeting> = myDB!!.viewAllMeeting()
        for (i in allMeeting.indices) {
            mWidgetItems.add(WidgetItem("${allMeeting[i].title}", "${allMeeting[i].tgl}", "${allMeeting[i].desc}"))
            Log.w("widgetitem", "${mWidgetItems}")
        }
    }
    init {
        mContext = context
        mAppWidgetId = intent.getIntExtra(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        )
        myDB = MeetingTransaction(this.mContext)
    }
}












//class StackWidgetService : RemoteViewsService() {
//    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
//        return StackRemoteViewsFactory(this.applicationContext, intent)
//    }
//}

//internal class StackRemoteViewsFactory(context: Context, intent: Intent) :
//    RemoteViewsFactory {
//    private val mWidgetItems: MutableList<WidgetItem> = ArrayList<WidgetItem>()
//    private val mContext: Context
//    private val mAppWidgetId: Int
//    override fun onCreate() {
//        // In onCreate() you setup any connections / cursors to your data source. Heavy lifting,
//        // for example downloading or creating content etc, should be deferred to onDataSetChanged()
//        // or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.
//        for (i in 0 until mCount) {
//            mWidgetItems.add(WidgetItem("$i!"))
//        }
//        // We sleep for 3 seconds here to show how the empty view appears in the interim.
//        // The empty view is set in the StackWidgetProvider and should be a sibling of the
//        // collection view.
//        try {
//            Thread.sleep(3000)
//        } catch (e: InterruptedException) {
//            e.printStackTrace()
//        }
//    }
//
//    override fun onDestroy() {
//        // In onDestroy() you should tear down anything that was setup for your data source,
//        // eg. cursors, connections, etc.
//        mWidgetItems.clear()
//    }
//
//    override fun getCount(): Int {
//        return mCount
//    }
//
//    override fun getViewAt(position: Int): RemoteViews {
//        // position will always range from 0 to getCount() - 1.
//        // We construct a remote views item based on our widget item xml file, and set the
//        // text based on the position.
//        val rv = RemoteViews(mContext.getPackageName(), R.layout.widget_item)
//        rv.setTextViewText(R.id.widget_item, mWidgetItems[position].text)
//        // Next, we set a fill-intent which will be used to fill-in the pending intent template
//        // which is set on the collection view in StackWidgetProvider.
//        val extras = Bundle()
//        extras.putInt(StackWidgetProvider.EXTRA_ITEM, position)
//        val fillInIntent = Intent()
//        fillInIntent.putExtras(extras)
//        rv.setOnClickFillInIntent(R.id.widget_item, fillInIntent)
//        // You can do heaving lifting in here, synchronously. For example, if you need to
//        // process an image, fetch something from the network, etc., it is ok to do it here,
//        // synchronously. A loading view will show up in lieu of the actual contents in the
//        // interim.
//        try {
//            println("Loading view $position")
//            Thread.sleep(500)
//        } catch (e: InterruptedException) {
//            e.printStackTrace()
//        }
//        // Return the remote views object.
//        return rv
//    }
//
//    override fun getLoadingView(): RemoteViews {
//        // You can create a custom loading view (for instance when getViewAt() is slow.) If you
//        // return null here, you will get the default loading view.
//        return null
//    }
//
//    override fun getViewTypeCount(): Int {
//        return 1
//    }
//
//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }
//
//    override fun hasStableIds(): Boolean {
//        return true
//    }
//
//    override fun onDataSetChanged() {
//        // This is triggered when you call AppWidgetManager notifyAppWidgetViewDataChanged
//        // on the collection view corresponding to this factory. You can do heaving lifting in
//        // here, synchronously. For example, if you need to process an image, fetch something
//        // from the network, etc., it is ok to do it here, synchronously. The widget will remain
//        // in its current state while work is being done here, so you don't need to worry about
//        // locking up the widget.
//    }
//
//    companion object {
//        private const val mCount = 10
//    }
//
//    init {
//        mContext = context
//        mAppWidgetId = intent.getIntExtra(
//            AppWidgetManager.EXTRA_APPWIDGET_ID,
//            AppWidgetManager.INVALID_APPWIDGET_ID
//        )
//    }
//}


//class WidgetRemoteViewsFactory(context: Context?, intent: Intent) :
//    RemoteViewsFactory {
//    private val context: Context? = null
//    private val appWidgetId: Int
//    private var widgetList: MutableList<String> = ArrayList()
//    private val dbhelper: DBHelper
//    private fun updateWidgetListView() {
//        val widgetFruitsArray: Array<String> = dbhelper.retrieveFruitsList()
//        val convertedToList: MutableList<String> =
//            ArrayList<String>(Arrays.asList(widgetFruitsArray))
//        widgetList = convertedToList
//    }
//
//    override fun getCount(): Int {
//        return widgetList.size
//    }
//
//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }
//
//    override fun getLoadingView(): RemoteViews {
//        // TODO Auto-generated method stub
//        return null
//    }
//
//    override fun getViewAt(position: Int): RemoteViews {
//        Log.d("WidgetCreatingView", "WidgetCreatingView")
//        val remoteView = RemoteViews(
//            context!!.packageName,
//            R.layout.listview_row_item
//        )
//        Log.d("Loading", widgetList[position])
//        remoteView.setTextViewText(R.id.listTV, widgetList[position])
//        return remoteView
//    }
//
//    override fun getViewTypeCount(): Int {
//        // TODO Auto-generated method stub
//        return 0
//    }
//
//    override fun hasStableIds(): Boolean {
//        // TODO Auto-generated method stub
//        return false
//    }
//
//    override fun onCreate() {
//        // TODO Auto-generated method stub
//        updateWidgetListView()
//    }
//
//    override fun onDataSetChanged() {
//        // TODO Auto-generated method stub
//        updateWidgetListView()
//    }
//
//    override fun onDestroy() {
//        // TODO Auto-generated method stub
//        widgetList.clear()
//        dbhelper.close()
//    }
//
//    init {
//        this.context = context
//        appWidgetId = intent.getIntExtra(
//            AppWidgetManager.EXTRA_APPWIDGET_ID,
//            AppWidgetManager.INVALID_APPWIDGET_ID
//        )
//        Log.d("AppWidgetId", appWidgetId.toString())
//        dbhelper = DBHelper(this.context)
//    }
//}