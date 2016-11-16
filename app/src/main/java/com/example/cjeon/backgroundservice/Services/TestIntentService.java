package com.example.cjeon.backgroundservice.Services;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import static android.R.attr.action;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class TestIntentService extends IntentService {
    private static final String ACTION_NAME = "com.example.cjeon.backgroundservice.Services.action.NAME";

    private static final String EXTRA_PARAM1 = "com.example.cjeon.backgroundservice.Services.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.example.cjeon.backgroundservice.Services.extra.PARAM2";

    public TestIntentService() {
        super("TestIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
//        String action = intent.getAction();
        Integer param1 = intent.getIntExtra(EXTRA_PARAM1, -1);

        Intent resultIntent = new Intent("BROADCAST")
                .putExtra("DATA", param1 + 100);
        LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent);
    }

}
