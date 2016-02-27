package com.benjaminearley.hackproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.benjaminearley.hackproject.util.SharedPreferencesUtil;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (SharedPreferencesUtil.isLoggedIn(this)) {

            App.getFirebaseRef().authWithPassword(SharedPreferencesUtil.getUserEmail(this), SharedPreferencesUtil.getUserPassword(this), new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(0, R.anim.fade_out);
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    startLoginActivity();
                }
            });

        } else {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                startLoginActivity();
            } else {
                if (checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    startLoginActivity();
                } else {
                    Intent intent = new Intent(this, PermissionActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(0, R.anim.fade_out);
                }
            }
        }
    }

    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, R.anim.fade_out);
    }
}
