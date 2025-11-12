package com.example.weatherforecastapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class weatherBased {
    @SerializedName("list")
    private List<weatherDetail> list;
    @SerializedName("city")
    private City city;
    public List<weatherDetail> getList() {
        return list;
    }
    public void setList(List<weatherDetail> list) {
        this.list = list;
    }
    public City getCity() {
        return city;
    }
    public void setCity(City city) {
        this.city = city;
    }
}
