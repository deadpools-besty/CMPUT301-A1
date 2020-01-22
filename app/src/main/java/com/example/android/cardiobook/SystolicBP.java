package com.example.android.cardiobook;

public class SystolicBP extends BloodPressure {

    public boolean isUnusual() {

        return value > 140 || value < 90;
    }
}
