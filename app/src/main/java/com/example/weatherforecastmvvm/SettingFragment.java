package com.example.weatherforecastmvvm;

import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        SharedPreferences mSharedPreference;
        String sharedPrefFile = "com.example.weatherforecastmvvm";
        mSharedPreference = requireContext().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE);
        ListPreference listtemperature = findPreference("TemperatureUnits");
        assert listtemperature != null;
        listtemperature.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (((String) newValue).equals("Celsius")) {
                    mSharedPreference.edit().putString("Temperature Unit", "\u2103").apply();
                } else {
                    mSharedPreference.edit().putString("Temperature Unit", "\u2109").apply();
                }
                Toast.makeText(getContext(), "Please restart to apply changes", Toast.LENGTH_LONG).show();
                Log.e("Temperature Unit", "onCreatePreferences: " + mSharedPreference.getString("Temperature Unit", ""));
                return true;
            }
        });
        ListPreference listwind = findPreference("WindSpeedUnits");
        assert listwind != null;
        listwind.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (((String) newValue).equals("kph")) {
                    mSharedPreference.edit().putString("Wind Speed Unit", "kph").apply();
                } else {
                    mSharedPreference.edit().putString("Wind Speed Unit", "mph").apply();
                }
                Toast.makeText(getContext(), "Please restart to apply changes", Toast.LENGTH_LONG).show();
                Log.e("Wind Speed Unit", "onCreatePreferences: " + mSharedPreference.getString("Wind Speed Unit", ""));
                return true;
            }
        });
        ListPreference listupdate = findPreference("UpdateFrequency");
        listupdate.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if (((String) newValue).equals("15 minutes")) {
                    mSharedPreference.edit().putLong("Update Frequency", AlarmManager.INTERVAL_FIFTEEN_MINUTES).apply();
                } else if (((String) newValue).equals("30 minutes")) {
                    mSharedPreference.edit().putLong("Update Frequency", AlarmManager.INTERVAL_HALF_HOUR).apply();
                } else if (((String) newValue).equals("1 hour")) {
                    mSharedPreference.edit().putLong("Update Frequency", AlarmManager.INTERVAL_HOUR).apply();
                } else if (((String) newValue).equals("12 hours")) {
                    mSharedPreference.edit().putLong("Update Frequency", AlarmManager.INTERVAL_HALF_DAY).apply();
                } else if (((String) newValue).equals("1 day")) {
                    mSharedPreference.edit().putLong("Update Frequency", AlarmManager.INTERVAL_DAY).apply();
                }
                return true;
            }
        });


    }
}