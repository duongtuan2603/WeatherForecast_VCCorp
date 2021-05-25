package com.example.weatherforecastmvvm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforecastmvvm.databinding.ActivityAddLocationBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLocationActivity extends AppCompatActivity {
    ActivityAddLocationBinding binding;
    ImageButton btn;
    List<SearchLocation> mSearchLocation = new ArrayList<>();
    SearchLocationAdapter adapter;
    EditText edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_location);
        btn = binding.btnaddlocation;
        edt = binding.edtlocation;
        RecyclerView recyclerView = binding.recyclerviewaddlocation;


        //add Location
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiservice.service.search("bea12c175e9041c598b13943211005", binding.getQ()).enqueue(new Callback<List<SearchLocation>>() {
                    @Override
                    public void onResponse(Call<List<SearchLocation>> call, Response<List<SearchLocation>> response) {
                        mSearchLocation = response.body();
                        adapter = new SearchLocationAdapter(mSearchLocation, AddLocationActivity.this);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(AddLocationActivity.this));

                    }

                    @Override
                    public void onFailure(Call<List<SearchLocation>> call, Throwable t) {

                    }
                });
            }
        });

    }

    public void ChosenLocation(String name) {
        apiservice.service.forecast("bea12c175e9041c598b13943211005", name, 7, "no", "no", "en").enqueue(new Callback<ReturnedForecast>() {
            @Override
            public void onResponse(Call<ReturnedForecast> call, Response<ReturnedForecast> response) {
                SavedLocation savedLocation = new SavedLocation(response.body().location.name,
                        response.body().location.country,
                        response.body().current.temp_c,
                        response.body().current.temp_f,
                        response.body().current.condition.icon,
                        response.body().current.condition.text);
                SavedLocationRoomDatabase.getDatabase(getApplicationContext()).savedLocationDao().insert(savedLocation);
                Intent intent = new Intent(AddLocationActivity.this, LocationListActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<ReturnedForecast> call, Throwable t) {
            }
        });
    }
}