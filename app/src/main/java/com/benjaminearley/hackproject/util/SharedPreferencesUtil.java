package com.benjaminearley.hackproject.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.benjaminearley.hackproject.R;

public class SharedPreferencesUtil {

    public static String getUserEmail(Context c) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        return sp.getString(c.getString(R.string.pref_login_email), "");
    }

    public static String getUserPassword(Context c) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        return sp.getString(c.getString(R.string.pref_login_password), "");
    }

    public static boolean isLoggedIn(Context c) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        return sp.getBoolean(c.getString(R.string.pref_login), false);
    }

    public static void setUserLogin(Context c, String email, String password) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor spe = sp.edit();
        spe.putString(c.getString(R.string.pref_login_email), email);
        spe.putString(c.getString(R.string.pref_login_password), password);
        spe.putBoolean(c.getString(R.string.pref_login), true);
        spe.apply();
    }
}
