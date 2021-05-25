package com.example.weatherforecastmvvm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface apigetlocation {
    Gson gson = new GsonBuilder()
            .setDateFormat("hh-mm-ss dd-MM-YYYY")
            .create();
    apigetlocation getlocation = new Retrofit.Builder().baseUrl("http://ip-api.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(apigetlocation.class);
    @GET("json/")
    Call<UserLocation> userlocation(@Query("fields") String fields);
}
