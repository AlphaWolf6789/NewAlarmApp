package com.example.alarmapp.Data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.alarmapp.Model.StopWatch;

import java.util.List;

@Dao
public interface StopWatchDao {
    @Insert
    void insert(StopWatch stopWatch);

    @Query("SELECT * FROM stopwatch")
    List<StopWatch> getStopWatch();

    @Update
    void update(StopWatch stopWatch);
}
