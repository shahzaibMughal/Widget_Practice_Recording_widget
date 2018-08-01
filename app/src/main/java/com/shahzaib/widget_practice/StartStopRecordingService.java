package com.shahzaib.widget_practice;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class StartStopRecordingService extends IntentService {


    public StartStopRecordingService() {
        super("StartStopRecordingService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (isRecording()) {
            stopRecording();
        } else {
            startRecording();
        }
    }


    private boolean isRecording() {
        return RecordingWidget.isRecording(getApplicationContext());
    }

    private void stopRecording() {
        RecordingWidget.setRecordingStatus(getApplicationContext(), false);
        SHOW_LOG("Recording Stopped");
        updateWidgetUI();
    }

    private void startRecording() {
        RecordingWidget.setRecordingStatus(getApplicationContext(), true);
        SHOW_LOG("Recording Started");
        updateWidgetUI();
    }

    private void updateWidgetUI() {
        // updating the ui.........
        // RecordingWidget.updateAppWidget() method internally check is recording started or stopped and update ui accordingly
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecordingWidget.class));
        for (int appWidgetId : appWidgetIds) {
            RecordingWidget.updateAppWidget(this, appWidgetManager, appWidgetId);
        }
    }

    private void SHOW_LOG(String message) {
        Log.i("123456",message);
    }


}
