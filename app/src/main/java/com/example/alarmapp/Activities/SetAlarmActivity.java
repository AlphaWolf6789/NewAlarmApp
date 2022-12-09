package com.example.alarmapp.Activities;

import static android.media.AudioManager.STREAM_MUSIC;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.alarmapp.Data.AlarmDatabase;
import com.example.alarmapp.Fragment.AlarmFragment;
import com.example.alarmapp.Model.Alarm;
import com.example.alarmapp.R;

import java.util.Calendar;
import java.util.Locale;

public class SetAlarmActivity extends AppCompatActivity {

    int hour, minute, img, position;
    private TextView txt_setAlarm;
    private ImageView img_Alarm;
    private EditText edt_AlarmNote;
    private SeekBar alarm_Volume;
    private SwitchCompat is_Vibrate;
    private static final String TIME_FORMAT_24 = "HH:mm";
    private Button btn_Save, btn_Cancle;
    private ToggleButton btn_Mon, btn_Tue, btn_Wed, btn_Thu, btn_Fri, btn_Sat, btn_Sun;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Calendar calendar;
    private Alarm alarm;
    MediaPlayer mediaPlayer;
    AudioManager audioManager;
    boolean vibrate;
    int volume;
    String note;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        btn_Mon = findViewById(R.id.btn_Mon);
        btn_Tue = findViewById(R.id.btn_Tue);
        btn_Wed = findViewById(R.id.btn_Wed);
        btn_Thu = findViewById(R.id.btn_Thu);
        btn_Fri = findViewById(R.id.btn_Fri);
        btn_Sat = findViewById(R.id.btn_Sat);
        btn_Sun = findViewById(R.id.btn_Sun);
        btn_Save = findViewById(R.id.btn_Save);
        btn_Cancle = findViewById(R.id.btn_cancle);
        txt_setAlarm = findViewById(R.id.txt_setAlarm);
        edt_AlarmNote = findViewById(R.id.edt_alarmNote);
        alarm_Volume = findViewById(R.id.seekbar);
        is_Vibrate = findViewById(R.id.is_Vibrate);
        calendar = Calendar.getInstance();
        img_Alarm = findViewById(R.id.img_setAlarm);

        receiveData();
        txt_setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpTimePicker(view);
            }
        });

        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToMainActivity();
            }
        });

        btn_Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mediaPlayer = MediaPlayer.create(SetAlarmActivity.this, R.raw.defaultalarm);
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        alarm_Volume.setMax(audioManager.getStreamMaxVolume(STREAM_MUSIC));
        alarm_Volume.setProgress(audioManager.getStreamVolume(STREAM_MUSIC));
        alarm_Volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.start();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
            }
        });


    }

    private void receiveData() {
        position = getIntent().getIntExtra("position", -1);
        alarm = (Alarm) getIntent().getExtras().get("alarm_object");
        hour = alarm.getHour();
        minute = alarm.getMinute();
        txt_setAlarm.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
        img_Alarm.setImageResource(alarm.getAlarm_ImgID());
        btn_Mon.setChecked(alarm.isMonday());
        btn_Tue.setChecked(alarm.isTuesday());
        btn_Wed.setChecked(alarm.isWednesday());
        btn_Thu.setChecked(alarm.isThursday());
        btn_Fri.setChecked(alarm.isFriday());
        btn_Sat.setChecked(alarm.isSaturday());
        btn_Sun.setChecked(alarm.isSunday());
        is_Vibrate.setChecked(alarm.isVibrate());
        edt_AlarmNote.setText(alarm.getNote());
    }

    private void sendDataToMainActivity() {
        String time = txt_setAlarm.getText().toString();

        note = edt_AlarmNote.getText().toString();
        String ringTone = "";
        volume = alarm_Volume.getProgress();
        vibrate = is_Vibrate.isChecked();
//        Toast.makeText(SetAlarmActivity.this, "vibrate "+ vibrate, Toast.LENGTH_SHORT).show();
        boolean recurring;
        if (btn_Mon.isChecked() || btn_Tue.isChecked() || btn_Wed.isChecked() || btn_Thu.isChecked()
                || btn_Fri.isChecked() || btn_Sat.isChecked() || btn_Sun.isChecked()) {
            recurring = true;
        } else {
            recurring = false;
        }
//        Alarm alarm_result = new Alarm(alarm.getAlarmId(), getImageAlarm(), hour, minute, volume, true, recurring,
//                vibrate, btn_Mon.isChecked(), btn_Tue.isChecked(), btn_Wed.isChecked(), btn_Thu.isChecked()
//                , btn_Fri.isChecked(), btn_Sat.isChecked(), btn_Sun.isChecked(), note, ringTone, System.currentTimeMillis());
        Alarm alarm_result = new Alarm();
//        Toast.makeText(SetAlarmActivity.this, "recurring "+ recurring, Toast.LENGTH_SHORT).show();

        alarm.setAlarm_ImgID(getImageAlarm());
        alarm.setHour(hour);
        alarm.setMinute(minute);
        alarm.setVolume(volume);
        alarm.setStarted(true);
        alarm.setRecurring(recurring);
        alarm.setVibrate(vibrate);
        alarm.setMonday(btn_Mon.isChecked());
        alarm.setTuesday(btn_Tue.isChecked());
        alarm.setWednesday(btn_Wed.isChecked());
        alarm.setThursday(btn_Thu.isChecked());
        alarm.setFriday(btn_Fri.isChecked());
        alarm.setSaturday(btn_Sat.isChecked());
        alarm.setSunday(btn_Sun.isChecked());
        alarm.setNote(note);
        alarm.setRingtone(ringTone);
        alarm.setCreated(System.currentTimeMillis());

        AlarmDatabase.getInstance(this).alarmDao().update(alarm);

        alarm.schedule(SetAlarmActivity.this);
        Intent intentResult = new Intent(SetAlarmActivity.this, AlarmFragment.class);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("alarm_object_fr",alarm);
//        intent1.putExtra("position_update", position);
//        intent1.putExtras(bundle);
        setResult(AlarmFragment.RESULT_CODE, intentResult);
        finish();
    }

    private void popUpTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int Selectedhour, int Selectedminute) {
                hour = Selectedhour;
                minute = Selectedminute;
                img_Alarm.setImageResource(getImageAlarm());
                txt_setAlarm.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Change Time");
        timePickerDialog.show();
    }

    private int getImageAlarm() {
        if (hour >= 18 || (hour >= 0 && hour < 6)) {
            return R.drawable.img_night;
        } else {
            return R.drawable.img_day;
        }
    }

}