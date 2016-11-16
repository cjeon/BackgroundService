package com.example.cjeon.backgroundservice.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.SystemClock;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.cjeon.backgroundservice.R;
import com.example.cjeon.backgroundservice.Services.TestIntentService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter intentFilter = new IntentFilter("BROADCAST");
        LocalBroadcastManager.getInstance(this).registerReceiver(new ResponseReceiver(), intentFilter);
    }

    public void startTestIntentService(View view) {
        Intent intent = new Intent(this, TestIntentService.class);
        startService(intent);
    }

    public void start3sAlarm(View view) {
        Intent intent = new Intent("BROADCAST");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 123, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime(),
                pendingIntent);
    }

    private class ResponseReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_main), "text", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
        }
    }
}

