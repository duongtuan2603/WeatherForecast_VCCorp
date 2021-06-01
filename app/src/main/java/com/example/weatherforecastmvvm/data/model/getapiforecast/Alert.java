package com.example.weatherforecastmvvm.data.model.getapiforecast;

import java.io.Serializable;

public class Alert implements Serializable {
    String headline;
    String msgtype;
    String severity;
    String urgency;
    String areas;
    String category;
    String certainty;
    String event;
    String note;
    String effective;
    String expires;
    String desc;
    String instruction;

    public String getHeadline() {
        return headline;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public String getSeverity() {
        return severity;
    }

    public String getUrgency() {
        return urgency;
    }

    public String getAreas() {
        return areas;
    }

    public String getCategory() {
        return category;
    }

    public String getCertainty() {
        return certainty;
    }

    public String getEvent() {
        return event;
    }

    public String getNote() {
        return note;
    }

    public String getEffective() {
        return effective;
    }

    public String getExpires() {
        return expires;
    }

    public String getDesc() {
        return desc;
    }

    public String getInstruction() {
        return instruction;
    }
}
