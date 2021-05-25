package com.example.weatherforecastmvvm;

import java.io.Serializable;

public class AirQuality implements Serializable {
    float co;
    float no2;
    float o3;
    float so2;
    float pm2_5;
    float pm10;

    public float getCo() {
        return co;
    }

    public float getNo2() {
        return no2;
    }

    public float getO3() {
        return o3;
    }

    public float getSo2() {
        return so2;
    }

    public float getPm2_5() {
        return pm2_5;
    }

    public float getPm10() {
        return pm10;
    }
}
