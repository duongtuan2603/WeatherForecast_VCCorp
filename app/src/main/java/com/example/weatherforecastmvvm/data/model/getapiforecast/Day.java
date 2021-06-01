package com.example.weatherforecastmvvm.data.model.getapiforecast;

import java.io.Serializable;

public class Day implements Serializable {
    double maxtemp_c;
    double maxtemp_f;
    double mintemp_c;
    double mintemp_f;
    double avgtemp_c;
    double avgtemp_f;
    double maxwind_mph;
    double maxwind_kph;
    double totalprecip_mm;
    double totalprecip_in;
    double avgvis_km;
    double avgvis_miles;
    double avghumidity;
    int daily_will_it_rain;
    String daily_chance_of_rain;
    int daily_will_it_snow;
    String daily_chance_of_snow;
    Condition condition;
    double uv;

    public double getMaxtemp_c() {
        return maxtemp_c;
    }

    public double getMaxtemp_f() {
        return maxtemp_f;
    }

    public double getMintemp_c() {
        return mintemp_c;
    }

    public double getMintemp_f() {
        return mintemp_f;
    }

    public double getAvgtemp_c() {
        return avgtemp_c;
    }

    public double getAvgtemp_f() {
        return avgtemp_f;
    }

    public double getMaxwind_mph() {
        return maxwind_mph;
    }

    public double getMaxwind_kph() {
        return maxwind_kph;
    }

    public double getTotalprecip_mm() {
        return totalprecip_mm;
    }

    public double getTotalprecip_in() {
        return totalprecip_in;
    }

    public double getAvgvis_km() {
        return avgvis_km;
    }

    public double getAvgvis_miles() {
        return avgvis_miles;
    }

    public double getAvghumidity() {
        return avghumidity;
    }

    public int getDaily_will_it_rain() {
        return daily_will_it_rain;
    }

    public String getDaily_chance_of_rain() {
        return daily_chance_of_rain;
    }

    public int getDaily_will_it_snow() {
        return daily_will_it_snow;
    }

    public String getDaily_chance_of_snow() {
        return daily_chance_of_snow;
    }

    public Condition getCondition() {
        return condition;
    }

    public double getUv() {
        return uv;
    }
}
