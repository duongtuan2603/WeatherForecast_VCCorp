package com.example.weatherforecastmvvm.API;

import com.example.weatherforecastmvvm.ReturnedForecast;
import com.example.weatherforecastmvvm.SearchLocation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ForecastAPI {
    Gson gson = new GsonBuilder()
            .setDateFormat("hh-mm-ss dd-MM-YYYY")
            .create();
    ForecastAPI service = new Retrofit.Builder()
            .baseUrl("http://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ForecastAPI.class);
    @GET("forecast.json")
    Call<ReturnedForecast> forecast(@Query("key") String key,
                                    @Query("q") String q,
                                    @Query("days") int days,
                                    @Query("aqi") String aqi,
                                    @Query("alerts") String alerts,
                                    @Query("lang") String lang);
    @GET("search.json")
    Call<List<SearchLocation>> search(@Query("key") String key,
                                      @Query("q") String q);
}
