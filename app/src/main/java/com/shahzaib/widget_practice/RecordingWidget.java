package com.shahzaib.widget_practice;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class RecordingWidget extends AppWidgetProvider {
    public static String KEY_RECORDING_STATUS = "recordingStatus";


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recording);
        if (!isRecording(context)) {
            views.setViewVisibility(R.id.ic_mic, View.VISIBLE);
            views.setViewVisibility(R.id.ic_recording, View.GONE);
        } else {
            views.setViewVisibility(R.id.ic_mic, View.GONE);
            views.setViewVisibility(R.id.ic_recording, View.VISIBLE);
        }
        views.setOnClickPendingIntent(R.id.ic_mic, getOnWidgetClickPendingIntent(context));
        views.setOnClickPendingIntent(R.id.ic_recording, getOnWidgetClickPendingIntent(context));
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }


    public static PendingIntent getOnWidgetClickPendingIntent(Context context) {
        Intent startStopRecordingIntent = new Intent(context, StartStopRecordingService.class);
        return PendingIntent.getService(context, 0, startStopRecordingIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


    public static boolean isRecording(Context context) {
        SharedPreferences recordingStatusSP = context.getSharedPreferences("RecordingStatus", Context.MODE_PRIVATE);
        return recordingStatusSP.getBoolean(RecordingWidget.KEY_RECORDING_STATUS, false);
    }

    public static void setRecordingStatus(Context context, boolean isRecording) {
        SharedPreferences recordingStatusSP = context.getSharedPreferences("RecordingStatus", Context.MODE_PRIVATE);
        recordingStatusSP.edit().putBoolean(RecordingWidget.KEY_RECORDING_STATUS, isRecording).apply();
    }


}

