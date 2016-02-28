package com.benjaminearley.hackproject;

import android.Manifest;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.benjaminearley.hackproject.models.Places;
import com.benjaminearley.hackproject.util.SharedPreferencesUtil;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class GeofenceCallbackService extends Service implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private final IBinder mBinder = new GeofenceCallbackBinder();

    private GoogleApiClient mGoogleApiClient;

    private PendingIntent mPlacesGeofencePendingIntent;
    private GeofencingRequest.Builder geofencingRequestBuilder;

    public GeofenceCallbackService() {
    }

    protected synchronized void buildGoogleApiClient(Context context) {
        mGoogleApiClient = new GoogleApiClient.Builder(context.getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    public void start() {
        buildGoogleApiClient(this);
        mGoogleApiClient.connect();
    }

    public void stop() {
        mGoogleApiClient.disconnect();
    }

    public void setPlaces() {

        Query queryRef = App.getPlacesRef().orderByChild("name");
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                    final Places place = snapshot.getValue(Places.class);
                    SharedPreferencesUtil.setEnterLocation(GeofenceCallbackService.this, snapshot.getKey(), place.getName());
                    mPlacesGeofencePendingIntent = getPlacesGeofencePendingIntent();
                    geofencingRequestBuilder = new GeofencingRequest.Builder();
                    geofencingRequestBuilder.addGeofence(new Geofence.Builder()
                            .setRequestId(snapshot.getKey())
                            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                            .setCircularRegion(place.getLatitude(), place.getLongitude(), 40f)
                            .setExpirationDuration(Geofence.NEVER_EXPIRE)
                            .build());
                    LocationServices.GeofencingApi.addGeofences(mGoogleApiClient, geofencingRequestBuilder.build(),
                            mPlacesGeofencePendingIntent);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    private PendingIntent getPlacesGeofencePendingIntent() {
        // Reuse the PendingIntent if we already have it.
        if (mPlacesGeofencePendingIntent != null) {
            return mPlacesGeofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofencePlaceTransitionsIntentService.class);
        // We use FLAG_UPDATE_CURRENT so that we get the same pending intent back when
        // calling addGeofences() and removeGeofences().
        return PendingIntent.getService(this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        setPlaces();

        LocationRequest mLocationRequest = new LocationRequest();

        mLocationRequest.setInterval(2000L);

        mLocationRequest.setFastestInterval(500L);

        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("loc", location.toString());
    }

    public class GeofenceCallbackBinder extends Binder {
        GeofenceCallbackService getService() {
            // Return this instance of LocalService so clients can call public methods
            return GeofenceCallbackService.this;
        }
    }
}
