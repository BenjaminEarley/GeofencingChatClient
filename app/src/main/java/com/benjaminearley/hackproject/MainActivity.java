package com.benjaminearley.hackproject;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.benjaminearley.hackproject.GeofenceCallbackService.GeofenceCallbackBinder;
import com.benjaminearley.hackproject.util.SharedPreferencesUtil;

public class MainActivity extends AppCompatActivity {

    GeofenceCallbackService mService;
    boolean mBound = false;

    private ImageView profileImage;


    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            GeofenceCallbackBinder binder = (GeofenceCallbackBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        profileImage = (ImageView) findViewById(R.id.profile_icon);

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        Intent intent = new Intent(this, GeofenceCallbackService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    public void onProfileIconClick(View view) {

        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("email", SharedPreferencesUtil.getUserEmail(this));

        startActivity(intent);


    }
}
