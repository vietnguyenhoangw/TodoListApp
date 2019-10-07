package com.example.todolistapp.New;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.todolistapp.R;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String id = intent.getStringExtra("ID");

        NotificationTask.createSampleNotification(context,
                Integer.parseInt(id),
                R.drawable.ic_notifications_active_black_24dp,
                intent.getStringExtra("TaskName"),
                intent.getStringExtra("TaskDescription"));
    }
}
