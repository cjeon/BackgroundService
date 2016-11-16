package com.example.cjeon.backgroundservice.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class TestIntentService extends IntentService {

    public TestIntentService() {
        super("TestIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        String msg = intent.getStringExtra("msg");

        Intent resultIntent = new Intent("BROADCAST")
                .putExtra("msg", msg);
        LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent);
    }

}
