package com.example.alarmapp.Model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class Time extends City {
    TimeZone timeZone = TimeZone.getTimeZone("UTC");
    Calendar calendar = Calendar.getInstance(timeZone);


    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Time(int cityID, String cityName, String country, String timeZone) {
        super(cityID, cityName, country, timeZone);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.US);
        TimeZone timexZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timexZone);
        int hour;
        if (timeZone.length() == 8) {
            hour = timeZone.charAt(4) - '0';
        } else hour = (timeZone.charAt(4) - '0') * 10 + timeZone.charAt(5) - '0';
        simpleDateFormat.setTimeZone(timexZone);
        String timeString = simpleDateFormat.format(calendar.getTime());
        if (timeZone.charAt(3) == '+') {
            hour += (timeString.charAt(0) - '0') * 10 + timeString.charAt(1) - '0';
        } else {
            hour -= (timeString.charAt(0) - '0') * 10 + timeString.charAt(1) - '0';
            if (hour < 0) {
                hour = -hour;
            }
        }
        if (hour < 10) {
            timeString = "0" + hour + ":" + timeString.substring(3);
        } else timeString = hour + ":" + timeString.substring(3);

        this.time = timeString;
    }
}
