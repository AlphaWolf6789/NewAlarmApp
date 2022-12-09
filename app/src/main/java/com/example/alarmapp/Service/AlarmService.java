package com.example.alarmapp.Service;

import static com.example.alarmapp.AlarmReceiver.NOTE;
import static com.example.alarmapp.AlarmReceiver.VIBRATE;
import static com.example.alarmapp.AlarmReceiver.VOLUME;
import static com.example.alarmapp.App.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.alarmapp.Activities.RingActivity;
import com.example.alarmapp.R;

public class AlarmService extends Service {
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;
    private int volume;

    @Override
    public void onCreate() {
        super.onCreate();

        mediaPlayer = MediaPlayer.create(this, R.raw.defaultalarm);
        mediaPlayer.setLooping(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Intent notificationIntent = new Intent(this, RingActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        String alarmTitle = "Alarm ";
        if (intent.getStringExtra(NOTE) != null) {
            alarmTitle = alarmTitle + intent.getStringExtra(NOTE);
        }

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(alarmTitle)
                .setContentText("Ring Ring ... Ring")
                .setSmallIcon(R.drawable.ic_alarm)
                .setContentIntent(pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .build();
        volume = intent.getIntExtra(VOLUME, 10);
        mediaPlayer.setVolume(volume,volume);
        mediaPlayer.start();
        boolean isVibrate = intent.getBooleanExtra(VIBRATE, false);

        Log.i("QQQ", "volume: " + volume);
        if (isVibrate) {
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            long[] pattern = {0, 2000, 500};
            vibrator.vibrate(pattern, 0);

        }
        startForeground(1, notification);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        vibrator.cancel();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
