package com.emergency.a108_publicapp;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rahul on 14/12/16.
 */

public class VolleyRequest extends StringRequest {

    private static final String GENERATE_OTP_URL = "http://dashboard108.herokuapp.com/otp/gen?";
    private static final String CHECK_OTP_URL = "http://dashboard108.herokuapp.com/otp/check?";
    private Map<String, String> params;
    private String TAG = "Volley";

    public VolleyRequest(String mobile, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Request.Method.GET, GENERATE_OTP_URL + "mobile=" + mobile, listener, errorListener);
//        Log.d(TAG, "VolleyRequest() called with:"+GENERATE_OTP_URL+" mobile = [" + mobile + "], listener = [" + listener + "], errorListener = [" + errorListener + "]");
//        params = new HashMap<>();
//        params.put("mobile", mobile);
    }

    public VolleyRequest(String mobile, String otp, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Request.Method.GET, CHECK_OTP_URL  + "mobile=" + mobile + "&otp="+ otp, listener, errorListener);
        params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("otp", otp);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}


