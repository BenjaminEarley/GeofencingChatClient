package com.benjaminearley.hackproject;

import android.app.IntentService;
import android.content.Intent;

import com.benjaminearley.hackproject.util.SharedPreferencesUtil;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

public class GeofencePlaceTransitionsIntentService extends IntentService {

    public GeofencePlaceTransitionsIntentService() {
        super(GeofencePlaceTransitionsIntentService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {

            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
            for (Geofence geofence : geofencingEvent.getTriggeringGeofences()) {

                String triggeringGeofence = geofence.getRequestId();

                int placeId = Integer.parseInt(triggeringGeofence);

                String name = SharedPreferencesUtil.getLocation(this, String.valueOf(placeId));

                long time = System.currentTimeMillis();


                if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
                    if (time - SharedPreferencesUtil.getTime(this) > 100) {
                        SharedPreferencesUtil.onLocation(this, "Hack Chat");
                    }
                } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
                    SharedPreferencesUtil.onLocation(this, name);
                    SharedPreferencesUtil.setTime(this, time);
                }

            }
        }
    }

}
