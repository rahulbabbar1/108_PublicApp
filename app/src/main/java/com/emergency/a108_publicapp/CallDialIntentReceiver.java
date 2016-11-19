package com.emergency.a108_publicapp;

/**
 * Created by rahul on 19/11/16.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;


public class CallDialIntentReceiver extends BroadcastReceiver{
    Context context;

    @Override
    public void onReceive(Context context, Intent intent){
        try{
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                //Toast.makeText(context, "Phone Is Ringing " + intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER), Toast.LENGTH_LONG).show();

            }

            if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                if (intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)!=null){
                    if (intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER).equals("108")){
                        Toast.makeText(context, "Call Recieved"  + intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER), Toast.LENGTH_LONG).show();
                        Intent i =new Intent(context, LocationSender.class);
                        context.startService(i);
                    }
                }
            }

            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                //Toast.makeText(context, "Phone Is Idle"  + intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER), Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception e){e.printStackTrace();}
    }

}
