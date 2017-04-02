package com.emergency.a108_publicapp;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

/**
 * Created by rahul on 2/4/17.
 */
public class AfterPopup extends Service implements View.OnTouchListener, View.OnKeyListener{
    private static final String TAG = AfterPopup.class.getSimpleName();

    WindowManager windowManager;
    WindowManager.LayoutParams param;
    RelativeLayout relativeLayout;

    HomeWatcher mHomeWatcher;
    boolean isVisible;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        windowManager  = (WindowManager) getSystemService(WINDOW_SERVICE);

        param = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                PixelFormat.TRANSLUCENT
        );
        param.gravity = Gravity.CENTER;

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        relativeLayout = (RelativeLayout) layoutInflater.inflate(R.layout.popup, null);

        relativeLayout.setOnTouchListener(this);
        relativeLayout.setOnKeyListener(this);

        isVisible = false;

        initHomeListener();
    }

    public void onDestroy() {
        hide();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() called with: intent = [" + intent + "], flags = [" + flags + "], startId = [" + startId + "]");
        show();
        return Service.START_STICKY;
    }

    public void initHomeListener() {
        mHomeWatcher = new HomeWatcher(this);
        mHomeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                Log.d(TAG, "onHomePressed() called");
                hide();
            }

            @Override
            public void onHomeLongPressed() {
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_OUTSIDE :
                hide();
        }
        return false;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        Log.d("TAG", "onKey() called with: " + "v = [" + v + "], keyCode = [" + keyCode + "], event = [" + event + "]");
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction()== KeyEvent.ACTION_UP){
            hide();
        }
        return false;
    }

    private void show(){
        if(!isVisible){
            windowManager.addView(relativeLayout, param);
            isVisible = true;
        }
    }

    private void hide(){
        if(isVisible){
            windowManager.removeView(relativeLayout);
            isVisible = false;
        }
    }
}
