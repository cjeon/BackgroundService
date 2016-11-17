package com.example.cjeon.backgroundservice.activities;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.AlarmClock;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.cjeon.backgroundservice.R;
import com.example.cjeon.backgroundservice.services.TestIntentService;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private BroadcastReceiver localBroadcastReceiver;
    private BroadcastReceiver globalBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter intentFilter = new IntentFilter("BROADCAST");

        localBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String msg = "local, " + intent.getStringExtra("msg");
                runSnackBar(msg);
            }};

        globalBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String msg = "global, " + intent.getStringExtra("msg");
                runSnackBar(msg);
            }};

        registerReceiver(globalBroadcastReceiver, intentFilter);
        LocalBroadcastManager.getInstance(this).registerReceiver(localBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(globalBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(localBroadcastReceiver);
    }

    public void localBroadcastWithService(View view) {
        Intent intent = new Intent(this, TestIntentService.class);
        intent.putExtra("msg", "service");
        startService(intent);
    }

    public void globalBroadcastWithAlarmManager(View view) {
        Intent intent = new Intent("BROADCAST");
        intent.putExtra("msg", "alarm manager");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // global
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                pendingIntent);
    }

    public void setAlarm(View view) {
        Intent alarm = new Intent(AlarmClock.ACTION_SET_ALARM)
                .putExtra(AlarmClock.EXTRA_HOUR, Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
                .putExtra(AlarmClock.EXTRA_MINUTES, Calendar.getInstance().get(Calendar.MINUTE));
        startActivity(alarm);
    }

    public void runSnackBar(String msg) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_main), msg, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

    public static class BootReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_account_balance_black_24dp)
                        .setContentTitle("Boot Receiver")
                        .setContentText("Boot Completed!");
                ((NotificationManager) context.getSystemService(NOTIFICATION_SERVICE)).notify(0, builder.build());
            }
        }
    }
}

