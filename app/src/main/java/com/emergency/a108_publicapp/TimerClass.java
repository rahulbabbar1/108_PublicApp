package com.emergency.a108_publicapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.github.pavlospt.CircleView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by rahul on 18/12/16.
 */
public class TimerClass extends AppCompatActivity {
    private static final String TAG = TimerClass.class.getSimpleName();

    CircleView circleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Intent data = getIntent();

        circleView = (CircleView) findViewById(R.id.cicleView);
        circleView.setTitleText("Calculating..");

        if (data.getStringExtra("time") != null) {
            int time = Integer.parseInt(data.getStringExtra("time"));


            final long startTime = time * 1000;

            final long interval = 1 * 1000;

            new CountDownTimer(startTime, interval) {

                public void onTick(long millisUntilFinished) {
                    int sec= (int)millisUntilFinished / 1000;
                    int min  = (int)(sec/60);
                    sec = sec - (int)(min*60);
                    circleView.setTitleText(min+" min " +sec + "sec");
                }

                public void onFinish() {
                    circleView.setTitleText("Time Over!");
                }
            }.start();
        }

    }
}