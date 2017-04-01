package com.emergency.a108_publicapp;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ViewUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;

import java.util.zip.Inflater;

import static java.security.AccessController.getContext;

public class HomeActivity extends AppCompatActivity {

    public String TAG= "homeactivity";
    private TSnackbar snack;
    private ImageView callEmergency;
    private TextView nearbyMap;
    private NearbyBottomSheetDialog nearbyBottomSheetDialog;
    private LinearLayout hrsvLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        callEmergency = (ImageView)findViewById(R.id.call_emergency);
        nearbyMap = (TextView)findViewById(R.id.btn_nearby_map);

        snack = TSnackbar.make(findViewById(R.id.parent_home_content),"Please give Permissions.", TSnackbar.LENGTH_INDEFINITE);

        if(checkPermissions()){
            permissionsGranted();
        }else{
            permissionsNotGranted();
        }


        callEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                call108();
            }
        });

        nearbyBottomSheetDialog = NearbyBottomSheetDialog.getInstance();
        nearbyMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nearbyBottomSheetDialog.show(getSupportFragmentManager(), nearbyBottomSheetDialog.getTag());
            }
        });

        hrsvLL = (LinearLayout) findViewById(R.id.hrsv_ll);
        fetchTrustContacts();
    }


    private void fetchTrustContacts(){
        for(int i=0;i<5;i++){
            RelativeLayout imageView = (RelativeLayout) getLayoutInflater().inflate(R.layout.circular_item, null);
            hrsvLL.addView(imageView);
        }
    }


    public boolean checkPermissions(){
        // Here, thisActivity is the current activity
        if( (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED)||(ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)||(ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED)||(ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED)||(ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) ||(ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) ){

            Log.d(TAG, "myCheckPermission() if called with: " + "thisActivity = [" + this + "]");
            return false;
        }
        else{
            return true;
        }
    }

    private void permissionsGranted(){
        snack.setText("Thank you. All requested permissions are available.");
        snack.setDuration(TSnackbar.LENGTH_SHORT);
        snack.show();
        callEmergency.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_call_blue_24px));
    }

    private void permissionsNotGranted(){
        snack.setText("Required permissions are not granted yet.");
        snack.setDuration(TSnackbar.LENGTH_INDEFINITE);
        snack.setAction("Grant", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermissions();
            }
        });
        snack.show();
        callEmergency.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_call_black_24px));
    }

    private static final  int MY_PERMISSIONS_REQUEST_CODE =101;

    private void requestPermissions(){
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.INTERNET, android.Manifest.permission.SEND_SMS, android.Manifest.permission.RECEIVE_SMS, android.Manifest.permission.CALL_PHONE},
                MY_PERMISSIONS_REQUEST_CODE);
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

    private void call108() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:108"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions();
            return;
        } else {
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_edit_ac){
            Intent intent = new Intent(getApplicationContext(), CircleOfTrustActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }




}




