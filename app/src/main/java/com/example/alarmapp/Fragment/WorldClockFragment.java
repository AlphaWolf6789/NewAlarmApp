package com.example.alarmapp.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alarmapp.Activities.SetTimeZoneActivity;
import com.example.alarmapp.Adapter.TimeZoneListViewApdapter;
import com.example.alarmapp.Model.City;
import com.example.alarmapp.Model.Time;
import com.example.alarmapp.R;
import com.example.alarmapp.SQLiteController;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class WorldClockFragment extends Fragment {
    private TimeZoneListViewApdapter timeZoneListViewApdapter;
    private FloatingActionButton mbtnAddTimeZone;
    private TextView tvTime;
    private TextView tvDate;
    private ListView listViewTimeZone;
    private BroadcastReceiver mTimeTickReceiver;
    private ArrayList<Time> timeZones;
    public final int REQUEST_CODE = 1001, RESULT_CODE = 1000;
    SQLiteController sqLiteController;

    // Them class City, Time vao Model, Them CityListViewAdapter, TimeZoneListViewAdapter
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_world_clock, container, false);
        sqLiteController = new SQLiteController(getActivity());
        timeZones = sqLiteController.getAllTime();

        tvDate = view.findViewById(R.id.txt_current_date);
        tvTime = view.findViewById(R.id.txt_world_time);
        listViewTimeZone = view.findViewById(R.id.lv_timeZone);

        timeZoneListViewApdapter = new TimeZoneListViewApdapter(timeZones, sqLiteController);

        listViewTimeZone.setAdapter(timeZoneListViewApdapter);
        initClock();

        mbtnAddTimeZone = (FloatingActionButton) view.findViewById(R.id.btn_addWorldTime);
        mbtnAddTimeZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SetTimeZoneActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initClock();
        mTimeTickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                initClock();
                for (int i = 0; i< timeZones.size(); i++){
                    initTimeZone(timeZones.get(i));
                }
                timeZoneListViewApdapter = new TimeZoneListViewApdapter(timeZones, sqLiteController);
                listViewTimeZone.setAdapter(timeZoneListViewApdapter);
            }
        };
        getActivity().registerReceiver(mTimeTickReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
    }

    private void initTimeZone(Time timeZones) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm",Locale.US);

        TimeZone timexZone = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(timexZone);
        int hour;
        String timeZone = timeZones.getTimeZone();
        if (timeZone.length() == 8) {
            hour = timeZone.charAt(4) - '0';
        }
        else hour = (timeZone.charAt(4) - '0') * 10 + timeZone.charAt(5) - '0';
        simpleDateFormat.setTimeZone(timexZone);
        String timeString = simpleDateFormat.format(calendar.getTime());
        if (timeZone.charAt(3) == '+'){
            hour += (timeString.charAt(0) - '0') * 10 + timeString.charAt(1) - '0';
        }else {
            hour -= (timeString.charAt(0) - '0') * 10 + timeString.charAt(1) - '0';
            if (hour < 0) {
                hour = - hour;
            }
        }
        if (hour < 10){
            timeString ="0" + hour + ":" + timeString.substring(3);
        }
        else    timeString = hour + ":" + timeString.substring(3);
        timeZones.setTime(timeString);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_CODE){
            if(data.getExtras() != null){
                City city = (City) data.getExtras().get("city_object_fr");
                boolean check = false;
                for (int i = 0; i< timeZones.size(); i++){
                    if (city.getCityID() == timeZones.get(i).getCityID()){
                        check = true;
                    }
                }
                if (!check) {
                    Time time = new Time(city.getCityID(), city.getCityName(), city.getCountry(),city.getTimeZone());
                    boolean isAdded = sqLiteController.insert(time);
                    timeZones = sqLiteController.getAllTime();
                    timeZoneListViewApdapter = new TimeZoneListViewApdapter(timeZones, sqLiteController);
                    listViewTimeZone.setAdapter(timeZoneListViewApdapter);
                }
                else {
                    Toast.makeText(getActivity(), "World Time is Existed", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mTimeTickReceiver);
    }

    private void initClock() {
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        String getDate = currentDate.format(Calendar.getInstance().getTime());
        tvDate.setText("Current: " + getDate);
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        String getTime = currentTime.format(Calendar.getInstance().getTime());
        tvTime.setText(getTime);
    }
}