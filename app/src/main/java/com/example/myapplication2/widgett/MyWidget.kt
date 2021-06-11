package com.example.myapplication2.widgett

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import com.example.myapplication2.R


/**
 * Implementation of App Widget functionality.
 */
class MyWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            val intent = Intent(context, ListWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            val rv = RemoteViews(context.packageName, R.layout.my_widget)
            rv.setRemoteAdapter(R.id.list_view, intent)
            rv.setEmptyView(R.id.list_view, R.id.emptyView)
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.list_view)
            appWidgetManager.updateAppWidget(appWidgetId, rv)
            Log.w("Widget", "${intent}")



            val toastIntent = Intent(context, MyWidget::class.java)
            toastIntent.action = TOAST_ACTION
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            val toastPendingIntent = PendingIntent.getBroadcast(
                context, 0, toastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            rv.setPendingIntentTemplate(R.id.list_view, toastPendingIntent)
//            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds)
    }

    override fun onReceive(context: Context, intent: Intent) {
        val mgr = AppWidgetManager.getInstance(context)
        if (intent.action == TOAST_ACTION) {
            val appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID
            )
            val viewIndex = intent.getIntExtra(EXTRA_ITEM, 0)
            Toast.makeText(context, "Touched view $viewIndex", Toast.LENGTH_SHORT).show()
        }
        super.onReceive(context, intent)
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
    }

    companion object {
        const val TOAST_ACTION = "com.example.myapplication2.TOAST_ACTION"
        const val EXTRA_ITEM =   "com.example.myapplication2.EXTRA_ITEM"
    }
}

//internal fun updateAppWidget(
//    context: Context,
//    appWidgetManager: AppWidgetManager,
//    appWidgetId: Int
//) {
//    val widgetText = context.getString(R.string.appwidget_text)
//    // Construct the RemoteViews object
//    val views = RemoteViews(context.packageName, R.layout.my_widget)
////    views.setTextViewText(R.id.appwidget_text_title, widgetText)
//
//    // Instruct the widget manager to update the widget
//    appWidgetManager.updateAppWidget(appWidgetId, views)
//}

//class StackWidgetProvider : AppWidgetProvider() {
//    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
//        super.onDeleted(context, appWidgetIds)
//    }
//
//    override fun onDisabled(context: Context) {
//        super.onDisabled(context)
//    }
//
//    override fun onEnabled(context: Context) {
//        super.onEnabled(context)
//    }
//
//    override fun onReceive(context: Context, intent: Intent) {
//        val mgr = AppWidgetManager.getInstance(context)
//        if (intent.action == TOAST_ACTION) {
//            val appWidgetId = intent.getIntExtra(
//                AppWidgetManager.EXTRA_APPWIDGET_ID,
//                AppWidgetManager.INVALID_APPWIDGET_ID
//            )
//            val viewIndex = intent.getIntExtra(EXTRA_ITEM, 0)
//            Toast.makeText(context, "Touched view $viewIndex", Toast.LENGTH_SHORT).show()
//        }
//        super.onReceive(context, intent)
//    }
//
//    override fun onUpdate(
//        context: Context,
//        appWidgetManager: AppWidgetManager,
//        appWidgetIds: IntArray
//    ) {
//        // update each of the widgets with the remote adapter
//        for (i in appWidgetIds.indices) {
//            // Here we setup the intent which points to the StackViewService which will
//            // provide the views for this collection.
//            val intent = Intent(context, StackWidgetService::class.java)
//            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i])
//            // When intents are compared, the extras are ignored, so we need to embed the extras
//            // into the data so that the extras will not be ignored.
//            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
//            val rv = RemoteViews(context.packageName, R.layout.widget_layout)
//            rv.setRemoteAdapter(appWidgetIds[i], R.id.stack_view, intent)
//            // The empty view is displayed when the collection has no items. It should be a sibling
//            // of the collection view.
//            rv.setEmptyView(R.id.stack_view, R.id.empty_view)
//            // Here we setup the a pending intent template. Individuals items of a collection
//            // cannot setup their own pending intents, instead, the collection as a whole can
//            // setup a pending intent template, and the individual items can set a fillInIntent
//            // to create unique before on an item to item basis.
//            val toastIntent = Intent(context, StackWidgetProvider::class.java)
//            toastIntent.action = TOAST_ACTION
//            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i])
//            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
//            val toastPendingIntent = PendingIntent.getBroadcast(
//                context, 0, toastIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT
//            )
//            rv.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent)
//            appWidgetManager.updateAppWidget(appWidgetIds[i], rv)
//        }
//        super.onUpdate(context, appWidgetManager, appWidgetIds)
//    }
//
//    companion object {
//        const val TOAST_ACTION = "com.example.android.stackwidget.TOAST_ACTION"
//        const val EXTRA_ITEM = "com.example.android.stackwidget.EXTRA_ITEM"
//    }
//}