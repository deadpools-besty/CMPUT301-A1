package com.example.android.cardiobook;

import java.util.Date;

public class Measurement {

    private Date date;
    private BloodPressure systolicBP = new SystolicBP();
    private BloodPressure diastolicBP = new DiastolicBP();

    public Measurement(Date date, BloodPressure systolicBP, BloodPressure diastolicBP, int heartRate, String comment) {
        this.date = date;
        this.systolicBP = systolicBP;
        this.diastolicBP = diastolicBP;
        this.heartRate = heartRate;
        this.comment = comment;
    }

    public Measurement(Date date, BloodPressure systolicBP, BloodPressure diastolicBP, int heartRate) {
        this.date = date;
        this.systolicBP = systolicBP;
        this.diastolicBP = diastolicBP;
        this.heartRate = heartRate;
    }

    private int heartRate;
    private String comment;

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
