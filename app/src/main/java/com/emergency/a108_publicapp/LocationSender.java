package com.emergency.a108_publicapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by rahul on 19/11/16.
 */
public class LocationSender extends Service{
    GPSTracker gps;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        Toast.makeText(this, "Service Created", Toast.LENGTH_LONG).show();
        gps = new GPSTracker(this);
        if(gps.canGetLocation()){
            Toast.makeText(this, "Can get location", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Latitude: " + gps.getLatitude() + "LOngitude: " + gps.getLongitude(), Toast.LENGTH_LONG).show();

        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

    }
    @Override
    public void onStart(Intent intent, int startid) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
        gps.stopUsingGPS();
    }
}
