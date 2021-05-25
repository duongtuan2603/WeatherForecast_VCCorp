package com.example.weatherforecastmvvm;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class SavedLocation implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    int id;

    String locationname;
    String locationcountry;
    float currenttemp_c;
    float currenttemp_f;
    String currentweathericon;
    String currentweatherdescription;

    public SavedLocation(String locationname, String locationcountry, float currenttemp_c,float currenttemp_f, String currentweathericon, String currentweatherdescription) {
        this.locationname = locationname;
        this.locationcountry = locationcountry;
        this.currenttemp_c = currenttemp_c;
        this.currenttemp_f = currenttemp_f;
        this.currentweathericon = currentweathericon;
        this.currentweatherdescription = currentweatherdescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocationname() {
        return locationname;
    }

    public void setLocationname(String locationname) {
        this.locationname = locationname;
    }

    public String getLocationcountry() {
        return locationcountry;
    }

    public void setLocationcountry(String locationcountry) {
        this.locationcountry = locationcountry;
    }

    public float getCurrenttemp_c() {
        return currenttemp_c;
    }

    public void setCurrenttemp_c(float currenttemp_c) {
        this.currenttemp_c = currenttemp_c;
    }

    public float getCurrenttemp_f() {
        return currenttemp_f;
    }

    public void setCurrenttemp_f(float currenttemp_f) {
        this.currenttemp_f = currenttemp_f;
    }

    public String getCurrentweathericon() {
        return currentweathericon;
    }

    public void setCurrentweathericon(String currentweathericon) {
        this.currentweathericon = currentweathericon;
    }

    public String getCurrentweatherdescription() {
        return currentweatherdescription;
    }

    public void setCurrentweatherdescription(String currentweatherdescription) {
        this.currentweatherdescription = currentweatherdescription;
    }
}
