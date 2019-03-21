package com.death_slash.eishon.daclient.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.death_slash.eishon.daclient.DoctorLagbeManager;
import com.death_slash.eishon.daclient.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    Button registerUserBtn;
    CheckBox isDoctorChk;
    Spinner sexSpinner, bloodSpinner, districtSpinner, hospitalSpinner, specialitySpinner;

    EditText userNameEdTxt, mobileEdTxt, mobile2EdTxt, bdmcIdEdTxt, passwordEdTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerUserBtn = findViewById(R.id.registerBtn);
        isDoctorChk = findViewById(R.id.isDoctorRegChkBx);

        userNameEdTxt = findViewById(R.id.nameRegEdTxt);
        mobileEdTxt = findViewById(R.id.mobileRegEdTxt);
        mobile2EdTxt  = findViewById(R.id.mobile4ARegEdTxt);
        specialitySpinner = findViewById(R.id.specialityRegSpinner);
        bdmcIdEdTxt = findViewById(R.id.bdmcIdRegEdTxt);
        districtSpinner = findViewById(R.id.wDistrictRegSpinner);
        hospitalSpinner = findViewById(R.id.wHospitalRegSpinner);
        passwordEdTxt = findViewById(R.id.passWordRegEdTxt);
        sexSpinner = findViewById(R.id.sexRegSpinner);
        bloodSpinner = findViewById(R.id.bloodRegSpinner);

        ArrayAdapter<CharSequence> sexSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.sex_array, android.R.layout.simple_spinner_item);
        sexSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner.setAdapter(sexSpinnerAdapter);

        ArrayAdapter<CharSequence> bloodSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.bloodgroup_array, android.R.layout.simple_spinner_item);
        bloodSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodSpinner.setAdapter(bloodSpinnerAdapter);

        ArrayAdapter<CharSequence> districtSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.district_array, android.R.layout.simple_spinner_item);
        districtSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        districtSpinner.setAdapter(districtSpinnerAdapter);

        ArrayAdapter<CharSequence> hospitalSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.hospital_array, android.R.layout.simple_spinner_item);
        hospitalSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hospitalSpinner.setAdapter(hospitalSpinnerAdapter);

        ArrayAdapter<CharSequence> specialitySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.speciality_array, android.R.layout.simple_spinner_item);
        specialitySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specialitySpinner.setAdapter(specialitySpinnerAdapter);

        isDoctorChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoctorLagbeManager.newUserIsDoctor = isDoctorChk.isChecked();

                if(isDoctorChk.isChecked()){

                    userNameEdTxt.setEnabled(true);
                    mobileEdTxt.setEnabled(true);
                    mobile2EdTxt.setEnabled(true);
                    specialitySpinner.setEnabled(true);
                    bdmcIdEdTxt.setEnabled(true);
                    districtSpinner.setEnabled(true);
                    hospitalSpinner.setEnabled(true);
                    passwordEdTxt.setEnabled(true);
                    sexSpinner.setEnabled(true);
                    bloodSpinner.setEnabled(true);

                    //specialityEdTxt.setHint("Speciality");
                    bdmcIdEdTxt.setHint("BDMCId");
                    //hospitalEdTxt.setHint("Working Hospital");
                    //districtEdTxt.setHint("Working District");
                    mobile2EdTxt.setHint("Mobile No. for Appointment");
                }else{
                    userNameEdTxt.setEnabled(true);
                    mobileEdTxt.setEnabled(true);
                    mobile2EdTxt.setEnabled(true);
                    specialitySpinner.setEnabled(false);
                    bdmcIdEdTxt.setEnabled(false);
                    districtSpinner.setEnabled(true);
                    hospitalSpinner.setEnabled(false);
                    passwordEdTxt.setEnabled(true);
                    sexSpinner.setEnabled(true);
                    bloodSpinner.setEnabled(true);

                    //specialityEdTxt.setHint("");
                    bdmcIdEdTxt.setHint("");
                    //hospitalEdTxt.setHint("");
                    //districtEdTxt.setHint("District");
                    mobile2EdTxt.setHint("Age");
                }
            }
        });

        registerUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObj = getJSONObject();
                registerRequest(passwordEdTxt.getText().toString(), jsonObj);
            }
        });

    }

    private void registerRequest(String pass, final JSONObject jsonObject) {
        RequestQueue request = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                (DoctorLagbeManager.newUserIsDoctor)
                        ?DoctorLagbeManager.registerDoctorURL(pass)
                        :DoctorLagbeManager.registerPatientURL(pass),

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("MESSAGE", response);
                        Toast.makeText(getApplicationContext(),"" +response, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Error: " +error.toString(), Toast.LENGTH_LONG).show();
                        Log.e("MESSAGE", error.toString());
                    }
                })
        {
            @Override
            public byte[] getBody() {
                String temp = jsonObject.toString();
                return temp.getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        request.add(stringRequest);
    }

    private JSONObject getJSONObject(){
        JSONObject jsonObject = new JSONObject();
        try{
            if(DoctorLagbeManager.newUserIsDoctor) {
                jsonObject.put("mobile", mobileEdTxt.getText().toString());
                jsonObject.put("name", userNameEdTxt.getText().toString());
                jsonObject.put("sex", sexSpinner.getSelectedItem().toString());
                jsonObject.put("bloodGroup", bloodSpinner.getSelectedItem().toString());
                jsonObject.put("speciality", specialitySpinner.getSelectedItem().toString());
                jsonObject.put("wDistrict", districtSpinner.getSelectedItem().toString());
                jsonObject.put("wHospital", hospitalSpinner.getSelectedItem().toString());
                jsonObject.put("mobile4A", mobile2EdTxt.getText().toString());
                jsonObject.put("bdmcid", bdmcIdEdTxt.getText().toString());
            }else {
                jsonObject.put("mobile", mobileEdTxt.getText().toString());
                jsonObject.put("name", userNameEdTxt.getText().toString());
                jsonObject.put("sex", sexSpinner.getSelectedItem().toString());
                jsonObject.put("age", mobile2EdTxt.getText().toString());
                jsonObject.put("bloodGroup", bloodSpinner.getSelectedItem().toString());
                jsonObject.put("pDistrict", districtSpinner.getSelectedItem().toString());
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        //Toast.makeText(getApplicationContext(),""+jsonObject.toString(),Toast.LENGTH_LONG).show();
        return jsonObject;
    }

}
