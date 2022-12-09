package com.example.alarmapp.Data;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.alarmapp.Model.Alarm;

import java.util.List;

@Dao
public interface AlarmDao {
    @Insert
    void insert(Alarm alarm);

    @Delete
    void deleteAlarm(Alarm alarm);

    @Query("SELECT * FROM alarmDB ORDER BY created ASC")
    List<Alarm> getListAlarms();

    @Update
    void update(Alarm alarm);

}
