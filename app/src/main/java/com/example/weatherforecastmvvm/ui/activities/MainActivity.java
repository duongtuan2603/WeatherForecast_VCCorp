package com.example.weatherforecastmvvm.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.weatherforecastmvvm.api.ForecastAPI;
import com.example.weatherforecastmvvm.ui.adapter.LocationPageAdapter;
import com.example.weatherforecastmvvm.data.database.SavedLocationRoomDatabase;
import com.example.weatherforecastmvvm.R;
import com.example.weatherforecastmvvm.data.model.getapiforecast.ReturnedForecast;
import com.example.weatherforecastmvvm.data.local.SavedLocation;
import com.example.weatherforecastmvvm.service.ForegroundService;
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
            ForecastAPI.service.forecast("bea12c175e9041c598b13943211005", "auto:ip", 7, "no", "no", "en").enqueue(new Callback<ReturnedForecast>() {
                @Override
                public void onResponse(Call<ReturnedForecast> call, Response<ReturnedForecast> response) {
                    SavedLocation savedLocation = new SavedLocation(response.body().getLocation().getName(),
                            response.body().getLocation().getCountry(),
                            response.body().getCurrent().getTemp_c(),
                            response.body().getCurrent().getTemp_f(),
                            response.body().getCurrent().getCondition().getIcon(),
                            response.body().getCurrent().getCondition().getText());
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
        binding.btnlocationlist.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LocationListActivity.class);
            startActivity(intent);
        });
        locationPageAdapter.notifyDataSetChanged();


        //buttonsetting
        binding.btnsetting.setOnClickListener(v -> {
            Intent intentsetting = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intentsetting);
        });
        locationPageAdapter.notifyDataSetChanged();
        Intent intentforeground = new Intent(this, ForegroundService.class);
        startService(intentforeground);

        //set current view
        setCurrentView();

    }

    private void setCurrentView() {
        Intent intent = getIntent();
        binding.viewpager.setCurrentItem(intent.getIntExtra("Location clicked",0));
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationPageAdapter.notifyDataSetChanged();

    }
}