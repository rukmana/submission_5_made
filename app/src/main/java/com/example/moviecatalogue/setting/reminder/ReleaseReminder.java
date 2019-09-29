package com.example.moviecatalogue.setting.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.moviecatalogue.R;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import java.util.Calendar;

public class ReleaseReminder extends BroadcastReceiver {
    private static int id = 111;
    private String DISPATCHER_TAG = "DISPATCHER_TAG";
    FirebaseJobDispatcher firebaseJobDispatcher;

    @Override
    public void onReceive(Context context, Intent intent) {
        firebaseJobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        start();
    }

    public ReleaseReminder() {
    }

    private void start() {
        Job job = firebaseJobDispatcher.newJobBuilder()
                .setService(ReleaseReminderService.class)
                .setRecurring(false)
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setTrigger(Trigger.executionWindow(0, 1))
                .setReplaceCurrent(true)
                .setTag(DISPATCHER_TAG)
                .build();

        firebaseJobDispatcher.mustSchedule(job);
    }

    public void setAlarmRelease(Context ctx){
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(ctx, ReleaseReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, id, intent, 0);

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        Toast.makeText(ctx, R.string.on_release_reminder, Toast.LENGTH_SHORT).show();
    }

    public void cancelAlarmRelease(Context ctx){
        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(ctx, ReleaseReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(ctx, id, intent, 0);

        alarmManager.cancel(pendingIntent);

        Toast.makeText(ctx, R.string.off_release_reminder, Toast.LENGTH_SHORT).show();
    }
}
