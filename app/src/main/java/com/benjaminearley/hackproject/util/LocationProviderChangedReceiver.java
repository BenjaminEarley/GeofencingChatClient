package com.benjaminearley.hackproject.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

public class LocationProviderChangedReceiver extends BroadcastReceiver {

    public final String TAG = LocationProviderChangedReceiver.class.getSimpleName();

    public LocationProviderChangedReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {


        if (isGappSafeLocationMode(context)) {

        } else {
            SharedPreferencesUtil.onLocation(context, "Hack Chat");
        }
    }


    public boolean isGappSafeLocationMode(Context context) {
        int locationMode = 99;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            return (locationMode != Settings.Secure.LOCATION_MODE_OFF && locationMode != Settings.Secure.LOCATION_MODE_SENSORS_ONLY);

        } else {
            //TODO: need more research on this
            String locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }

    }
}
