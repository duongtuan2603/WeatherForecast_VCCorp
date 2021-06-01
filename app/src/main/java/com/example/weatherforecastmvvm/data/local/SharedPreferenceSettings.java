package com.example.weatherforecastmvvm.data.local;

import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.weatherforecastmvvm.data.local.Settings;

public class SharedPreferenceSettings {
    private static SharedPreferences preferences;
    private SharedPreferenceSettings() {
    }

    public static Settings getSharedPreferenceSettings(Context context) {
        preferences = context.getSharedPreferences("com.example.weatherforecastmvvm", Context.MODE_PRIVATE);
        return new Settings(preferences.getString("Temperature Unit", "\u2103"),
                preferences.getString("Wind Speed Unit", "kph"),
                preferences.getLong("Update Frequency", AlarmManager.INTERVAL_HALF_HOUR));
    }

}
