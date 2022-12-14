package com.example.alarmapp.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.alarmapp.Model.Alarm;

@Database(entities = {Alarm.class}, version = 3, exportSchema = false)
public abstract class AlarmDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "alarm_table";
    private  static  AlarmDatabase instance;

    public static synchronized AlarmDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AlarmDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract AlarmDao alarmDao();
}
