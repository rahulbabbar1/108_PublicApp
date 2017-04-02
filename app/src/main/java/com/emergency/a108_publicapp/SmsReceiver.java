package com.emergency.a108_publicapp;

/**
 * Created by rahul on 24/11/16.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsReceiver extends BroadcastReceiver {

    static String recievedMSG = null;
     String time,requestID;
    static String TAG = "smsreceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage msg = null;
        if (null != bundle) {
            Object[] smsObj = (Object[]) bundle.get("pdus");
            for (Object object : smsObj) {
                msg = SmsMessage.createFromPdu((byte[]) object);
//                Date date = new Date(msg.getTimestampMillis());
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                String receiveTime = format.format(date);
                recievedMSG = msg.getDisplayMessageBody();
                if (recievedMSG.startsWith("Lateroxdriver")) {
                    Log.d(TAG, "onReceive() called with: context = [" + context + "], intent = [" + intent + "]");
                    String regex = "^Lateroxdriver (.+)";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(recievedMSG);
                    if (matcher.find()) {
//                        time = matcher.group(1);
//                        Log.d(TAG," time = " + time + " reqId = "+requestID);
                        requestID = matcher.group(1);
                        Log.d(TAG," time = " + time + " reqId = "+requestID);
                        Intent i = new Intent(context, TrackActivity.class);
                        //i.putExtra("time", time);
                        i.putExtra("requestId", requestID);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(i);
                    }
//                    int firstClosBrac = recievedMSG.indexOf(']');
//                    latitude = recievedMSG.substring(recievedMSG.indexOf('[')+1,firstClosBrac);
//                    longitude = recievedMSG.substring(recievedMSG.indexOf('[',firstClosBrac)+1,recievedMSG.indexOf(']',firstClosBrac+1));
                }
            }
        }
    }
}
