package com.example.weatherforecastmvvm;

import com.example.weatherforecastmvvm.GetAPIForeCast.Current;
import com.example.weatherforecastmvvm.GetAPIForeCast.Forecast;
import com.example.weatherforecastmvvm.GetAPIForeCast.ReturnedAlert;
import com.example.weatherforecastmvvm.getapilocation.Location;

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
