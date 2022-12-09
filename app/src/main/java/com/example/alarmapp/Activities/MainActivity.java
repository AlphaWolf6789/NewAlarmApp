package com.example.alarmapp.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.alarmapp.Adapter.ViewPagerAdapter;
import com.example.alarmapp.Fragment.AlarmFragment;
import com.example.alarmapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;
    private ViewPager viewPager;
    Intent intent;
    AlarmFragment alarmFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.view_pager);
        setUpViewPager();
        navigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.action_alarm:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.action_global_clock:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.action_timer:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.action_stopwatch:
                    viewPager.setCurrentItem(3);
                    break;
                case R.id.action_bed_clock:
                    viewPager.setCurrentItem(4);
                    break;
            }
            return true;
        });

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == 1000){
//            if(resultCode == RESULT_OK){
//                if (data.getExtras() != null) {
//                    Alarm alarm = (Alarm) data.getExtras().get("alarm_object_main");
//                    Toast.makeText(MainActivity.this, "Main clicked" + alarm.toString(), Toast.LENGTH_LONG).show();
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("alarm_object_fr", alarm);
//                    alarmFragment = new AlarmFragment();
//                    alarmFragment.setArguments(bundle);
//                    getSupportFragmentManager().beginTransaction().add(android.R.id.content, alarmFragment).addToBackStack(null).commit();
////                    getSupportFragmentManager().beginTransaction().show(alarmFragment).addToBackStack(null).commit();
//
//                }
//            }
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setUpViewPager(){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        navigationView.getMenu().findItem(R.id.action_alarm).setChecked(true);
                        break;
                    case 1:
                        navigationView.getMenu().findItem(R.id.action_global_clock).setChecked(true);
                        break;
                    case 2:
                        navigationView.getMenu().findItem(R.id.action_timer).setChecked(true);
                        break;
                    case 3:
                        navigationView.getMenu().findItem(R.id.action_stopwatch).setChecked(true);
                        break;
                    case 4:
                        navigationView.getMenu().findItem(R.id.action_bed_clock).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}