package com.benjaminearley.hackproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(LOCATION_SERVICE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(0, R.anim.fade_out);
        } else {
            Intent intent = new Intent(this, PermissionActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(0, R.anim.fade_out);
        }
    }
}
