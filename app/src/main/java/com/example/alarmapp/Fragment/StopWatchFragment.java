package com.example.alarmapp.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.alarmapp.Data.StopWatchDatabase;
import com.example.alarmapp.Model.StopWatch;
import com.example.alarmapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

public class StopWatchFragment extends Fragment implements View.OnClickListener{

    private FloatingActionButton startPause;
    private FloatingActionButton restart;
    private AppCompatButton lap;
    private TextView stopwatchText;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;
    private ListView listView;
    int timeStack = 0;
    int timeLapse = 0;
    Timer timer;
    TimerTask timerTask;
    int time = 0;
    private boolean running;
    private boolean started = false;
    private StopWatch stopWatch;
    private List<StopWatch> stopWatchList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        restart = view.findViewById(R.id.btn_restart_stopwatch);
        startPause = view.findViewById(R.id.btn_start_pause_stopwatch);
        lap = view.findViewById(R.id.btn_lap_stopwatch);
        restart.setOnClickListener(this);
        lap.setOnClickListener(this);
        startPause.setOnClickListener(this);
        stopwatchText = view.findViewById(R.id.txt_stopwatch);
        listView = view.findViewById(R.id.list_view_stopwatch);
        timer = new Timer();
        stopWatchList = new ArrayList<>();
        stopWatch = new StopWatch("00 : 00 : 00");
        StopWatchDatabase.getInstance(getContext()).stopWatchDao().insert(stopWatch);
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                R.layout.custom_list_view, arrayList);
        if(StopWatchDatabase.getInstance(getContext()).stopWatchDao().getStopWatch() != null){
            loadData();
            String time = stopWatchList.get(0).getTimeStopWatch();
            stopwatchText.setText(time);
        }
        listView.setAdapter(adapter);
        return view;
    }

    private void loadData() {
        stopWatchList = StopWatchDatabase.getInstance(getContext()).stopWatchDao().getStopWatch();
    }


    private void startStopwatch() {
        started = true;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if(getActivity() != null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            loadData();
                            time++;
                            stopwatchText.setText(getTimerText(time));



                        }
                    });
                }


            }
        };
        timer.scheduleAtFixedRate(timerTask, 100, 10);
    }

    private String getTimerText(int time) {
        int rounded;
        rounded = (int) Math.round(time);
        int millisecond = ((rounded % 360000) % 6000) % 100;
        int second = ((rounded % 36000) % 6000) / 100;
        int minute = ((rounded % 36000) / 6000);

        stopWatch.setTimeStopWatch(formatTime(millisecond, second, minute));
        StopWatchDatabase.getInstance(getContext()).stopWatchDao().insert(stopWatch);
        return formatTime(millisecond, second, minute);
    }

    private String formatTime(int millisecond, int second, int minute) {
        return String.format("%02d", minute) + " : " + String.format("%02d", second) + " : "
                + String.format("%02d", millisecond);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_pause_stopwatch:
                if (running == false) {
                    lap.setAlpha(1.0f);
                    lap.setClickable(true);

                    restart.setAlpha(1.0f);
                    restart.setClickable(true);

                    running = true;
                    startPause.setImageResource(R.drawable.ic_pause);
                    startStopwatch();
                } else {
                    lap.setAlpha(.5f);
                    lap.setClickable(false);

                    running = false;
                    startPause.setImageResource(R.drawable.ic_play);
                    timerTask.cancel();
                }
                break;

            case R.id.btn_restart_stopwatch:
                if (started == true) {
                    lap.setAlpha(.5f);
                    lap.setClickable(false);
                    timerTask.cancel();
                    startPause.setImageResource(R.drawable.ic_play);
                    running = false;
                    time = 0;
                    timeLapse = 0;
                    timeStack = 0;
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            listView.removeAllViewsInLayout();
                            arrayList.clear();
                            adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                                    R.layout.custom_list_view, arrayList);
                            listView.setAdapter(adapter);
                            stopwatchText.setText("00 : 00 : 00");
                        }
                    }, 100);
                }
                break;

            case R.id.btn_lap_stopwatch:
                if (running == true) {
                    timeLapse = time - timeStack;
                    timeStack = timeStack + timeLapse;
                    arrayList.add(0, getTimerText(timeLapse));
                    adapter.notifyDataSetChanged();

                }
                break;

        }
    }
}