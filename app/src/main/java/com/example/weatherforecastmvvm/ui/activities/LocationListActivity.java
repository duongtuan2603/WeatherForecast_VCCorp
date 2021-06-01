package com.example.weatherforecastmvvm.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.weatherforecastmvvm.R;
import com.example.weatherforecastmvvm.data.database.SavedLocationRoomDatabase;
import com.example.weatherforecastmvvm.data.local.SavedLocation;
import com.example.weatherforecastmvvm.data.local.SharedPreferenceSettings;
import com.example.weatherforecastmvvm.databinding.ActivityLocationListBinding;
import com.example.weatherforecastmvvm.ui.adapter.SavedLocationAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class LocationListActivity extends AppCompatActivity {
    ActivityLocationListBinding mBinding;
    SavedLocationAdapter mAdapter;
    public SavedLocation savedLocation;
    String mFinalTemp;
    List<SavedLocation> mSavedLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(LocationListActivity.this, R.layout.activity_location_list);
        mFinalTemp = SharedPreferenceSettings.getSharedPreferenceSettings(getApplicationContext()).getTemperatureunit();
        FloatingActionButton buttonadd = mBinding.floatingActionButton2;


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
        if (SavedLocationRoomDatabase.getDatabase(this).savedLocationDao().getalllocations() == null || SavedLocationRoomDatabase.getDatabase(this).savedLocationDao().getalllocations().isEmpty()) {
            Intent intent = new Intent(LocationListActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LocationListActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    public void onItemClicked(int position) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("Location clicked", position);
        startActivity(intent);
    }
}
