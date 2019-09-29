package com.example.moviecatalogue.setting.reminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.moviecatalogue.MainActivity;
import com.example.moviecatalogue.R;

import java.util.Calendar;

public class DailyReminder extends BroadcastReceiver {
    private static int id = 222;

    public DailyReminder() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        notification(context, context.getString(R.string.app_name), context.getResources().getString(R.string.message_daily_reminder), id);
    }

    private void notification(Context ctx, String title, String message, int id) {
        String CHANNEL_ID = "Channe_1";
        String CHANNEL_NAME = "Daily Reminder";

        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(ctx, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx)
                .setSmallIcon(R.drawable.ic_new_release)
                .setContentTitle(title)
                .setColor(ContextCompat.getColor(ctx, android.R.color.transparent))
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(sound);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = builder.build();
        notificationManager.notify(id, notification);
    }

    public void setAlarmDaily(Context ctx){
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(ctx, DailyReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, id, intent, 0);

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        Toast.makeText(ctx, R.string.on_daily_reminder, Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarmDaily(Context ctx){
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(ctx, DailyReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, id, intent, 0);

        alarmManager.cancel(pendingIntent);

        Toast.makeText(ctx, R.string.off_daily_reminder, Toast.LENGTH_SHORT).show();
    }
}
