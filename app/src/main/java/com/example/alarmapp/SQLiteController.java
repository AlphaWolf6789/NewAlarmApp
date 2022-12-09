package com.example.alarmapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.alarmapp.Model.Time;

import java.util.ArrayList;

public class SQLiteController extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TimeDB";
    private static final String TABLE_NAME = "Time";
    private static final String ID = "id";
    private static final String cityName = "cityName";
    private static final String cityCountry = "cityCountry";
    private static final String timeZone = "timeZone";
    private static final String times = "time";

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME +" ("
            + ID + " INTEGER PRIMARY KEY, "
            + cityName + " TEXT, "
            + cityCountry + " TEXT, "
            + timeZone + " TEXT, "
            + times + " TEXT)";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public SQLiteController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public ArrayList<Time> getAllTime(){
        ArrayList<Time> times = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String cityName = cursor.getString(1);
            String cityCountry = cursor.getString(2);
            String timeZone = cursor.getString(3);
            Time time = new Time(id, cityName, cityCountry,timeZone);
            times.add(time);
        }
        db.close();

        return times;
    }

    public boolean insert(Time time){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(ID, time.getCityID());
        contentValues.put(cityName, time.getCityName());
        contentValues.put(cityCountry, time.getCountry());
        contentValues.put(timeZone, time.getTimeZone());

        long checkInserted = db.insertOrThrow(TABLE_NAME, null,contentValues);

        return checkInserted > 0 ? true : false;
    }

    public boolean delete(int id){
        SQLiteDatabase db = getWritableDatabase();

        long deleted = db.delete(TABLE_NAME, ID + "=" + id,null);
        db.close();

        return deleted > 0 ? true: false;
    }
}
