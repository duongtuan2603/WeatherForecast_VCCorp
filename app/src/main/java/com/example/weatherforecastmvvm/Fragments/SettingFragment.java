package com.example.weatherforecastmvvm.Fragments;

import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.weatherforecastmvvm.R;

public class SettingFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        SharedPreferences mSharedPreference = requireContext().getSharedPreferences("com.example.weatherforecastmvvm", Context.MODE_PRIVATE);
        ListPreference mListTemperature = findPreference("TemperatureUnits");
        assert mListTemperature != null;
        mListTemperature.setOnPreferenceChangeListener((preference, newValue) -> {
            if (newValue.equals("Celsius")) {
                mSharedPreference.edit().putString("Temperature Unit", "\u2103").apply();
            } else {
                mSharedPreference.edit().putString("Temperature Unit", "\u2109").apply();
            }
            Toast.makeText(getContext(), "Please restart to apply changes", Toast.LENGTH_LONG).show();
            Log.e("Temperature Unit", "onCreatePreferences: " + mSharedPreference.getString("Temperature Unit", ""));
            return true;
        });
        ListPreference mListWindSpeed = findPreference("WindSpeedUnits");
        assert mListWindSpeed != null;
        mListWindSpeed.setOnPreferenceChangeListener((preference, newValue) -> {
            if (newValue.equals("kph")) {
                mSharedPreference.edit().putString("Wind Speed Unit", "kph").apply();
            } else {
                mSharedPreference.edit().putString("Wind Speed Unit", "mph").apply();
            }
            Toast.makeText(getContext(), "Please restart to apply changes", Toast.LENGTH_LONG).show();
            Log.e("Wind Speed Unit", "onCreatePreferences: " + mSharedPreference.getString("Wind Speed Unit", ""));
            return true;
        });
        ListPreference mListUpdate = findPreference("UpdateFrequency");
        assert mListUpdate != null;
        mListUpdate.setOnPreferenceChangeListener((preference, newValue) -> {
            switch (((String) newValue)) {
                case "15 minutes":
                    mSharedPreference.edit().putLong("Update Frequency", AlarmManager.INTERVAL_FIFTEEN_MINUTES).apply();
                    break;
                case "30 minutes":
                    mSharedPreference.edit().putLong("Update Frequency", AlarmManager.INTERVAL_HALF_HOUR).apply();
                    break;
                case "1 hour":
                    mSharedPreference.edit().putLong("Update Frequency", AlarmManager.INTERVAL_HOUR).apply();
                    break;
                case "12 hours":
                    mSharedPreference.edit().putLong("Update Frequency", AlarmManager.INTERVAL_HALF_DAY).apply();
                    break;
                case "1 day":
                    mSharedPreference.edit().putLong("Update Frequency", AlarmManager.INTERVAL_DAY).apply();
                    break;
            }
            return true;
        });


    }
}