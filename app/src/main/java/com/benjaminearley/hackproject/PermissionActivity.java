package com.benjaminearley.hackproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class PermissionActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION_SERVICES = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        if (mayRequestLocation()) {
            Intent intent = new Intent(this, PermissionActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(0, R.anim.fade_out);
        }
    }

    private boolean mayRequestLocation() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
            requestPermissions(new String[]{ACCESS_FINE_LOCATION}, REQUEST_LOCATION_SERVICES);
        } else {
            requestPermissions(new String[]{ACCESS_FINE_LOCATION}, REQUEST_LOCATION_SERVICES);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION_SERVICES) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, R.anim.fade_out);
            }
        }
    }
}

