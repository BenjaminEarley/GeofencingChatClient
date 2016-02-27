package com.benjaminearley.hackproject;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class GeofenceCallbackService extends Service {

    private final IBinder mBinder = new GeofenceCallbackBinder();

    public GeofenceCallbackService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class GeofenceCallbackBinder extends Binder {
        GeofenceCallbackService getService() {
            // Return this instance of LocalService so clients can call public methods
            return GeofenceCallbackService.this;
        }
    }
}
