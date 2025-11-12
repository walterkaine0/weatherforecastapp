package com.example.weatherforecastapp.Model;

import com.google.gson.annotations.SerializedName;
public class Main {
    @SerializedName("temp")
    private double temp;
    @SerializedName("temp_min")
    private double tempMin;
    @SerializedName("temp_max")
    private double tempMax;
    public double getTemp() {
        return temp;
    }
    public double getTempMin() {
        return tempMin;
    }
    public double getTempMax() {
        return tempMax;
    }
}