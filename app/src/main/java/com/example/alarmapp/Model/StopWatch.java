package com.example.alarmapp.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "stopwatch")
public class StopWatch implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int idStopWatch;
    private String timeStopWatch;

    public StopWatch() {
    }

    public StopWatch( String timeStopWatch) {
        this.timeStopWatch = timeStopWatch;
    }

    public int getIdStopWatch() {
        return idStopWatch;
    }

    public void setIdStopWatch(int idStopWatch) {
        this.idStopWatch = idStopWatch;
    }

    public String getTimeStopWatch() {
        return timeStopWatch;
    }

    public void setTimeStopWatch(String timeStopWatch) {
        this.timeStopWatch = timeStopWatch;
    }
}
