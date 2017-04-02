package com.emergency.a108_publicapp;

import android.*;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.emergency.a108_publicapp.Constants.IS_ASSIGNED;
import static com.emergency.a108_publicapp.Constants.SHARED_PREFERENCES;

/**
 * Created by rahul on 2/4/17.
 */
public class TrackActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = TrackActivity.class.getSimpleName();
    GoogleMap m_map;
    Button markComplete;
    LinearLayout nameContainer;
    String lat, lng;
    MarkerOptions clientPosition;
    boolean mapReady = false;
    boolean shouldShow = false;
    public Activity trackActivity;
    private String requestId;
    private String uid,city;

    private TextView userTV, phoneTV;
    TextView timeLeft;

    private BottomSheetBehavior mBottomSheetBehavior;

    private BottomSheetBehavior bottomSheetBehavior;

    FirebaseDatabase database;

    //TODO Convert int to float
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
        Log.d(TAG, "onCreate() called with: " + "savedInstanceState = [" + savedInstanceState + "]");
        
        trackActivity=TrackActivity.this;

        Intent data = getIntent();
        requestId = data.getStringExtra("requestId");

//        Intent i= new Intent(TrackActivity.this,GetLocation.class);
//        startService(i);


        final Button makeComplete = (Button)findViewById(R.id.complete_button);
        makeComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(requestId != null) {
                    makeComplete.setEnabled(false);
                    Toast.makeText(TrackActivity.this, "Task Completed", Toast.LENGTH_LONG).show();
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(IS_ASSIGNED, false);
                    editor.commit();
                    Log.d(TAG, "onClick() called with: view = [" + view + "]");
                    markRequestComplete(requestId);
                    finish();
                } else {
                    Toast.makeText(TrackActivity.this,"No request id",Toast.LENGTH_LONG).show();
                }
            }
        });
        timeLeft = (TextView)findViewById(R.id.time_left);
//        RequestItem requestItem = new RequestItem(getIntent().getStringExtra("requestItem"));
//        requestId = requestItem.getRequestId();
//        lat = requestItem.getLatitude();
//        Log.d(TAG, "onCreate() called with: latityse = [" + lat + "]");
//        lng = requestItem.getLongitude();
//        Log.d(TAG, "onCreate() called with: lng = [" + lng + "]");
        shouldShow=true;

        database = FirebaseDatabase.getInstance();




//        setAddress((TextView)findViewById(R.id.user_address));
        userTV = (TextView)findViewById(R.id.user_name);
        //((TextView)findViewById(R.id.user_gender)).setText(requestItem.getGender());
        //((TextView)findViewById(R.id.user_age)).setText(requestItem.getAge());
        phoneTV = (TextView)findViewById(R.id.user_phone);
        phoneTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone(phoneTV.getText().toString());
            }
        });
        findViewById(R.id.imagePhone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone(phoneTV.getText().toString());
            }
        });



        findViewById(R.id.callButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                        Uri.parse("google.navigation:q="+lat+","+lng));
//                startActivity(intent);
                callPhone(phoneTV.getText().toString());
            }
        });
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


//        DisplayMetrics displaymetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//        int height = displaymetrics.heightPixels;
//        int bottomHeight = height/6;


        Resources r = getResources();
        int bottomHeight  = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics());

        findViewById(R.id.map).setPadding(0,0,0,bottomHeight);
        final int bottomHeight2 = bottomHeight;
        View bottomSheet = findViewById( R.id.bottom_sheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setPeekHeight(bottomHeight);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    mBottomSheetBehavior.setPeekHeight(bottomHeight2);
                }
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    Log.d(TAG, "onStateChanged() called with: bottomSheet = [" + bottomSheet + "], newState = [" + newState + "]");
                    //mBottomSheetBehavior.setPeekHeight(bottomHeight);
                }
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
                //mBottomSheetBehavior.setState(Bot);
                Log.d(TAG, "onSlide() called with: bottomSheet = [" + bottomSheet + "], slideOffset = [" + slideOffset + "]");
            }
        });

        getUid();

    }

    @Override
    public void onMapReady(GoogleMap map) {
        mapReady = true;
        m_map = map;
        m_map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

//        if(shouldShow){
//            showPoint();
//        }
    }

    private void showPoint(){
        if (mapReady) {
            if (lat.length() != 0 && lng.length() != 0) {
                LatLng latLng = new LatLng(Float.parseFloat(lat), Float.parseFloat(lng));
                CameraPosition target = CameraPosition.builder().target(latLng).zoom(15).build();
                clientPosition = new MarkerOptions().position(latLng).title("Emergency");
                m_map.moveCamera(CameraUpdateFactory.newCameraPosition(target));
                m_map.addMarker(clientPosition);
            }
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu)
//    {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item)
//    {
//
//        switch (item.getItemId())
//        {
//            case R.id.menu_logout:
//                // Single menu item is selected do something
//                // Ex: launching new activity/screen or show alert message
//                logout();
//                finish();
//                return true;
//
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    public void markRequestComplete(String requestId){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference requestRef = database.getReference("requests/"+requestId+"/status");
        requestRef.setValue("completed");


    }

//    private void setAddress(TextView address){
//
//        address.setText(getAddress(Double.parseDouble(lat),Double.parseDouble(lng)));
//
//    }

    private String getAddress(double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                result.append(address.getAddressLine(0)+", ");
                result.append(address.getLocality()+", "+address.getAdminArea()+", " +address.getCountryName());
//                result.append(address.getCountryName());
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }

        return result.toString();
    }

    private void getUid(){
        DatabaseReference myRef = database.getReference("requests/"+requestId);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange() called with: dataSnapshot = [" + dataSnapshot.toString() + "]");
                uid = dataSnapshot.child("driverId").getValue().toString();
                getCity("cityData/" + uid);
                Log.d(TAG, "get data onDataChange() called with: uid = [" + uid + "]");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getDistance(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("subscription", response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    timeLeft.setText(jsonResponse.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("duration").getString("text"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
        //Log.d(TAG, "onResponse() called with: phoneNumer =[" + phoneNumber + "]");
        VolleyRequest volleyRequest= new VolleyRequest("&origins="+lat+","+lng+"&destinations="+lat+","+lng, responseListener, errorListener);
        volleyRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 5, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(volleyRequest);
    }

    public void getDriverLocation() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("driver/" + city + "/" + uid);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange() called with: dataSnapshot = [" + dataSnapshot.toString() + "]");
                if((dataSnapshot.child("latitude").getValue()!=null)&&(dataSnapshot.child("longitude").getValue()!=null)){
                    lat = dataSnapshot.child("latitude").getValue().toString();
                    lng = dataSnapshot.child("longitude").getValue().toString();
                    getDistance();
                    showPoint();
                }
                Log.d(TAG, "get data onDataChange() called with: city = [" + city + "]");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getCity(String location) {
        DatabaseReference myRef = database.getReference(location);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange() called with: dataSnapshot = [" + dataSnapshot.toString() + "]");
                city = dataSnapshot.child("city").getValue().toString();
                getDriverLocation();
                Log.d(TAG, "get data onDataChange() called with: city = [" + city + "]");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void callPhone(String phoneNumber){
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+phoneNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions();
            return;
        }else{
            startActivity(intent);
        }
    }

    private void requestPermissions(){
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.CALL_PHONE},
                101);
    }

}
