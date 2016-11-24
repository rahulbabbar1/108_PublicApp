package com.emergency.a108_publicapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    Activity mainActivity;
    public String TAG="mainactivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity=MainActivity.this;
        myCheckPermission(mainActivity);
        Intent i= new Intent(MainActivity.this,GetLocation.class);
        startService(i);
    }

    public final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    public void myCheckPermission(Activity thisActivity){
        // Here, thisActivity is the current activity
        if( (ContextCompat.checkSelfPermission(thisActivity,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED)||(ContextCompat.checkSelfPermission(thisActivity,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)||(ContextCompat.checkSelfPermission(thisActivity,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED)||(ContextCompat.checkSelfPermission(thisActivity,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED)||(ContextCompat.checkSelfPermission(thisActivity,
                Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) ){

//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(thisActivity,
//                    Manifest.permission.READ_CONTACTS)) {
//
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//            } else {

                // No explanation needed, we can request the permission.
            Log.d(TAG, "myCheckPermission() if called with: " + "thisActivity = [" + thisActivity + "]");

                ActivityCompat.requestPermissions(thisActivity,
                        new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET,Manifest.permission.SEND_SMS,Manifest.permission.RECEIVE_SMS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            //}

        }
        else{
            Log.d(TAG, "myCheckPermission() else called with: " + "thisActivity = [" + thisActivity + "]");
            Intent i= new Intent(MainActivity.this,GetLocation.class);
            startService(i);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                myCheckPermission(mainActivity);
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
