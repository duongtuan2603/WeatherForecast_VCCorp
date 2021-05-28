package com.example.weatherforecastmvvm.Service;

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

import com.example.weatherforecastmvvm.API.ForecastAPI;
import com.example.weatherforecastmvvm.R;
import com.example.weatherforecastmvvm.ReturnedForecast;
import com.example.weatherforecastmvvm.Screens.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForegroundService extends Service {
    SharedPreferences sharedPreferences;
    String finaltemp;
    Long updatefrequency;
    PendingIntent pendingIntent;

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
        createchannelID();
        Intent intentnotification = new Intent(ForegroundService.this, MainActivity.class);
        AlarmManager alarmManager =
                (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intentnotification, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + updatefrequency,
                updatefrequency,
                pendingIntent);
        sendnotification();
        return START_NOT_STICKY;
    }

    private void createchannelID() {
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
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendnotification() {
        //Create Notification and start foreground
        ForecastAPI.service.forecast("bea12c175e9041c598b13943211005", "auto:ip", 7, "no", "no", "en").enqueue(new Callback<ReturnedForecast>() {
            @Override
            public void onResponse(Call<ReturnedForecast> call, Response<ReturnedForecast> response) {
                //Create Notification
                Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_baseline_wb_sunny_24)
                        .setContentTitle(response.body().getLocation().getName() + " - " + response.body().getLocation().getCountry())
                        .setContentText(finaltemp.equals("\u2103")
                                ? (int) response.body().getCurrent().getTemp_c() + " " + finaltemp + " - " + response.body().getCurrent().getCondition().getText()
                                : (int) response.body().getCurrent().getTemp_f() + " " + finaltemp + " - " + response.body().getCurrent().getCondition().getText())
                        .setPriority(Notification.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .build();
                startForeground(1, notification);
            }

            @Override
            public void onFailure(Call<ReturnedForecast> call, Throwable t) {
                Log.e("Service", "onFailure: Can not get API");
            }
        });


    }
}
