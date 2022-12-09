package com.example.alarmapp.Adapter;

import com.example.alarmapp.Fragment.AlarmFragment;
import com.example.alarmapp.Fragment.BedClockFragment;
import com.example.alarmapp.Fragment.StopWatchFragment;
import com.example.alarmapp.Fragment.TimerFragment;
import com.example.alarmapp.Fragment.WorldClockFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new AlarmFragment();
            case 1:
                return new WorldClockFragment();
            case 2:
                return new TimerFragment();
            case 3:
                return new StopWatchFragment();
            case 4:
                return new BedClockFragment();
            default:
                return new AlarmFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
