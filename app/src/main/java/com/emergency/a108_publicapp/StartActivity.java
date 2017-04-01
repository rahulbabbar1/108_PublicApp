package com.emergency.a108_publicapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by rahul on 14/12/16.
 */
public class StartActivity extends AppCompatActivity{

    private static final String TAG = StartActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences(getString(R.string.shared_preference_name), MODE_PRIVATE);
        Boolean isLoggedIn = prefs.getBoolean(getString(R.string.shared_preference_login), false);
        if(isLoggedIn){
            Intent main = new Intent(this,HomeActivity.class);
            startActivity(main);
        }
        else {
            Intent login = new Intent(this,LoginActivity.class);
            startActivity(login);
        }
        finish();
        setContentView(R.layout.activity_main);
    }
}
