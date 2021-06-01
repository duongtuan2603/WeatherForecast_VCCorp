package com.example.weatherforecastmvvm.data.local;

public class Settings {
    String temperatureunit;
    String windspeedunit;
    long updatefrequency;

    public Settings(String temperatureunit, String windspeedunit, long updatefrequency) {
        this.temperatureunit = temperatureunit;
        this.windspeedunit = windspeedunit;
        this.updatefrequency = updatefrequency;
    }

    public String getTemperatureunit() {
        return temperatureunit;
    }

    public String getWindspeedunit() {
        return windspeedunit;
    }

    public long getUpdatefrequency() {
        return updatefrequency;
    }
}
