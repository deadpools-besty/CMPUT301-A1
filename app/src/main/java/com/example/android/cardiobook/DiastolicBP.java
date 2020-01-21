package com.example.android.cardiobook;

public class DiastolicBP extends BloodPressure {

    public boolean isUnusual() {

        if (value > 90 || value < 60) {
            return true;
        }
        else return false;
    }


}
