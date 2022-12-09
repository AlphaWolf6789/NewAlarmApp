package com.example.alarmapp.Model;

import static com.example.alarmapp.AlarmReceiver.FRIDAY;
import static com.example.alarmapp.AlarmReceiver.HOUR;
import static com.example.alarmapp.AlarmReceiver.MINUTE;
import static com.example.alarmapp.AlarmReceiver.MONDAY;
import static com.example.alarmapp.AlarmReceiver.NOTE;
import static com.example.alarmapp.AlarmReceiver.RECURRING;
import static com.example.alarmapp.AlarmReceiver.RINGTONE;
import static com.example.alarmapp.AlarmReceiver.SATURDAY;
import static com.example.alarmapp.AlarmReceiver.SUNDAY;
import static com.example.alarmapp.AlarmReceiver.THURSDAY;
import static com.example.alarmapp.AlarmReceiver.TUESDAY;
import static com.example.alarmapp.AlarmReceiver.VIBRATE;
import static com.example.alarmapp.AlarmReceiver.VOLUME;
import static com.example.alarmapp.AlarmReceiver.WEDNESDAY;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.alarmapp.AlarmReceiver;

import java.io.Serializable;
import java.util.Calendar;

@Entity(tableName = "alarmDB")
public class Alarm implements Serializable {
    @PrimaryKey
    @NonNull
    private int alarmId;
    private int Alarm_ImgID;
    private int hour, minute;
    private int volume;
    private boolean started, recurring, vibrate;
    private boolean monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    private String note;
    private String ringtone;
    private long created;



    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public boolean isVibrate() {
        return vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public String getRingtone() {
        return ringtone;
    }

    public void setRingtone(String ringtone) {
        this.ringtone = ringtone;
    }

    public int getAlarm_ImgID() {
        return Alarm_ImgID;
    }

    public void setAlarm_ImgID(int alarm_ImgID) {
        Alarm_ImgID = alarm_ImgID;
    }

    public Alarm() {
    }

    public Alarm(int alarmId, int alarm_ImgID, int hour, int minute, int volume, boolean started,
                 boolean recurring, boolean vibrate, boolean monday, boolean tuesday,
                 boolean wednesday, boolean thursday, boolean friday, boolean saturday,
                 boolean sunday, String note, String ringtone, long created) {
        this.alarmId = alarmId;
        Alarm_ImgID = alarm_ImgID;
        this.hour = hour;
        this.minute = minute;
        this.volume = volume;
        this.started = started;
        this.recurring = recurring;
        this.vibrate = vibrate;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.note = note;
        this.ringtone = ringtone;
        this.created = created;
    }

    public void schedule(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(RECURRING, recurring);
        intent.putExtra(MONDAY, monday);
        intent.putExtra(TUESDAY, tuesday);
        intent.putExtra(WEDNESDAY, wednesday);
        intent.putExtra(THURSDAY, thursday);
        intent.putExtra(FRIDAY, friday);
        intent.putExtra(SATURDAY, saturday);
        intent.putExtra(SUNDAY, sunday);
        intent.putExtra(VIBRATE, vibrate);
        intent.putExtra(VOLUME, volume);

        intent.putExtra(NOTE, note);
        intent.putExtra(RINGTONE, ringtone);
        intent.putExtra(HOUR, hour);
        intent.putExtra(MINUTE,minute);


        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, PendingIntent.FLAG_MUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // if alarm time has already passed, increment day by 1
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }

        if (!recurring) {
            String toastText = null;
            try {
                toastText = String.format("One Time Alarm %s scheduled for %s at %02d:%02d", note, DayUtil.toDay(calendar.get(Calendar.DAY_OF_WEEK)), hour, minute);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();

            alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    alarmPendingIntent
            );
        } else {
            String toastText = String.format("Recurring Alarm %s scheduled for %s at %02d:%02d", note, getRecurringDaysText(), hour, minute);
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();

            final long RUN_DAILY = 24 * 60 * 60 * 1000;
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    RUN_DAILY,
                    alarmPendingIntent
            );
        }

        this.started = true;
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);
        alarmManager.cancel(alarmPendingIntent);
        this.started = false;

        String toastText = String.format("Alarm cancelled for %02d:%02d ", hour, minute);
        Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
        Log.i("cancel", toastText);
    }

    public String getRecurringDaysText() {
        if (!recurring) {
            return null;
        }

        String days = "";
        if (monday) {
            days += "Mon ";
        }
        if (tuesday) {
            days += "Tue ";
        }
        if (wednesday) {
            days += "Wed ";
        }
        if (thursday) {
            days += "Thus ";
        }
        if (friday) {
            days += "Fri ";
        }
        if (saturday) {
            days += "Sat ";
        }
        if (sunday) {
            days += "Sun ";
        }

        return days;
    }
    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }

    public boolean isMonday() {
        return monday;
    }

    public void setMonday(boolean monday) {
        this.monday = monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public void setTuesday(boolean tuesday) {
        this.tuesday = tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public void setWednesday(boolean wednesday) {
        this.wednesday = wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public void setThursday(boolean thursday) {
        this.thursday = thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public void setFriday(boolean friday) {
        this.friday = friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public void setSaturday(boolean saturday) {
        this.saturday = saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setSunday(boolean sunday) {
        this.sunday = sunday;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
