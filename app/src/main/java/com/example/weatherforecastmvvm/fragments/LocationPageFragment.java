package com.example.weatherforecastmvvm.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.weatherforecastmvvm.api.ForecastAPI;
import com.example.weatherforecastmvvm.adapter.ForecastDayAdapter;
import com.example.weatherforecastmvvm.adapter.HourAdapter;
import com.example.weatherforecastmvvm.R;
import com.example.weatherforecastmvvm.getapiforecast.ReturnedForecast;
import com.example.weatherforecastmvvm.screen.MainActivity;
import com.example.weatherforecastmvvm.databinding.FragmentLocationPageBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationPageFragment extends Fragment {

    String mLocationName;
    SharedPreferences mSharedPreferences;
    String mFinalTemp;
    String mFinalWind;
    ReturnedForecast mReturnedForecast;
    HourAdapter mHourAdapter;
    ForecastDayAdapter mDayAdapter;
    FragmentLocationPageBinding mBinding;

    public LocationPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentLocationPageBinding.inflate(inflater, container, false);
        Bundle bundle = getArguments();
        assert bundle != null;
        mLocationName = bundle.getString("Location Page Fragment");
        return mBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mSharedPreferences = requireContext().getSharedPreferences("com.example.weatherforecastmvvm", Context.MODE_PRIVATE);
        mFinalTemp = mSharedPreferences.getString("Temperature Unit", "\u2103");
        mFinalWind = mSharedPreferences.getString("Wind Speed Unit", "kph");
        Log.e("Temperature Unit", "onCreate: " + mSharedPreferences.getString("Temperature Unit", "can not get"));
        //Call API Forecast
        ForecastAPI.service.forecast("bea12c175e9041c598b13943211005", mLocationName, 7, "yes", "no", "en").enqueue(new Callback<ReturnedForecast>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ReturnedForecast> call, Response<ReturnedForecast> response) {
                if (response.body() != null) {
                    mReturnedForecast = response.body();
                    mBinding.setReturnedforecast(mReturnedForecast);

                    //Set Temperature Unit and Wind Speed Unit
                    setTemperatureunitAndwindSpeedunit();

                    //in-day forecast
                    setRecyclerviewHour();

                    //seven-day forecast
                    setRecyclerviewDay();

                    //Set UV index
                    setUVindex();

                    //Set AQI index
                    setAQIindex();

                }
            }

            @Override
            public void onFailure(Call<ReturnedForecast> call, Throwable t) {
                Log.e("Home Fragment", "Can not get forecast API");
            }
        });
    }

    private void setAQIindex() {
        float aqi = mReturnedForecast.getCurrent().getAir_quality().getPm2_5();
        if (aqi > 0 && aqi < 12) {
            mBinding.aqititle.setText(R.string.Good);
            mBinding.aqidescription.setText(R.string.Air_quality_is_considered_satisfactory);
        } else if (aqi > 12 && aqi < 35.5) {
            mBinding.aqititle.setText(R.string.Moderate);
            mBinding.aqidescription.setText(R.string.Air_quality_is_acceptable);
        } else if (aqi > 35.5 && aqi < 55.5) {
            mBinding.aqititle.setText(R.string.Unhealthy_for_Sensitive_Groups);
            mBinding.aqidescription.setText(R.string.Members_of_sensitive_groups_may_experience_health_effects);
        } else if (aqi > 55.5 && aqi < 150.5) {
            mBinding.aqititle.setText(R.string.Unhealthy);
            mBinding.aqidescription.setText(R.string.Everyone_may_begin_to_experience_health_effects);
        } else if (aqi > 150.5 && aqi < 250.5) {
            mBinding.aqititle.setText(R.string.Very_Unhealthy);
            mBinding.aqidescription.setText(R.string.Health_warnings_of_emergency_conditions);
        } else {
            mBinding.aqititle.setText(R.string.Hazardous);
            mBinding.aqidescription.setText(R.string.Health_alert_everyone_may_experience_more_serious_health_effects);
        }
    }

    private void setUVindex() {
        float uvindex = mReturnedForecast.getCurrent().getUv();
        if (uvindex > 0 && uvindex < 2) {
            mBinding.uvtitle.setText(R.string.Low);
            mBinding.uvdescription.setText(R.string.No_protection_needed);
        } else if (uvindex > 3 && uvindex < 7) {
            mBinding.uvtitle.setText(R.string.Moderate_to_High);
            mBinding.uvdescription.setText(R.string.Protection_needed);
        } else {
            mBinding.uvtitle.setText(R.string.Very_High_to_Extreme);
            mBinding.uvdescription.setText(R.string.Extra_protection_needed);
        }
    }

    private void setTemperatureunitAndwindSpeedunit() {
        //Set Current temperature and wind speed Unit
        mBinding.txtcurrenttempunit.setText(mFinalTemp);
        mBinding.currentwindunit.setText(mFinalWind);

        //Set Current Temp
        if (mFinalTemp.equals("\u2109")) {
            mBinding.txtcurrenttemp.setText(String.valueOf((int) mReturnedForecast.getCurrent().getTemp_f()));
            mBinding.dewpointtemp.setText(String.valueOf(mReturnedForecast.getCurrent().getCloud())+"\u2109");
            mBinding.feelsLikeTemp.setText( String.valueOf(mReturnedForecast.getCurrent().getFeelslike_f()) +"\u2109");
        } else {
            mBinding.txtcurrenttemp.setText(String.valueOf((int) mReturnedForecast.getCurrent().getTemp_c()));
            mBinding.dewpointtemp.setText(String.valueOf(mReturnedForecast.getCurrent().getCloud())+"\u2103");
            mBinding.feelsLikeTemp.setText( String.valueOf(mReturnedForecast.getCurrent().getFeelslike_f()) +"\u2103");

        }
        //Set current wind speed
        if (mFinalWind.equals("kph")) {
            mBinding.currentwind.setText(String.valueOf((int) mReturnedForecast.getCurrent().getWind_kph()));
        } else {
            mBinding.currentwind.setText(String.valueOf((int) mReturnedForecast.getCurrent().getWind_mph()));
        }
    }

    //in-day forecast
    private void setRecyclerviewHour() {
        LinearLayoutManager hourlayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mHourAdapter = new HourAdapter(mReturnedForecast.getForecast().getForecastday().get(0).getHour(), (MainActivity) getContext());
        mBinding.recyclerviewhour.setLayoutManager(hourlayout);
        mHourAdapter.notifyDataSetChanged();
        mBinding.recyclerviewhour.setAdapter(mHourAdapter);
        mHourAdapter.notifyDataSetChanged();

    }


    //7-day forecast
    private void setRecyclerviewDay() {
        LinearLayoutManager daylayout = new LinearLayoutManager(getContext());
        mDayAdapter = new ForecastDayAdapter(mReturnedForecast.getForecast().getForecastday(), getContext());
        mDayAdapter.notifyDataSetChanged();
        mBinding.recyclerviewday.setLayoutManager(daylayout);
        mBinding.recyclerviewday.setAdapter(mDayAdapter);
        mDayAdapter.notifyDataSetChanged();
    }
}