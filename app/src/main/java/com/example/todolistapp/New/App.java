package com.example.todolistapp.New;

import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NotificationTask.createNotificationChannel(this);
    }
}
