package com.example.weatherforecastmvvm;

import java.io.Serializable;

public class Location implements Serializable {
    String name;
    String region;
    String country;
    float lat;
    float lon;
    String tz_id;
    long localtime_epoch;
    String localtime;

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    public String getTz_id() {
        return tz_id;
    }

    public long getLocaltime_epoch() {
        return localtime_epoch;
    }

    public String getLocaltime() {
        return localtime;
    }
}
