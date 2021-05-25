package com.example.weatherforecastmvvm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface apiservice {
    Gson gson = new GsonBuilder()
            .setDateFormat("hh-mm-ss dd-MM-YYYY")
            .create();
    apiservice service = new Retrofit.Builder()
            .baseUrl("http://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(apiservice.class);
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
