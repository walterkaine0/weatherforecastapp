package com.example.weatherforecastapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class weatherDetail {
    @SerializedName("dt")
    private long dt;
    @SerializedName("main")
    private Main main;
    @SerializedName("weather")
    private List<Weather> weather;
    @SerializedName("dt_txt")
    private String dtTxt;
    public long getDt() {
        return dt;
    }
    public void setDt(long dt) {
        this.dt = dt;
    }
    public Main getMain() {
        return main;
    }
    public void setMain(Main main) {
        this.main = main;
    }
    public List<Weather> getWeather() {
        return weather;
    }
    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }
    public String getDtTxt() {
        return dtTxt;
    }
    public void setDtTxt(String dtTxt) {
        this.dtTxt = dtTxt;
    }
}
