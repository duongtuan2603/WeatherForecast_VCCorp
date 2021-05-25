package com.example.weatherforecastmvvm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforecastmvvm.databinding.ActivityLocationListBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationListActivity extends AppCompatActivity {
    public RecyclerView recyclerViewsavedlocation;
    ActivityLocationListBinding binding;
    public SavedLocationAdapter adapter;
    public SavedLocation savedLocation;
    SharedPreferences sharedPreferences;
    String finaltemp;
    List<SavedLocation> mSavedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(LocationListActivity.this, R.layout.activity_location_list);

        sharedPreferences = getSharedPreferences("com.example.weatherforecastmvvm", MODE_PRIVATE);
        finaltemp = sharedPreferences.getString("Temperature Unit", "");
        FloatingActionButton buttonadd = binding.floatingActionButton2;
        recyclerViewsavedlocation = binding.recyclerviewsavedlocation;
        if (SavedLocationRoomDatabase.getDatabase(getApplicationContext()).savedLocationDao().getalllocations().isEmpty()) {
            apiservice.service.forecast("bea12c175e9041c598b13943211005", "auto:ip", 7, "no", "no", "en").enqueue(new Callback<ReturnedForecast>() {
                @Override
                public void onResponse(Call<ReturnedForecast> call, Response<ReturnedForecast> response) {
                    SavedLocation savedLocation = new SavedLocation(response.body().location.name,
                            response.body().location.country,
                            response.body().current.temp_c,
                            response.body().current.temp_f,
                            response.body().current.condition.icon,
                            response.body().current.condition.text);
                    SavedLocationRoomDatabase.getDatabase(getApplicationContext()).savedLocationDao().insert(savedLocation);

                }

                @Override
                public void onFailure(Call<ReturnedForecast> call, Throwable t) {

                }
            });
        }


        //Tap to add a Location
        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentaddlocation = new Intent(LocationListActivity.this, AddLocationActivity.class);
                startActivity(intentaddlocation);
            }
        });
        //Setup a recyclerview
        mSavedLocation = SavedLocationRoomDatabase.getDatabase(getApplicationContext()).savedLocationDao().getalllocations();
        Log.e("Size", "onCreate: " + mSavedLocation.size());
        adapter = new SavedLocationAdapter(mSavedLocation, LocationListActivity.this);
        recyclerViewsavedlocation.setAdapter(adapter);
        recyclerViewsavedlocation.setLayoutManager(new LinearLayoutManager(LocationListActivity.this));

        //Button Back
        binding.btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocationListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mSavedLocation = SavedLocationRoomDatabase.getDatabase(getApplicationContext()).savedLocationDao().getalllocations();
        Log.e("Size", "onResume: " + mSavedLocation.size());
        adapter.notifyDataSetChanged();
    }

    public void deletelocation(int position) {
        SavedLocationRoomDatabase.getDatabase(this).savedLocationDao().deletebyname(mSavedLocation.get(position).locationname);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LocationListActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }
}
