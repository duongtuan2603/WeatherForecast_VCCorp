package com.example.weatherforecastmvvm.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.weatherforecastmvvm.api.ForecastAPI;
import com.example.weatherforecastmvvm.ui.adapter.RecentLocationAdapter;
import com.example.weatherforecastmvvm.ui.adapter.SearchLocationAdapter;
import com.example.weatherforecastmvvm.data.database.SavedLocationRoomDatabase;
import com.example.weatherforecastmvvm.data.local.RecentLocation;
import com.example.weatherforecastmvvm.R;
import com.example.weatherforecastmvvm.data.model.getapiforecast.ReturnedForecast;
import com.example.weatherforecastmvvm.data.local.SearchLocation;
import com.example.weatherforecastmvvm.databinding.ActivityAddLocationBinding;
import com.example.weatherforecastmvvm.data.local.SavedLocation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLocationActivity extends AppCompatActivity {
    ActivityAddLocationBinding mBinding;
    List<SearchLocation> mSearchLocation = new ArrayList<>();
    List<RecentLocation> mRecentLocation = new ArrayList<>();
    SearchLocationAdapter mSearchLocationAdapter;
    RecentLocationAdapter mRecentLocationAdapter;
    String mGetLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_location);
        //
        mRecentLocationAdapter = new RecentLocationAdapter(SavedLocationRoomDatabase.getDatabase(this).savedLocationDao().getalllocations(),this );
        mBinding.recyclerviewaddlocation.setAdapter(mRecentLocationAdapter);
        mBinding.recyclerviewaddlocation.setLayoutManager(new LinearLayoutManager(this));
        mBinding.edtlocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mGetLocation = mBinding.edtlocation.getText().toString();
                if (!mGetLocation.isEmpty()) {
                    searchlocation();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void searchlocation() {
        ForecastAPI.service.search("bea12c175e9041c598b13943211005", mGetLocation).enqueue(new Callback<List<SearchLocation>>() {
            @Override
            public void onResponse(Call<List<SearchLocation>> call, Response<List<SearchLocation>> response) {
                mSearchLocation = response.body();
                mSearchLocationAdapter = new SearchLocationAdapter(mSearchLocation, AddLocationActivity.this);
                mBinding.recyclerviewaddlocation.setAdapter(mSearchLocationAdapter);
                mBinding.recyclerviewaddlocation.setLayoutManager(new LinearLayoutManager(AddLocationActivity.this));

            }

            @Override
            public void onFailure(Call<List<SearchLocation>> call, Throwable t) {
                Log.e("Search Location", "onFailure: Can not get API");
            }
        });
    }

    public void ChosenLocation(String name) {
        mRecentLocationAdapter.notifyDataSetChanged();
        ForecastAPI.service.forecast("bea12c175e9041c598b13943211005", name, 7, "no", "no", "en").enqueue(new Callback<ReturnedForecast>() {
            @Override
            public void onResponse(Call<ReturnedForecast> call, Response<ReturnedForecast> response) {
                SavedLocation savedLocation = new SavedLocation(response.body().getLocation().getName(),
                        response.body().getLocation().getCountry(),
                        response.body().getCurrent().getTemp_c(),
                        response.body().getCurrent().getTemp_f(),
                        response.body().getCurrent().getCondition().getIcon(),
                        response.body().getCurrent().getCondition().getText());
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