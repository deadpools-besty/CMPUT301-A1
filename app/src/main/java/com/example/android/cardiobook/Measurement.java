package com.example.android.cardiobook;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Date;

public class Measurement implements Serializable {

    private String date;
    private String time;
    private BloodPressure systolicBP = new SystolicBP();
    private BloodPressure diastolicBP = new DiastolicBP();
    private int heartRate;
    private String comment;


    Measurement(String date, String time, int systolicBP, int diastolicBP, int heartRate) {
        this.date = date;
        this.time = time;
        this.systolicBP.setBP(systolicBP);
        this.diastolicBP.setBP(diastolicBP);
        this.heartRate = heartRate;
    }

    public Measurement(String date, String time, int systolicBP, int diastolicBP, int heartRate, String comment) {
        this.date = date;
        this.time = time;
        this.systolicBP.setBP(systolicBP);
        this.diastolicBP.setBP(diastolicBP);
        this.heartRate = heartRate;
        this.comment = comment;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSystolicBP(int BP) {
        systolicBP.setBP(BP);
    }
    public SystolicBP getSystolicBP() {
        return (SystolicBP) systolicBP;
    }
    public void setDiastolicBP(int BP) {
        diastolicBP.setBP(BP);
    }
    public DiastolicBP getDiastolicBP() {
        return (DiastolicBP) diastolicBP;
    }



}
