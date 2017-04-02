package com.emergency.a108_publicapp;

/**
 * Created by rahul on 19/11/16.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;


public class CallDialIntentReceiver extends BroadcastReceiver{
    Context context;

    @Override
    public void onReceive(Context context, Intent intent){
        try{
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            Log.d("call receiver", "onReceive() called with: state = [" + state + "], mobile = [" + intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER) + "]");
            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                //Toast.makeText(context, "Phone Is Ringing " + intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER), Toast.LENGTH_LONG).show();

            }

            if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                if (intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)!=null){
                    if (intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER).equals("108")){
                        //Toast.makeText(context, "Call Recieved"  + intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER), Toast.LENGTH_LONG).show();
                        Intent i =new Intent(context, GetLocation.class);
                        context.startService(i);
                    }
                }
            }

            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                Log.d("test", "onReceive() called with: context = [" + context + "], intent = [" + intent + "]");
                if (intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)!=null){
                    Log.d("test", "onReceive() called with: context = [" + context + "], intent = [" + intent + "]");
                    String mobile = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    if (mobile.equals("8118830489")||mobile.equals("+918118830489")||mobile.equals("08118830489")){
                        Log.d("test", "onReceive() called with: context = [" + context + "], intent = [" + intent + "]");
                        Intent i =new Intent(context, AfterPopup.class);
                        context.startService(i);
                    }
                }
            }
        }
        catch(Exception e){e.printStackTrace();}
    }

}
