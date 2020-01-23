package com.example.android.cardiobook;

import androidx.annotation.Nullable;

import java.util.Date;

public class Measurement {

    private Date date;
    private Date time;
    private BloodPressure systolicBP = new SystolicBP();
    private BloodPressure diastolicBP = new DiastolicBP();
    private int heartRate;
    private String comment;


    Measurement(int systolicBP, int diastolicBP, int heartRate, @Nullable String comment) {
        this.date = new Date();
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

    public Date getDate() {
        return date;
    }
    void setDate(Date date) {
        this.date = date;
    }

    void setSystolicBP(int BP) {
        systolicBP.setBP(BP);
    }
    public int getSystolicBP() {
        return systolicBP.getBP();
    }
    void setDiastolicBPBP(int BP) {
        diastolicBP.setBP(BP);
    }
    public int getDiastolicBP() {
        return diastolicBP.getBP();
    }



}
