package com.fsck.ertebat.service;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.fsck.ertebat.ertebat;

public class PushService extends CoreService {
    private static String START_SERVICE = "com.fsck.ertebat.service.PushService.startService";
    private static String STOP_SERVICE = "com.fsck.ertebat.service.PushService.stopService";

    public static void startService(Context context) {
        Intent i = new Intent();
        i.setClass(context, PushService.class);
        i.setAction(PushService.START_SERVICE);
        addWakeLock(context, i);
        context.startService(i);
    }

    public static void stopService(Context context) {
        Intent i = new Intent();
        i.setClass(context, PushService.class);
        i.setAction(PushService.STOP_SERVICE);
        addWakeLock(context, i);
        context.startService(i);
    }

    @Override
    public int startService(Intent intent, int startId) {
        int startFlag = START_STICKY;
        if (START_SERVICE.equals(intent.getAction())) {
            if (ertebat.DEBUG)
                Log.i(ertebat.LOG_TAG, "PushService started with startId = " + startId);
        } else if (STOP_SERVICE.equals(intent.getAction())) {
            if (ertebat.DEBUG)
                Log.i(ertebat.LOG_TAG, "PushService stopping with startId = " + startId);
            stopSelf(startId);
            startFlag = START_NOT_STICKY;
        }

        return startFlag;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setAutoShutdown(false);
    }


    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }
}