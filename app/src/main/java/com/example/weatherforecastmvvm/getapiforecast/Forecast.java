package com.example.weatherforecastmvvm.getapiforecast;

import java.io.Serializable;
import java.util.List;

public class Forecast implements Serializable {
    List<ForecastDay> forecastday;

    public List<ForecastDay> getForecastday() {
        return forecastday;
    }
}
