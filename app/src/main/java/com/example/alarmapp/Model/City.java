package com.example.alarmapp.Model;

import java.io.Serializable;

public class City implements Serializable {
    private int cityID;
    private String cityName;
    private String country;
    String timeZone;

    public City(int cityID, String cityName, String country, String timeZone) {
        this.cityID = cityID;
        this.cityName = cityName;
        this.country = country;
        this.timeZone = timeZone;
    }

    public City() {
    }

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String toLowerCase() {
        return cityName.toLowerCase();
    }
}
