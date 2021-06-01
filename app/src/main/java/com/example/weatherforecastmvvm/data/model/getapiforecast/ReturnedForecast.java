package com.example.weatherforecastmvvm.data.model.getapiforecast;

import com.example.weatherforecastmvvm.data.local.Location;

import java.io.Serializable;
public class ReturnedForecast implements Serializable {
    Location location;
    Current current;
    Forecast forecast;
    ReturnedAlert alerts;

    public ReturnedAlert getAlerts() {
        return alerts;
    }

    public Location getLocation() {
        return location;
    }

    public Current getCurrent() {
        return current;
    }

    public Forecast getForecast() {
        return forecast;
    }

    @Override
    public String toString() {
        return "ReturnedForecast{" +
                "Location=" + location +
                ", current=" + current +
                ", forecast=" + forecast +
                ", alerts=" + alerts +
                '}';
    }
}
