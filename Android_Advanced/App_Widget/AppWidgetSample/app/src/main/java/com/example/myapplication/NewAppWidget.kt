package com.example.myapplication

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.widget.RemoteViews
import androidx.core.content.edit
import java.text.DateFormat
import java.util.*

/**
 * Implementation of App Widget functionality.
 */
class NewAppWidget : AppWidgetProvider() {

    val mSharedPrefFile = "com.example.android.appwidgetsample"
    val COUNT_KEY = "count";

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {

        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            val prefs: SharedPreferences = context.getSharedPreferences(mSharedPrefFile, 0);

            val widgetText = context.getString(R.string.appwidget_text)
            val views = RemoteViews(context.packageName, R.layout.new_app_widget)
            views.setTextViewText(R.id.appwidget_id, appWidgetId.toString())

            var count: Int = prefs.getInt(COUNT_KEY + appWidgetId, 0)
            count++;

            val dateString: String = DateFormat.getTimeInstance(DateFormat.SHORT).format(Date())
            views.setTextViewText(R.id.appwidget_update,
                context.resources.getString(R.string.date_count_format, count, dateString))

            prefs.edit {
                this.putInt(COUNT_KEY + appWidgetId, count)
                this.apply()
            }

            val intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.data = Uri.parse("https://www.google.com")

            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_MUTABLE
            )

            views.setOnClickPendingIntent(R.id.button_newpage, pendingIntent)

            // Unable to get update time to work
//            val intentUpdate = Intent(context, NewAppWidget::class.java)
//            intentUpdate.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
//            val idArray = IntArray(appWidgetId)
//            intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray)
//
//            val pendingUpdate = PendingIntent.getBroadcast(
//                context,
//                appWidgetId,
//                intentUpdate,
//                PendingIntent.FLAG_UPDATE_CURRENT
//            )
//            views.setOnClickPendingIntent(R.id.button_update, pendingUpdate)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

}