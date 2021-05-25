package com.example.weatherforecastmvvm;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import java.io.Serializable;

public class Hour implements Serializable {
    long time_epoch;
    String time;
    double temp_c;
    double temp_f;
    int is_day;
    Condition condition;
    double wind_mph;
    double wind_kph;
    int wind_degree;
    String wind_dir;
    double pressure_mb;
    double pressure_in;
    double precip_mm;
    double precip_in;
    int humidity;
    int cloud;
    double feelslike_c;
    double feelslike_f;
    double windchill_c;
    double windchill_f;
    double heatindex_c;
    double heatindex_f;
    double dewpoint_c;
    double dewpoint_f;
    int will_it_rain;
    String chance_of_rain;
    int will_it_snow;
    String chance_of_snow;
    double vis_km;
    double vis_miles;
    double gust_mph;
    double gust_kph;
    double uv;

    public long getTime_epoch() {
        return time_epoch;
    }

    public String getTime() {
        return time;
    }

    public double getTemp_c() {
        return temp_c;
    }

    public double getTemp_f() {
        return temp_f;
    }

    public int getIs_day() {
        return is_day;
    }

    public Condition getCondition() {
        return condition;
    }

    public double getWind_mph() {
        return wind_mph;
    }

    public double getWind_kph() {
        return wind_kph;
    }

    public int getWind_degree() {
        return wind_degree;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public double getPressure_mb() {
        return pressure_mb;
    }

    public double getPressure_in() {
        return pressure_in;
    }

    public double getPrecip_mm() {
        return precip_mm;
    }

    public double getPrecip_in() {
        return precip_in;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getCloud() {
        return cloud;
    }

    public double getFeelslike_c() {
        return feelslike_c;
    }

    public double getFeelslike_f() {
        return feelslike_f;
    }

    public double getWindchill_c() {
        return windchill_c;
    }

    public double getWindchill_f() {
        return windchill_f;
    }

    public double getHeatindex_c() {
        return heatindex_c;
    }

    public double getHeatindex_f() {
        return heatindex_f;
    }

    public double getDewpoint_c() {
        return dewpoint_c;
    }

    public double getDewpoint_f() {
        return dewpoint_f;
    }

    public int getWill_it_rain() {
        return will_it_rain;
    }

    public String getChance_of_rain() {
        return chance_of_rain;
    }

    public int getWill_it_snow() {
        return will_it_snow;
    }

    public String getChance_of_snow() {
        return chance_of_snow;
    }

    public double getVis_km() {
        return vis_km;
    }

    public double getVis_miles() {
        return vis_miles;
    }

    public double getGust_mph() {
        return gust_mph;
    }

    public double getGust_kph() {
        return gust_kph;
    }

    public double getUv() {
        return uv;
    }
//    @BindingAdapter("binding:profileImage")
//    public static void loadImage(ImageView view, String imageUrl) {
//        Glide.with(view.getContext())
//                .load(imageUrl)
//                .into(view);
    }

