package com.example.weatherforecastmvvm.ui.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.weatherforecastmvvm.ui.fragments.SettingFragment;
import com.example.weatherforecastmvvm.R;
import com.example.weatherforecastmvvm.databinding.SettingsActivityBinding;

public class SettingsActivity extends AppCompatActivity {
    SettingsActivityBinding mBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}