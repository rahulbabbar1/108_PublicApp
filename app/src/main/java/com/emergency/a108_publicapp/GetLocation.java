package com.emergency.a108_publicapp;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by rahul on 24/11/16.
 */


public class GetLocation extends Service {
    public String TAG="getlocation";
    public int count=0;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate() called with: " + "");
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            Toast.makeText(GetLocation.this,"Please Enable Location first",Toast.LENGTH_LONG).show();
            showSettings(GetLocation.this);
        }
        else{

        }

// Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                //Called when a new location is found by the network location provider.
                //makeUseOfNewLocation(location);
                if(count==0){
                    sendSMS(location.getLatitude(),location.getLongitude());
                }
                //Toast.makeText(GetLocation.this, "onLocationChanged() called with: " + "location [ longitude = " + location.getLongitude() + " latitude = " + location.getLatitude() + "]",Toast.LENGTH_LONG).show();
                Log.d(TAG, "onLocationChanged() called with: " + "location [ longitude = " + location.getLongitude() + " latitude = " + location.getLatitude() + " type: " +location.getProvider() + " accuracy : " + location.getAccuracy()+"]" );
                count++;
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

// Register the listener with the Location Manager to receive location updates
        Log.d(TAG, "onCreate() permission called with: " + ContextCompat.checkSelfPermission(GetLocation.this,
                Manifest.permission.ACCESS_FINE_LOCATION) + "");
        if((ContextCompat.checkSelfPermission(GetLocation.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)){
            Log.d(TAG, "onCreate() inside if called with: " + "");
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

    }
    @Override
    public void onStart(Intent intent, int startid) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
    }



    public void showSettings(Context mContext){
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
//
//        // Setting Dialog Title
//        alertDialog.setTitle("GPS is settings");
//
//        // Setting Dialog Message
//        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
//
//        // On pressing Settings button
//        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                mContext.startActivity(intent);
//            }
//        });
//        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//
//        // on pressing cancel button
//
//        // Showing Alert Message
//        alertDialog.show();]

        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    public void sendSMS(final double latitude, final double longitude){
        new Thread(new Runnable() {
            @Override
            public void run() {
                // do the thing that takes a long time
                Log.d(TAG, "sendSMS run() called with: " + "");
                String smsBody = "Latiitude: [" + latitude + "] " +  "Longitude: [" + longitude + "] ";
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(Constants.serverNumber, null, smsBody, null, null);
            }
        }).start();
    }
}