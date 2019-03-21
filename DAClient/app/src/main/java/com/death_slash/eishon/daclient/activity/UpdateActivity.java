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
import com.death_slash.eishon.daclient.domain.Doctor;
import com.death_slash.eishon.daclient.domain.Patient;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateActivity extends AppCompatActivity {

    Button updateUserBtn;
    Spinner sexSpinner, bloodSpinner, districtSpinner, hospitalSpinner, specialitySpinner;

    EditText userNameEdTxt, mobileEdTxt, mobile2EdTxt, doctorIdEdTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        updateUserBtn = findViewById(R.id.updateBtn);

        userNameEdTxt = findViewById(R.id.nameUDEdTxt);
        mobileEdTxt = findViewById(R.id.mobileUDEdTxt);
        mobile2EdTxt  = findViewById(R.id.mobile4AEdUDTxt);
        specialitySpinner = findViewById(R.id.specialityUDSpinner);
        doctorIdEdTxt = findViewById(R.id.doctorIdUDEdTxt);
        districtSpinner = findViewById(R.id.wDistrictUDSpinner);
        hospitalSpinner = findViewById(R.id.wHospitalUDSpinner);
        sexSpinner = findViewById(R.id.sexUDSpinner);
        bloodSpinner = findViewById(R.id.bloodUDSpinner);

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

        if(DoctorLagbeManager.userIsDoctor){
            userNameEdTxt.setEnabled(true);
            mobileEdTxt.setEnabled(false);
            mobile2EdTxt.setEnabled(true);
            specialitySpinner.setEnabled(true);
            doctorIdEdTxt.setEnabled(true);
            districtSpinner.setEnabled(true);
            hospitalSpinner.setEnabled(true);
            sexSpinner.setEnabled(true);
            bloodSpinner.setEnabled(true);

            Doctor doctor = DoctorLagbeManager.getDoctor();
            userNameEdTxt.setText(doctor.getName());
            mobileEdTxt.setText(doctor.getMobile());
            mobile2EdTxt.setText(doctor.getMobile4A());
            doctorIdEdTxt.setText(doctor.getBDMCId());
            specialitySpinner.setSelection(getSpinnerSelectionVal(specialitySpinner, doctor.getSpeciality()));
            districtSpinner.setSelection(getSpinnerSelectionVal(districtSpinner, doctor.getwDistrict()));
            hospitalSpinner.setSelection(getSpinnerSelectionVal(hospitalSpinner, doctor.getwHospital()));
            sexSpinner.setSelection(getSpinnerSelectionVal(sexSpinner, doctor.getSex()));
            bloodSpinner.setSelection(getSpinnerSelectionVal(bloodSpinner, doctor.getBloodGroup()));

        }else{
            userNameEdTxt.setEnabled(true);
            mobileEdTxt.setEnabled(false);
            mobile2EdTxt.setEnabled(true);
            specialitySpinner.setEnabled(false);
            doctorIdEdTxt.setEnabled(false);
            districtSpinner.setEnabled(true);
            hospitalSpinner.setEnabled(false);
            sexSpinner.setEnabled(true);
            bloodSpinner.setEnabled(true);

            mobile2EdTxt.setHint("Age");

            Patient patient = DoctorLagbeManager.getPatient();
            userNameEdTxt.setText(patient.getName());
            mobileEdTxt.setText(patient.getMobile());
            mobile2EdTxt.setText(patient.getAge());
            districtSpinner.setSelection(getSpinnerSelectionVal(districtSpinner, patient.getpDistrict()));
            sexSpinner.setSelection(getSpinnerSelectionVal(sexSpinner, patient.getSex()));
            bloodSpinner.setSelection(getSpinnerSelectionVal(bloodSpinner, patient.getBloodGroup()));
        }

        updateUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObj = getJSONObject();
                updateRequest(DoctorLagbeManager.getAPIToken(), jsonObj);
            }
        });
    }

    private void updateRequest(String token, final JSONObject jsonObject) {
        RequestQueue request = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                (DoctorLagbeManager.userIsDoctor)
                        ?DoctorLagbeManager.editDoctorInfoURL(token)
                        :DoctorLagbeManager.editPatientInfoURL(token),

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("MESSAGE", response);
                        Toast.makeText(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();
                        Intent intent;
                        if(DoctorLagbeManager.userIsDoctor) {
                            DoctorLagbeManager.setDoctor(new Gson().fromJson(jsonObject.toString(), Doctor.class));
                            intent = new Intent(UpdateActivity.this, DoctorActivity.class);
                        }else {
                            DoctorLagbeManager.setPatient(new Gson().fromJson(jsonObject.toString(), Patient.class));
                            intent = new Intent(UpdateActivity.this, PatientActivity.class);
                        }

                        startActivity(intent);
                        finish();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),""+error.toString(),Toast.LENGTH_LONG).show();
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
            if(DoctorLagbeManager.userIsDoctor) {
                jsonObject.put("mobile", mobileEdTxt.getText().toString());
                jsonObject.put("name", userNameEdTxt.getText().toString());
                jsonObject.put("sex", sexSpinner.getSelectedItem().toString());
                jsonObject.put("bloodGroup", bloodSpinner.getSelectedItem().toString());
                jsonObject.put("speciality", specialitySpinner.getSelectedItem().toString());
                jsonObject.put("wDistrict", districtSpinner.getSelectedItem().toString());
                jsonObject.put("wHospital", hospitalSpinner.getSelectedItem().toString());
                jsonObject.put("mobile4A", mobile2EdTxt.getText().toString());
                jsonObject.put("bdmcid", doctorIdEdTxt.getText().toString());
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

    private int getSpinnerSelectionVal(Spinner spinner, String val){
        int n = 0;

        for(int i=0; i < spinner.getAdapter().getCount(); i++){
            if(spinner.getItemAtPosition(i).toString().equals(val)){
                n = i;
                break;
            }
        }

        return n;
    }
}
