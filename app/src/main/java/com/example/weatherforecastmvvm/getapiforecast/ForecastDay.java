package com.example.weatherforecastmvvm.getapiforecast;

import java.io.Serializable;
import java.util.List;

public class ForecastDay implements Serializable {
    String date;
    long date_epoch;
    Day day;
    Astro astro;
    List<Hour> hour;

    public void setDate(String date) {
        this.date = date;
    }

    public void setDate_epoch(long date_epoch) {
        this.date_epoch = date_epoch;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public void setAstro(Astro astro) {
        this.astro = astro;
    }

    public void setHour(List<Hour> hour) {
        this.hour = hour;
    }

    public String getDate() {
        return date;
    }

    public long getDate_epoch() {
        return date_epoch;
    }

    public Day getDay() {
        return day;
    }

    public Astro getAstro() {
        return astro;
    }

    public List<Hour> getHour() {
        return hour;
    }
}
