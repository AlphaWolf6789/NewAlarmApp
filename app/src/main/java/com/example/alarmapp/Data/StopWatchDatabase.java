package com.example.alarmapp.Data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.alarmapp.Model.StopWatch;

@Database(entities = {StopWatch.class}, version = 1, exportSchema = false)
public abstract class StopWatchDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "stopwatch.db";
    private  static  StopWatchDatabase instance;

    public static synchronized StopWatchDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), StopWatchDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract StopWatchDao stopWatchDao();
}
