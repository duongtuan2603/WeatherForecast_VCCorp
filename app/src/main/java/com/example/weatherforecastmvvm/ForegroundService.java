package com.example.weatherforecastmvvm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForegroundService extends Service {
    SharedPreferences sharedPreferences;
    String finaltemp;
    Long updatefrequency;

    private static final String CHANNEL_ID = "CHANNEL_ID";

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getApplicationContext().getSharedPreferences("com.example.weatherforecastmvvm", MODE_PRIVATE);
        finaltemp = sharedPreferences.getString("Temperature Unit", "\u2103");
        updatefrequency = sharedPreferences.getLong("Update Frequency", AlarmManager.INTERVAL_HALF_HOUR);

        Log.e("Service", "Successfully created");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sendnotification();
        return START_NOT_STICKY;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendnotification() {
        //Create channel ID
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        //Create Notification and start foreground
        apiservice.service.forecast("bea12c175e9041c598b13943211005", "auto:ip", 7, "no", "no", "en").enqueue(new Callback<ReturnedForecast>() {
            @Override
            public void onResponse(Call<ReturnedForecast> call, Response<ReturnedForecast> response) {
                Intent intent = new Intent(ForegroundService.this, MainActivity.class);
                //Set repeating update
                AlarmManager alarmManager =
                        (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_NO_CREATE);
                alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime() + updatefrequency,
                        updatefrequency,
                        pendingIntent);
                Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_baseline_wb_sunny_24)
                        .setContentTitle(response.body().location.name + " - " + response.body().location.country)
                        .setContentText(finaltemp.equals("\u2103")
                                ? (int) response.body().current.temp_c + " " + finaltemp + " - " + response.body().current.condition.text
                                : (int) response.body().current.temp_f + " " + finaltemp + " - " + response.body().current.condition.text)
                        .setPriority(Notification.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .build();
                startForeground(1, notification);

            }

            @Override
            public void onFailure(Call<ReturnedForecast> call, Throwable t) {

            }
        });


    }
}
