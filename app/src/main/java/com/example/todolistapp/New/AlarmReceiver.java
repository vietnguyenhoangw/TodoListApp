package com.example.todolistapp.New;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.todolistapp.R;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationTask.createSampleNotification(context, 10,
                R.drawable.ic_launcher_background,
                "Todo List", "Please check your task now!!!");
    }
}
