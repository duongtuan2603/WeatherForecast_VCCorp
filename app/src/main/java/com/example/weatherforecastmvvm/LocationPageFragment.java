package com.example.weatherforecastmvvm;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforecastmvvm.databinding.FragmentLocationPageBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationPageFragment extends Fragment {

    String locationname;
    SharedPreferences MainsharedPreferences;
    RecyclerView recyclerViewHour;
    RecyclerView recyclerViewDay;
    String finaltemp;
    String finalwind;
    ReturnedForecast returnedForecast;
    FragmentLocationPageBinding binding;

    public LocationPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLocationPageBinding.inflate(inflater, container, false);
        Bundle bundle = getArguments();
        assert bundle != null;
        locationname = bundle.getString("Location Page Fragment");
        MainsharedPreferences = requireContext().getSharedPreferences("com.example.weatherforecastmvvm", Context.MODE_PRIVATE);
        finaltemp = MainsharedPreferences.getString("Temperature Unit", "\u2103");
        finalwind = MainsharedPreferences.getString("Wind Speed Unit", "kph");
        Log.e("Temperature Unit", "onCreate: " + MainsharedPreferences.getString("Temperature Unit", "can not get"));


        //Call API Forecast
        apiservice.service.forecast("bea12c175e9041c598b13943211005", locationname, 7, "yes", "no", "en").enqueue(new Callback<ReturnedForecast>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ReturnedForecast> call, Response<ReturnedForecast> response) {
                if (response.body() != null) {
                    returnedForecast = response.body();
                    Log.e("Current Faraheit", String.valueOf((int) returnedForecast.current.temp_f));
                    binding.setReturnedforecast(returnedForecast);
                    Log.d("Called API", returnedForecast.location.name);


                    //Set Current temperature and wind speed Unit
                    binding.txtcurrenttempunit.setText(finaltemp);
                    binding.currentwindunit.setText(finalwind);

                    //Set Current Temp
                    if (finaltemp.equals("\u2109")) {
                        binding.txtcurrenttemp.setText(String.valueOf((int) returnedForecast.current.temp_f));
                        binding.dewpointtemp.setText(returnedForecast.current.cloud + finaltemp);
                        binding.feelsLikeTemp.setText(returnedForecast.current.feelslike_f + finaltemp);
                    } else {
                        binding.txtcurrenttemp.setText(String.valueOf((int) returnedForecast.current.temp_c));
                        binding.dewpointtemp.setText(returnedForecast.current.cloud + finaltemp);
                        binding.feelsLikeTemp.setText(returnedForecast.current.feelslike_c + finaltemp);

                    }
                    //Set current wind speed
                    if (finalwind.equals("kph")) {
                        binding.currentwind.setText(String.valueOf((int) returnedForecast.current.wind_kph));
                    } else {
                        binding.currentwind.setText(String.valueOf((int) returnedForecast.current.wind_mph));
                    }


                    //in-day forecast
                    recyclerviewhour();
                    //seven-day forecast
                    recyclerviewday();

                    //Set UV index
                    float uvindex = returnedForecast.current.uv;
                    if (uvindex > 0 && uvindex < 2) {
                        binding.uvtitle.setText("Low");
                        binding.uvdescription.setText("No protection needed");
                    } else if (uvindex > 3 && uvindex < 7) {
                        binding.uvtitle.setText("Moderate to High");
                        binding.uvdescription.setText("Protection needed");
                    } else {
                        binding.uvtitle.setText("Very High to Extreme");
                        binding.uvdescription.setText("Extra protection needed");
                    }

                    //Set AQI index
                    float aqi = returnedForecast.current.air_quality.pm2_5;
                    if (aqi > 0 && aqi < 12) {
                        binding.aqititle.setText("Good");
                        binding.aqidescription.setText("Air quality is considered satisfactory");
                    } else if (aqi > 12 && aqi < 35.5) {
                        binding.aqititle.setText("Moderate");
                        binding.aqidescription.setText("Air quality is acceptable");
                    } else if (aqi > 35.5 && aqi < 55.5) {
                        binding.aqititle.setText("Unhealthy for Sensitive Groups");
                        binding.aqidescription.setText("Members of sensitive groups may experience health effects");
                    } else if (aqi > 55.5 && aqi < 150.5) {
                        binding.aqititle.setText("Unhealthy");
                        binding.aqidescription.setText("Everyone may begin to experience health effects");
                    } else if (aqi > 150.5 && aqi < 250.5) {
                        binding.aqititle.setText("Very unhealthy");
                        binding.aqidescription.setText("Health warnings of emergency conditions");
                    } else {
                        binding.aqititle.setText("Hazardous");
                        binding.aqidescription.setText("Health alert: everyone may experience more serious health effects");
                    }

                    //Set alert
//                             if (returnedForecast.alerts.alert.size()!=0){binding.alerttitle.setText(returnedForecast.alerts.alert.get(0).event);
//                                                                    binding.aqidescription.setText(returnedForecast.alerts.alert.get(0).desc);}


                }
            }

            @Override
            public void onFailure(Call<ReturnedForecast> call, Throwable t) {
                Log.e("Failed", "Can not get forecast API");
            }
        });
        return binding.getRoot();
    }

    //in-day forecast
    private void recyclerviewhour() {
        recyclerViewHour = binding.recyclerviewhour;
        LinearLayoutManager hourlayout = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        HourAdapter houradapter = new HourAdapter(returnedForecast.forecast.forecastday.get(0).hour, (MainActivity) getContext());
        recyclerViewHour.setLayoutManager(hourlayout);
        recyclerViewHour.setAdapter(houradapter);

    }

    //7-day forecast
    private void recyclerviewday() {
        recyclerViewDay = binding.recyclerviewday;
        LinearLayoutManager daylayout = new LinearLayoutManager(getContext());
        ForecastDayAdapter dayadapter = new ForecastDayAdapter(returnedForecast.forecast.forecastday, getContext());
        recyclerViewDay.setLayoutManager(daylayout);
        recyclerViewDay.setAdapter(dayadapter);
    }
}