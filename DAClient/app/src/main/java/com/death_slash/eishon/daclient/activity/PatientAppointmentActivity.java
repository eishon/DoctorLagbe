package com.death_slash.eishon.daclient.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.death_slash.eishon.daclient.adapter.AcceptAppoinmentListener;
import com.death_slash.eishon.daclient.adapter.AppointmentListAdapter;
import com.death_slash.eishon.daclient.adapter.PAppointmentListAdapter;
import com.death_slash.eishon.daclient.adapter.RejecttAppoinmentListener;
import com.death_slash.eishon.daclient.domain.Appointment;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PatientAppointmentActivity extends AppCompatActivity {

    RecyclerView pAppointmentListRecyclerView;
    LinearLayoutManager mLayoutManager;

    List<Appointment> appointments;
    PAppointmentListAdapter pAppointmentListAdapter;

    String a_r_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_appointment);

        pAppointmentListRecyclerView = findViewById(R.id.patient_appointment_recyclerview);
        mLayoutManager = new LinearLayoutManager(PatientAppointmentActivity.this);
        pAppointmentListRecyclerView.setLayoutManager(mLayoutManager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        requestAppointmentList();
    }

    private void setupRecyclerViewWithAdapter(){
        pAppointmentListAdapter = new PAppointmentListAdapter(appointments);
        pAppointmentListRecyclerView.setAdapter(pAppointmentListAdapter);
    }

    private void requestAppointmentList(){
        final RequestQueue request = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                DoctorLagbeManager.getAppointmentsURL(DoctorLagbeManager.getAPIToken()),
                null,

                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = null;
                        List<Appointment> a = new ArrayList<>();
                        try {
                            jsonArray = response.getJSONArray("all");
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                a.add(new Gson().fromJson(jsonObject.toString(), Appointment.class));
                                //Toast.makeText(getApplicationContext(),"" +jsonObject.toString(), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        appointments = a;
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
}
