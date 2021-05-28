package com.example.weatherforecastmvvm.getapiforecast;

import java.io.Serializable;

public class Condition implements Serializable {
    String text;
    String icon;
    int code;

    public String getText() {
        return text;
    }

    public String getIcon() {
        return icon;
    }

    public int getCode() {
        return code;
    }


}
