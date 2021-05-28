package com.example.weatherforecastmvvm.Screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.weatherforecastmvvm.API.ForecastAPI;
import com.example.weatherforecastmvvm.Adapter.SavedLocationAdapter;
import com.example.weatherforecastmvvm.Database.SavedLocationRoomDatabase;
import com.example.weatherforecastmvvm.R;
import com.example.weatherforecastmvvm.ReturnedForecast;
import com.example.weatherforecastmvvm.SavedLocation;
import com.example.weatherforecastmvvm.databinding.ActivityLocationListBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationListActivity extends AppCompatActivity {
    ActivityLocationListBinding mBinding;
    SavedLocationAdapter mAdapter;
    public SavedLocation savedLocation;
    SharedPreferences mSharedPreference;
    String mFinalTemp;
    List<SavedLocation> mSavedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(LocationListActivity.this, R.layout.activity_location_list);

        mSharedPreference = getSharedPreferences("com.example.weatherforecastmvvm", MODE_PRIVATE);
        mFinalTemp = mSharedPreference.getString("Temperature Unit", "");
        FloatingActionButton buttonadd = mBinding.floatingActionButton2;
        if (SavedLocationRoomDatabase.getDatabase(getApplicationContext()).savedLocationDao().getalllocations().isEmpty()) {
            ForecastAPI.service.forecast("bea12c175e9041c598b13943211005", "auto:ip", 7, "no", "no", "en").enqueue(new Callback<ReturnedForecast>() {
                @Override
                public void onResponse(Call<ReturnedForecast> call, Response<ReturnedForecast> response) {
                    SavedLocation savedLocation = new SavedLocation(response.body().getLocation().getName(),
                            response.body().getLocation().getCountry(),
                            response.body().getCurrent().getTemp_c(),
                            response.body().getCurrent().getTemp_f(),
                            response.body().getCurrent().getCondition().getIcon(),
                            response.body().getCurrent().getCondition().getText());
                    SavedLocationRoomDatabase.getDatabase(getApplicationContext()).savedLocationDao().insert(savedLocation);

                }

                @Override
                public void onFailure(Call<ReturnedForecast> call, Throwable t) {

                }
            });
        }


        //Tap to add a Location
        buttonadd.setOnClickListener(v -> {
            Intent intentaddlocation = new Intent(LocationListActivity.this, AddLocationActivity.class);
            startActivity(intentaddlocation);
            finish();
        });
        //Setup a recyclerview
        mSavedLocation = SavedLocationRoomDatabase.getDatabase(getApplicationContext()).savedLocationDao().getalllocations();
        Log.e("Size", "onCreate: " + mSavedLocation.size());
        mAdapter = new SavedLocationAdapter(mSavedLocation, LocationListActivity.this);
        mBinding.recyclerviewsavedlocation.setAdapter(mAdapter);
        mBinding.recyclerviewsavedlocation.setLayoutManager(new LinearLayoutManager(LocationListActivity.this));

        //Button Back
        mBinding.btnback.setOnClickListener(v -> {
            Intent intent = new Intent(LocationListActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mSavedLocation = SavedLocationRoomDatabase.getDatabase(getApplicationContext()).savedLocationDao().getalllocations();
        Log.e("Size", "onResume: " + mSavedLocation.size());
        mAdapter.notifyDataSetChanged();
    }

    public void deletelocation(int position) {
        SavedLocationRoomDatabase.getDatabase(this).savedLocationDao().deletebyname(mSavedLocation.get(position).getLocationname());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LocationListActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}
