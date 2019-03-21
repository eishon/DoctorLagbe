package com.death_slash.eishon.daclient.activity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.death_slash.eishon.daclient.adapter.DoctorInfoListener;
import com.death_slash.eishon.daclient.adapter.DoctorListAdapter;
import com.death_slash.eishon.daclient.adapter.SetAppointmentListener;
import com.death_slash.eishon.daclient.domain.Doctor;
import com.death_slash.eishon.daclient.domain.Patient;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PatientActivity extends AppCompatActivity implements DoctorInfoListener, SetAppointmentListener {

    Button searchBloodBtn, searchDoctorBtn;
    Button editPatientInfoBtn, checkAppointmentBtn;
    DoctorListAdapter doctorListAdapter;
    RecyclerView doctorListRecyclerView;
    LinearLayoutManager mLayoutManager;
    Spinner districtSpinner, hospitalSpinner, specialitySpinner;

    List<Doctor> doctors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        searchBloodBtn = findViewById(R.id.bloodgroup_p_search_button);
        searchDoctorBtn = findViewById(R.id.search_doctor_button);
        editPatientInfoBtn = findViewById(R.id.edit_patient_info_button);
        checkAppointmentBtn = findViewById(R.id.check_appointments_btn);
        doctorListRecyclerView = findViewById(R.id.doctor_list_recyclerview);
        mLayoutManager = new LinearLayoutManager(PatientActivity.this);
        doctorListRecyclerView.setLayoutManager(mLayoutManager);
        districtSpinner = findViewById(R.id.district_s_spinner);
        hospitalSpinner = findViewById(R.id.hospital_s_spinner);
        specialitySpinner = findViewById(R.id.speciality_s_spinner);

        ArrayAdapter<CharSequence> districtSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.district_array_all, android.R.layout.simple_spinner_item);
        districtSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        districtSpinner.setAdapter(districtSpinnerAdapter);

        ArrayAdapter<CharSequence> hospitalSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.hospital_array_all, android.R.layout.simple_spinner_item);
        hospitalSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hospitalSpinner.setAdapter(hospitalSpinnerAdapter);

        ArrayAdapter<CharSequence> specialitySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.speciality_array_all, android.R.layout.simple_spinner_item);
        specialitySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specialitySpinner.setAdapter(specialitySpinnerAdapter);

        setupRecyclerViewWithAdapter();

        searchBloodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientActivity.this, BloodSearchActivity.class);
                startActivity(intent);
            }
        });

        searchDoctorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getDoctorsRequest(DoctorLagbeManager.getAPIToken());
                getDoctorsRequest(
                        DoctorLagbeManager.getAPIToken(),
                        districtSpinner.getSelectedItem().toString(),
                        hospitalSpinner.getSelectedItem().toString(),
                        specialitySpinner.getSelectedItem().toString());
            }
        });

        editPatientInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientActivity.this, UpdateActivity.class);
                startActivity(intent);
            }
        });

        checkAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientActivity.this, PatientAppointmentActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupRecyclerViewWithAdapter() {
        doctorListAdapter = new DoctorListAdapter(doctors);
        doctorListAdapter.doctorInfoListener = this;
        doctorListAdapter.setAppointmentListener = this;
        doctorListRecyclerView.setAdapter(doctorListAdapter);
    }

    private void getDoctorsRequest(String token) {
        final RequestQueue request = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                DoctorLagbeManager.getAllDoctorInfoURL(token),
                null,

                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = null;
                        List<Doctor> d = new ArrayList<>();
                        try {
                            jsonArray = response.getJSONArray("all");
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                d.add(new Gson().fromJson(jsonObject.toString(), Doctor.class));
                                //oast.makeText(getApplicationContext(),"" +jsonObject.toString(), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        doctors = d;
                        setupRecyclerViewWithAdapter();
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
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        request.add(jsonObjectRequest);
    }

    private void getDoctorsRequest(String token, String district, String hospital, String speciality) {
        final RequestQueue request = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                DoctorLagbeManager.getAllDoctorInfoSearchURL(token, district, hospital, speciality),
                null,

                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = null;
                        List<Doctor> d = new ArrayList<>();
                        try {
                            jsonArray = response.getJSONArray("all");
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                d.add(new Gson().fromJson(jsonObject.toString(), Doctor.class));
                                //Toast.makeText(getApplicationContext(),"" +jsonObject.toString(), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        doctors = d;
                        setupRecyclerViewWithAdapter();
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
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        request.add(jsonObjectRequest);
    }

    @Override
    public void getDoctorInfo(String doctorId) {
        final RequestQueue request = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                DoctorLagbeManager.getDoctorInfoURL_2(DoctorLagbeManager.getAPIToken(), doctorId),
                null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(),"" +response.toString(), Toast.LENGTH_LONG).show();
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
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        request.add(jsonObjectRequest);
    }

    @Override
    public void setAppointment(String doctorId) {
        final RequestQueue request = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                DoctorLagbeManager.setAppointmentsURL(DoctorLagbeManager.getAPIToken(), doctorId),

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),""+response, Toast.LENGTH_LONG).show();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Error: " +error.toString(), Toast.LENGTH_LONG).show();
                        Log.e("MESSAGE", error.toString());
                    }
                }
        );

        request.add(stringRequest);
    }
}
