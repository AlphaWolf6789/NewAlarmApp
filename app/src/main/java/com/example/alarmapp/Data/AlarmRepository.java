package com.example.alarmapp.Data;

import com.example.alarmapp.Model.Alarm;

import java.util.List;

import androidx.lifecycle.LiveData;

public class AlarmRepository {
    private AlarmDao alarmDao;
    private LiveData<List<Alarm>> alarmsLiveData;

//    public AlarmRepository(Application application) {
//        AlarmDatabase db = AlarmDatabase.getDatabase(application);
//        alarmDao = db.alarmDao();
//        alarmsLiveData = alarmDao.getAlarms();
//    }
//
//    public void insert(Alarm alarm) {
//        AlarmDatabase.databaseWriteExecutor.execute(() -> {
//            alarmDao.insert(alarm);
//        });
//    }
//
//    public void update(Alarm alarm) {
//        AlarmDatabase.databaseWriteExecutor.execute(() -> {
//            alarmDao.update(alarm);
//        });
//    }
//
//    public LiveData<List<Alarm>> getAlarmsLiveData() {
//        return alarmsLiveData;
//    }
}
