package com.emergency.a108_publicapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Activity mainActivity;
    public String TAG="mainactivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity=MainActivity.this;
        findViewById(R.id.call_108).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call108();
            }
        });
        if(checkPermissions()){
            permissionsGranted();
        }else{
            permissionsNotGranted();
        }
    }

    private void permissionsGranted(){
        TextView tv=((TextView)findViewById(R.id.status_textView));
        findViewById(R.id.permissions_button).setVisibility(View.INVISIBLE);
        ((ImageView)findViewById(R.id.status_image)).setImageResource(R.mipmap.tick);
        tv.setText("It's All set");
        tv.setTextColor(Color.LTGRAY);
    }

    private void permissionsNotGranted(){
        TextView tv=((TextView)findViewById(R.id.status_textView));
        findViewById(R.id.permissions_button).setVisibility(View.VISIBLE);
        findViewById(R.id.permissions_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
            }
        });
        ((ImageView)findViewById(R.id.status_image)).setImageResource(R.mipmap.cross);
        tv.setText("Permission Required!!!");
        tv.setTextColor(Color.rgb(220,20,60));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CODE: {
                boolean isGranted=true;
                for(int i=0;i<grantResults.length;i++){
                    if(grantResults[i]!= PackageManager.PERMISSION_GRANTED)
                        isGranted=false;
                }
                if (isGranted) {
                    permissionsGranted();
                } else {
                    permissionsNotGranted();
                }
            }
        }
    }

    public boolean checkPermissions(){
        // Here, thisActivity is the current activity
        if( (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED)||(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)||(ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED)||(ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED)||(ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) ||(ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) ){

            Log.d(TAG, "myCheckPermission() if called with: " + "thisActivity = [" + this + "]");
            return false;

        }
        else{

            return true;
        }
    }

    private static final  int MY_PERMISSIONS_REQUEST_CODE =101;

    private void requestPermissions(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET,Manifest.permission.SEND_SMS,Manifest.permission.RECEIVE_SMS,Manifest.permission.CALL_PHONE},
                MY_PERMISSIONS_REQUEST_CODE);
    }

    private void call108(){
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:108"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions();
            return;
        }else{
            startActivity(intent);
        }

    }
}
