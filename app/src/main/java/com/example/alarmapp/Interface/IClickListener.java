package com.example.alarmapp.Interface;

import android.view.View;

import com.example.alarmapp.Model.Alarm;

public interface IClickListener {
    void onClickItem(View view, int position);
    void deleteAlarm(Alarm alarm);
}
