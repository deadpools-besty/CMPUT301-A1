package com.example.android.cardiobook;

public class DiastolicBP extends BloodPressure {

    public boolean isUnusual() {

        return value > 90 || value < 60;
    }


}
