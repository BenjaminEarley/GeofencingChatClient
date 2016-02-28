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

    public static String getFirstName(Context c) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        return sp.getString(c.getString(R.string.pref_first_name), "");
    }

    public static String getLastName(Context c) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        return sp.getString(c.getString(R.string.pref_last_name), "");
    }

    public static String getAge(Context c) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        return sp.getString(c.getString(R.string.pref_age), "");
    }

    public static String getGender(Context c) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        return sp.getString(c.getString(R.string.pref_gender), "");
    }

    public static boolean isLoggedIn(Context c) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        return sp.getBoolean(c.getString(R.string.pref_login), false);
    }

    public static void setFirstName(Context c, String firstName) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor spe = sp.edit();
        spe.putString(c.getString(R.string.pref_first_name), firstName);
        spe.apply();
    }

    public static void setLastName(Context c, String lastName) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor spe = sp.edit();
        spe.putString(c.getString(R.string.pref_last_name), lastName);
        spe.apply();
    }

    public static void setAge(Context c, String age) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor spe = sp.edit();
        spe.putString(c.getString(R.string.pref_age), age);
        spe.apply();
    }

    public static void setGender(Context c, String gender) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor spe = sp.edit();
        spe.putString(c.getString(R.string.pref_gender), gender);
        spe.apply();
    }

    public static void setUserLogin(Context c, String email, String password) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor spe = sp.edit();
        spe.putString(c.getString(R.string.pref_login_email), email);
        spe.putString(c.getString(R.string.pref_login_password), password);
        spe.putBoolean(c.getString(R.string.pref_login), true);
        spe.apply();
    }

    public static void setisLogin(Context c, boolean isLoggined) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor spe = sp.edit();
        spe.putBoolean(c.getString(R.string.pref_login), isLoggined);
        spe.apply();
    }

    public static void clear(Context c) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor spe = sp.edit();
        spe.clear();
        spe.apply();
    }

}
