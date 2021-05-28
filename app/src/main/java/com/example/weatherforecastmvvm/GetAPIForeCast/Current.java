package com.example.weatherforecastmvvm.GetAPIForeCast;

import java.io.Serializable;

public class Current implements Serializable {
    int last_updated_epoch;
    String last_updated;
    float temp_c;
    float temp_f;
    int is_day;
    Condition condition;
    float wind_mph;
    float wind_kph;
    int wind_degree;
    String wind_dir;
    float pressure_mb;
    float pressure_in;
    float precip_mm;
    float precip_in;
    int humidity;
    int cloud;
    float feelslike_c;
    float feelslike_f;
    float vis_km;
    float vis_miles;
    float uv;
    float gust_mph;
    float gust_kph;
    AirQuality air_quality;

    public AirQuality getAir_quality() {
        return air_quality;
    }

    public int getLast_updated_epoch() {
        return last_updated_epoch;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public float getTemp_c() {
        return temp_c;
    }

    public float getTemp_f() {
        return temp_f;
    }

    public int getIs_day() {
        return is_day;
    }

    public Condition getCondition() {
        return condition;
    }

    public float getWind_mph() {
        return wind_mph;
    }

    public float getWind_kph() {
        return wind_kph;
    }

    public int getWind_degree() {
        return wind_degree;
    }

    public String getWind_dir() {
        return wind_dir;
    }

    public float getPressure_mb() {
        return pressure_mb;
    }

    public float getPressure_in() {
        return pressure_in;
    }

    public float getPrecip_mm() {
        return precip_mm;
    }

    public float getPrecip_in() {
        return precip_in;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getCloud() {
        return cloud;
    }

    public float getFeelslike_c() {
        return feelslike_c;
    }

    public float getFeelslike_f() {
        return feelslike_f;
    }

    public float getVis_km() {
        return vis_km;
    }

    public float getVis_miles() {
        return vis_miles;
    }

    public float getUv() {
        return uv;
    }

    public float getGust_mph() {
        return gust_mph;
    }

    public float getGust_kph() {
        return gust_kph;
    }
}
