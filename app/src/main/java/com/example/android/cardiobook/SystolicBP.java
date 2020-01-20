package com.example.android.cardiobook;

public class SystolicBP extends BloodPressure {

    public boolean isUnusual() {

        if (value > 140 || value < 90) {
            return true;
        }
        else return false;
    }
}
