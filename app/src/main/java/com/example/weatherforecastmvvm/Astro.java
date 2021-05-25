package com.example.weatherforecastmvvm;

import java.io.Serializable;

public class Astro implements Serializable {
    String sunrise;
    String sunset;
    String moonrise;
    String moonset;
    String moon_phase;
    String moon_illumination;

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public String getMoonrise() {
        return moonrise;
    }

    public String getMoonset() {
        return moonset;
    }

    public String getMoon_phase() {
        return moon_phase;
    }

    public String getMoon_illumination() {
        return moon_illumination;
    }
}
