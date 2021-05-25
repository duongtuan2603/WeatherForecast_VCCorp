package com.example.weatherforecastmvvm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.weatherforecastmvvm.databinding.ActivityMainBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    List<SavedLocation> mSavedLocation;
    LocationPageAdapter locationPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //Add current location as the first database and setup viewpager
        if (SavedLocationRoomDatabase.getDatabase(getApplicationContext()).savedLocationDao().getalllocations() == null || SavedLocationRoomDatabase.getDatabase(getApplicationContext()).savedLocationDao().getalllocations().isEmpty()) {
            apiservice.service.forecast("bea12c175e9041c598b13943211005", "auto:ip", 7, "no", "no", "en").enqueue(new Callback<ReturnedForecast>() {
                @Override
                public void onResponse(Call<ReturnedForecast> call, Response<ReturnedForecast> response) {
                    SavedLocation savedLocation = new SavedLocation(response.body().location.name,
                            response.body().location.country,
                            response.body().current.temp_c,
                            response.body().current.temp_f,
                            response.body().current.condition.icon,
                            response.body().current.condition.text);
                    SavedLocationRoomDatabase.getDatabase(MainActivity.this).savedLocationDao().insert(savedLocation);
                    locationPageAdapter = new LocationPageAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, SavedLocationRoomDatabase.getDatabase(MainActivity.this).savedLocationDao().getalllocations());
                    locationPageAdapter.notifyDataSetChanged();
                    binding.viewpager.setAdapter(locationPageAdapter);
                    locationPageAdapter.notifyDataSetChanged();

                }

                @Override
                public void onFailure(Call<ReturnedForecast> call, Throwable t) {

                }
            });
        }

        //If current location exist, build viewpager only
        if (SavedLocationRoomDatabase.getDatabase(getApplicationContext()).savedLocationDao().getalllocations() != null || SavedLocationRoomDatabase.getDatabase(getApplicationContext()).savedLocationDao().getalllocations().size() != 0) {
            mSavedLocation = SavedLocationRoomDatabase.getDatabase(MainActivity.this).savedLocationDao().getalllocations();
            locationPageAdapter = new LocationPageAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mSavedLocation);
            locationPageAdapter.notifyDataSetChanged();
            binding.viewpager.setAdapter(locationPageAdapter);
            locationPageAdapter.notifyDataSetChanged();
        }


        //button add Location clicked
        binding.btnlocationlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LocationListActivity.class);
                startActivity(intent);
            }
        });
        locationPageAdapter.notifyDataSetChanged();


        //buttonsetting
        binding.btnsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentsetting = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intentsetting);
            }
        });
        locationPageAdapter.notifyDataSetChanged();
        Intent intentforeground = new Intent(this, ForegroundService.class);
        startService(intentforeground);

    }

    @Override
    protected void onResume() {
        super.onResume();
        locationPageAdapter.notifyDataSetChanged();

    }
}