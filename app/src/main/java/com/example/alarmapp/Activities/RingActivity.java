package com.example.alarmapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alarmapp.Model.Alarm;
import com.example.alarmapp.R;
import com.example.alarmapp.Service.AlarmService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import butterknife.ButterKnife;


public class RingActivity extends AppCompatActivity {
    private Button btn_dismiss;
    private Button btn_snooze;
    private ImageView clock;
    private TextView timeAlarm;
    private String time;
    private TextView note;
    private Calendar calendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_new);
        btn_dismiss = findViewById(R.id.btn_dismiss);
        btn_snooze = findViewById(R.id.btn_snooze);
        timeAlarm = findViewById(R.id.time_Alert);
        note = findViewById(R.id.txtNote);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        time = dateFormat.format(calendar.getInstance().getTime());
        timeAlarm.setText(time);

        ButterKnife.bind(this);


        btn_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
                getApplicationContext().stopService(intentService);
                finish();
            }
        });

        btn_snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.MINUTE, 2);

                Alarm alarm_snooze = new Alarm(new Random().nextInt(Integer.MAX_VALUE), -1, calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE), 10, true, false,true,
                        false, false, false, false, false, false, false,
                        "Snooze", "", System.currentTimeMillis());


                alarm_snooze.schedule(getApplicationContext());

                Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
                getApplicationContext().stopService(intentService);
                finish();
            }
        });

//        animateClock();
    }

//    private void animateClock() {
//        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(clock, "rotation", 0f, 20f, 0f, -20f, 0f);
//        rotateAnimation.setRepeatCount(ValueAnimator.INFINITE);
//        rotateAnimation.setDuration(800);
//        rotateAnimation.start();
//    }
}