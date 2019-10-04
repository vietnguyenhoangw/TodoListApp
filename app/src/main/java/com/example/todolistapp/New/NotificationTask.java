package com.example.todolistapp.New;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationTask {
    public static final String CHANNEL_ID ="com.example.todolistapp.New" ;
    public static final String CHANNEL_NAME = "todolistapp";

    public static void createNotificationChannel(Context context) {
        /* 1 - check OS API level >= 26 */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 2 - Create notification channel include necessary info
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel =
                    new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);
            channel.setDescription("Reminders");
            channel.setShowBadge(true);
            // 3 – Create notification channel using NotificationManager
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(channel);
        }
    }

    public static void createSampleNotification(Context context, int notificationId, int icon, String title, String message) {

        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 1 – Create notification with channel id include necessary info
            builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(icon)
                    .setContentTitle(title)
                    .setContentText(message);

        } else {
            // 2 – create notification in OS API level < 26 case.
            builder = new NotificationCompat.Builder(context)
                    .setSmallIcon(icon)
                    .setContentTitle(title)
                    .setContentText(message);
        }
        // 3 (step 4) – send notification to NotificationManager and show
        NotificationManager mNotificationManager =
                (NotificationManager)
                        context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notificationId, builder.build());
    }

}
