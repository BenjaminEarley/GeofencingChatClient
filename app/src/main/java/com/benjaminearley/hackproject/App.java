package com.benjaminearley.hackproject;

import android.app.Application;
import android.os.SystemClock;

import com.firebase.client.Firebase;

import java.util.concurrent.TimeUnit;

public class App extends Application {

    private static Firebase firebase;

    public static Firebase getFirebaseRef() {
        if (firebase == null) {
            firebase = new Firebase("https://odu-hackathon.firebaseio.com/");
        }
        return firebase;
    }

    public static Firebase getMessagesRef() {
        return getFirebaseRef().child("Messages");
    }

    public static Firebase getUsersRef() {
        return getFirebaseRef().child("Users");
    }

    public static String getAuthUuid() {
        return App.getFirebaseRef().getAuth().getUid();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);

        // Don't do this! This is just so cold launches take some time
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(1));
    }
}
