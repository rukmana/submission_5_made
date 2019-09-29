package com.example.moviecatalogue.setting.reminder;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.moviecatalogue.MainActivity;
import com.example.moviecatalogue.R;
import com.example.moviecatalogue.dataSource.network.Utils;
import com.example.moviecatalogue.dataSource.network.model.movie.MoviesData;
import com.example.moviecatalogue.dataSource.network.model.movie.MoviesResponseData;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ReleaseReminderService extends JobService {
    private static int id = 111;
    private ArrayList<MoviesData> mData = new ArrayList<>();

    private void getData(final JobParameters jobParameters){

        String apiKey = getResources().getString(R.string.api_key);
        final Date nowDate = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = dateFormat.format(nowDate);

        Call<MoviesResponseData> call = Utils.geClient().getReleaseData(apiKey, date, date);
        call.enqueue(new Callback<MoviesResponseData>() {
            @Override
            public void onResponse(Call<MoviesResponseData> call, Response<MoviesResponseData> response) {
                if(response.isSuccessful()){
                    MoviesResponseData responseData = response.body();
                    try {
                        mData = responseData.getResults();
                        notification(getApplication(), mData.get(0).getTitle(), getResources().getString(R.string.new_release) + " " + mData.get(0).getTitle(), id);
                        jobFinished(jobParameters, false);
                    }catch (Exception e){
                        e.printStackTrace();
                        jobFinished(jobParameters, true);
                    }
                }
            }

            @Override
            public void onFailure(Call<MoviesResponseData> call, Throwable t) {
                Log.e("failure", "failure");
            }
        });
    }

    private void notification(Context ctx, String title, String message, int id) {
        String CHANNEL_ID = "Channel";
        String CHANNEL_NAME = "Release Reminder";

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


    @Override
    public boolean onStartJob(JobParameters params) {
        getData(params);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }
}
