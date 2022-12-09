package com.example.alarmapp.Fragment;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarmapp.Activities.SetAlarmActivity;
import com.example.alarmapp.Adapter.AlarmAdapter;
import com.example.alarmapp.Data.AlarmDatabase;
import com.example.alarmapp.Interface.IClickListener;
import com.example.alarmapp.Model.Alarm;
import com.example.alarmapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class AlarmFragment extends Fragment implements IClickListener {

    private int hour, minute, img, newSetHour, newSetMinute;
    private FloatingActionButton btn_add;
    private Button btn_delete;
    private SwitchCompat onOffAlarm;
    private RecyclerView recyclerView;
    private AlarmAdapter adapter;
    private TextView txt_setAlarm;
    private ImageView img_Alarm;
    private boolean isSetUp = false;
    List<Alarm> list;
    private Calendar calendar;
    public static final int REQUEST_CODE = 11;
    public static final int RESULT_CODE = 12;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);
        btn_add = view.findViewById(R.id.btn_addAlarm);
        recyclerView = view.findViewById(R.id.rec_Alarm_Fr);
        btn_delete = view.findViewById(R.id.btn_DeleteAlarm);
        onOffAlarm= view.findViewById(R.id.switchBtn_Fragment);
        txt_setAlarm = view.findViewById(R.id.txt_TimeAlarm_Fragment);
        img_Alarm = view.findViewById(R.id.img_setAlarm);
        list = new ArrayList<>();
        adapter = new AlarmAdapter(list, R.layout.item_alarm, getActivity());
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        if (AlarmDatabase.getInstance(getContext()).alarmDao().getListAlarms() != null) {
            loadData();
        }
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpTimePicker(view);
            }
        });
        return view;

    }

    private void loadData() {
        list = AlarmDatabase.getInstance(getContext()).alarmDao().getListAlarms();
        adapter.setData(getListAlarm());
    }

    private void popUpTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int Selectedhour, int Selectedminute) {
                int alarmId = new Random().nextInt(Integer.MAX_VALUE);

                hour = Selectedhour;
                minute = Selectedminute;

                Alarm alarm = new Alarm(alarmId, setImageAlarm(hour), hour, minute, 80, true, false,
                        true, false, false, false, false,
                        false, false, false, "", "", System.currentTimeMillis());

                alarm.schedule(getContext());

                AlarmDatabase.getInstance(getContext()).alarmDao().insert(alarm);
                Toast.makeText(getContext(), "Add alarm successfully!", Toast.LENGTH_SHORT).show();
//                onOffAlarm.setChecked(alarm.isStarted());
                list = AlarmDatabase.getInstance(getContext()).alarmDao().getListAlarms();
                try {
                    adapter.setData(getListAlarm());
                } catch (NullPointerException e) {

                }
            }
        };
        calendar = Calendar.getInstance();
        newSetHour = calendar.get(Calendar.HOUR_OF_DAY);
        newSetMinute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), onTimeSetListener, newSetHour, newSetMinute, true);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }


    private int setImageAlarm(int hour) {
        if (hour >= 18 || (hour >= 0 && hour < 6)) {
            return R.drawable.img_night;
        } else {
            return R.drawable.img_day;
        }
    }

    private List<Alarm> getListAlarm() {
        return list;
    }

    @Override
    public void onClickItem(View view, int position) {
        final Alarm alarm = list.get(position);
        Intent intent = new Intent(getActivity(), SetAlarmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("alarm_object", alarm);
        intent.putExtra("position", position);
        intent.putExtras(bundle);
//        Toast.makeText(getActivity(), "object clicked " + alarm.getAlarm_ImgID(), Toast.LENGTH_LONG).show();
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void deleteAlarm(Alarm alarm) {
        AlarmDatabase.getInstance(getContext()).alarmDao().deleteAlarm(alarm);
        Toast.makeText(getContext(), "Delete successfully!!!", Toast.LENGTH_SHORT).show();
        loadData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE) {
            loadData();
        }
    }

}