package com.emergency.a108_publicapp;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rahul on 14/12/16.
 */

public class VolleyRequest extends StringRequest {

    private static final String GENERATE_OTP_URL = "http://dashboard108.herokuapp.com/otp/gen?";
    private static final String CHECK_OTP_URL = "http://dashboard108.herokuapp.com/otp/check?";
    private static final String CHECK_API_URL = "http://dashboard108.herokuapp.com/api/request?";
    private Map<String, String> params;
    private String TAG = "Volley";

    public VolleyRequest(String mobile, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Request.Method.GET, GENERATE_OTP_URL + "mobile=" + mobile, listener, errorListener);
    }

    public VolleyRequest(String mobile, String otp, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Request.Method.GET, CHECK_OTP_URL + "mobile=" + mobile + "&otp=" + otp, listener, errorListener);
    }

    public VolleyRequest(String mobile, String lat, String lng,Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Request.Method.GET, CHECK_API_URL + "mobile=" + mobile + "&lat=" + lat + "&lon=" + lng, listener, errorListener);
    }

}


