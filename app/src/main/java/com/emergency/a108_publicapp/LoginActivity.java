package com.emergency.a108_publicapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;

/**
 * Created by rahul on 25/11/16.
 */
public class LoginActivity extends AppCompatActivity implements VerticalStepperForm{
    private String TAG = "loginactivity";
    private VerticalStepperFormLayout verticalStepperForm;
    private EditText name;
    private EditText age;
    private EditText phone;
    private Button button;
    private Button buttonOTP;
    private RadioGroup gender;
    private EditText otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Dexter.initialize(this);

        String[] mySteps = {"Name", "Age", "Gender", "Phone Number", "OTP", "Permissions"};
        int colorPrimary = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
        int colorPrimaryDark = ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);

        // Finding the view
        verticalStepperForm = (VerticalStepperFormLayout) findViewById(R.id.vertical_stepper_form);

        // Setting up and initializing the form
        VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm, mySteps, this, this)
                .primaryColor(colorPrimary)
                .primaryDarkColor(colorPrimaryDark)
                .displayBottomNavigation(true) // It is true by default, so in this case this line is not necessary
                .init();


    }

    @Override
    public View createStepContentView(int stepNumber) {
        View view = null;
        switch (stepNumber) {
            case 0:
                view = createNameStep();
                break;
            case 1:
                view = createAgeStep();
                break;
            case 2:
                view = createGenderStep();
                break;
            case 3:
                view = createPhoneNumberStep();
                break;
            case 4:
                view = createOTPStep();
                break;
            case 5:
                view = createPermissionStep();
                break;
        }
        return view;
    }

    private View createNameStep() {
        // Here we generate programmatically the view that will be added by the system to the step content layout
        name = new EditText(this);
        name.setSingleLine(true);
        name.setHint("Your name");
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(checkName(v.getText().toString())) {
                    verticalStepperForm.goToNextStep();
                }
                return false;
            }
        });
        return name;
    }

    private View createAgeStep() {
        // In this case we generate the view by inflating a XML file
        age = new EditText(this);
        age.setSingleLine(true);
        age.setInputType(InputType.TYPE_CLASS_NUMBER);
        age.setHint("Your age");
        age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkAge(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        age.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(checkAge(v.getText().toString())) {
                    verticalStepperForm.goToNextStep();
                }
                return false;
            }
        });
        return age;
    }

    private View createGenderStep() {
        // In this case we generate the view by inflating a XML file
        gender=new RadioGroup(this);
        RadioButton male= new RadioButton(this);
        male.setText("Male");
        RadioButton female= new RadioButton(this);
        female.setText("Female");
        RadioButton trans= new RadioButton(this);
        trans.setText("Transgender");
        gender.addView(male);
        gender.addView(female);
        gender.addView(trans);
        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkGender();
            }
        });
        return gender;
    }

    private View createPhoneNumberStep() {
        phone = new EditText(this);
        phone.setSingleLine(true);
        phone.setInputType(InputType.TYPE_CLASS_PHONE);
        phone.setHint("Your Phone Number");
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkPhone(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        phone.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(checkPhone(v.getText().toString())) {
                    verticalStepperForm.goToNextStep();
                }
                return false;
            }
        });
        return phone;
    }
    private View createOTPStep() {
        LinearLayout view = new LinearLayout(this);
        otp = new EditText(this);
        otp.setSingleLine(true);
        otp.setInputType(InputType.TYPE_CLASS_PHONE);
        otp.setHint("OTP");
        otp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkOTP(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        otp.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(checkOTP(v.getText().toString())) {
                    verticalStepperForm.goToNextStep();
                }
                return false;
            }
        });
        buttonOTP = new Button(this);
        buttonOTP.setText("Submit");
        buttonOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOTPOnline();
            }
        });
        view.addView(otp);
        view.addView(buttonOTP);
        return view;
    }

    private View createPermissionStep() {
        LinearLayout view = new LinearLayout(this);
        button = new Button(this);
        button.setText("Give Permissions");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
            }
        });
        TextView textView = new TextView(this);
        textView.setText("Allow required Permissions");
        textView.setLines(2);
        view.setOrientation(LinearLayout.HORIZONTAL);
        view.addView(button);
        view.addView(textView);
        return view;
    }

    @Override
    public void onStepOpening(int stepNumber) {
        switch (stepNumber) {
            case 0:
                checkName(name.getText().toString());
                break;
            case 1:
                checkAge(age.getText().toString());
                break;
            case 2:
                checkGender();
                break;
            case 3:
                checkPhone(phone.getText().toString());
                // As soon as the phone number step is open, we mark it as completed in order to show the "Continue"
                // button (We do it because this field is optional, so the user can skip it without giving any info)
//                verticalStepperForm.setStepAsCompleted(2);
                // In this case, the instruction above is equivalent to:
                // verticalStepperForm.setActiveStepAsCompleted();
                break;
            case 4:
                checkOTP(otp.getText().toString());
                // As soon as the phone number step is open, we mark it as completed in order to show the "Continue"
                // button (We do it because this field is optional, so the user can skip it without giving any info)
//                verticalStepperForm.setStepAsCompleted(2);
                // In this case, the instruction above is equivalent to:
                // verticalStepperForm.setActiveStepAsCompleted();
                break;
            case 5:
                if(!checkPermissions()){
                    requestPermissions();
                }
                break;
        }
    }

    private boolean checkName(String name) {
        if(name.length() >= 3 && name.length() <= 40) {
            verticalStepperForm.setActiveStepAsCompleted();
            return true;
        } else {
            // This error message is optional (use null if you don't want to display an error message)
            String errorMessage = "The name must have between 3 and 40 characters";
            verticalStepperForm.setActiveStepAsUncompleted(errorMessage);
            return false;
        }
    }

    private boolean checkAge(String age) {
        int ageInt =0;
        if(age!=null&&(!age.equals(""))){
            ageInt=Integer.parseInt(age);
        }
        if(ageInt>0&&ageInt<139) {
            verticalStepperForm.setActiveStepAsCompleted();
            return true;
        } else {
            // This error message is optional (use null if you don't want to display an error message)
            String errorMessage = "The age must have between 1 and 138";
            verticalStepperForm.setActiveStepAsUncompleted(errorMessage);
            return false;
        }
    }

    private boolean checkPhone(String phone) {
        if(phone.length()== 10) {
            verticalStepperForm.setActiveStepAsCompleted();
            sendRequestOtp(phone);
            return true;
        } else {
            // This error message is optional (use null if you don't want to display an error message)
            String errorMessage = "The Phone Number must be a 10 digit number";
            verticalStepperForm.setActiveStepAsUncompleted(errorMessage);
            return false;
        }
    }

    private boolean checkGender() {
        if(gender.getCheckedRadioButtonId()!=-1) {
            verticalStepperForm.setActiveStepAsCompleted();
            return true;
        } else {
            // This error message is optional (use null if you don't want to display an error message)
            String errorMessage = "Please Select Gender";
            verticalStepperForm.setActiveStepAsUncompleted(errorMessage);
            return false;
        }
    }

    private boolean checkOTP(String otp) {
        if(otp.length()== 4) {
            //verticalStepperForm.setActiveStepAsCompleted();
            String errorMessage = "Submit OTP";
            verticalStepperForm.setActiveStepAsUncompleted(errorMessage);
            buttonOTP.setEnabled(true);
            return true;
        } else {
            // This error message is optional (use null if you don't want to display an error message)
            buttonOTP.setEnabled(false);
            String errorMessage = "The OTP must be a 4 digit number";
            verticalStepperForm.setActiveStepAsUncompleted(errorMessage);
            return false;
        }
    }

    private void checkOTPOnline() {
        sendCheckOtp(phone.getText().toString(),otp.getText().toString());
    }


    @Override
    public void sendData() {
        String phoneNumber = phone.getText().toString();
        sendDataToFirebase("users/"+phoneNumber+"/name",name.getText().toString());
        sendDataToFirebase("users/"+phoneNumber+"/age",age.getText().toString());
        sendDataToFirebase("users/"+phoneNumber+"/gender",((RadioButton)findViewById(gender.getCheckedRadioButtonId())).getText().toString());

        SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.shared_preference_name), MODE_PRIVATE).edit();
        editor.putBoolean(getString(R.string.shared_preference_login),true);
        editor.putString("mobile",phoneNumber);
        editor.commit();

        Intent intentHomeActivity = new Intent(this,HomeActivity.class);
        startActivity(intentHomeActivity);
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public boolean checkPermissions(){
        // Here, thisActivity is the current activity
        if( (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED)||(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)||(ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED)||(ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED)||(ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED)||(ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED)  ){

            Log.d(TAG, "myCheckPermission() if called with: " + "thisActivity = [" + this + "]");


            String errorMessage = "You need to allow all permissions to continue";
            verticalStepperForm.setActiveStepAsUncompleted(errorMessage);
            return false;

        }
        else{
            button.setEnabled(false);
            verticalStepperForm.setActiveStepAsCompleted();

            return true;
        }
    }
    private static final  int MY_PERMISSIONS_REQUEST_CODE =101;

    private void requestPermissions(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET,Manifest.permission.SEND_SMS,Manifest.permission.RECEIVE_SMS,Manifest.permission.CALL_PHONE},
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
                    button.setEnabled(false);
                    verticalStepperForm.setActiveStepAsCompleted();

                } else {
                    String errorMessage = "You need to allow all permissions to continue";
                    verticalStepperForm.setActiveStepAsUncompleted(errorMessage);
                }
            }
        }
    }

    private void sendDataToFirebase(String location,String value){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(location);
        myRef.setValue(value);
    }

    private void sendRequestOtp(final String phoneNumber){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("subscription", response);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        };
        Log.d(TAG, "onResponse() called with: phoneNumer =[" + phoneNumber + "]");
        VolleyRequest subscriptionRequest = new VolleyRequest(phoneNumber, responseListener, errorListener);
        subscriptionRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 5, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(subscriptionRequest);
    }

    private void sendCheckOtp(String phoneNumber,String otpNumber){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("subscription", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.has("check")){
                        if(jsonObject.getString("check").equals("true")){
                            buttonOTP.setEnabled(false);
                            verticalStepperForm.setActiveStepAsCompleted();
                        }
                        else {
                            String  errorMessage= "Invalid OTP";
                            verticalStepperForm.setActiveStepAsUncompleted(errorMessage);
                        }
                    }
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
        VolleyRequest subscriptionRequest = new VolleyRequest(phoneNumber, otpNumber, responseListener, errorListener);
        subscriptionRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 5, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(subscriptionRequest);
    }


}

