package com.death_slash.eishon.daclient.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
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
import com.death_slash.eishon.daclient.adapter.RejecttAppoinmentListener;
import com.death_slash.eishon.daclient.domain.Appointment;
import com.death_slash.eishon.daclient.domain.Doctor;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DoctorActivity extends AppCompatActivity implements AcceptAppoinmentListener, RejecttAppoinmentListener {

    EditText messageEditText, messageRejectEditText;
    Button searchBloodBtn;
    Button editDoctorInfoBtn;
    RecyclerView appointmentListRecyclerView;
    LinearLayoutManager mLayoutManager;

    List<Appointment> appointments;
    AppointmentListAdapter appointmentListAdapter;

    AlertDialog acceptDialog, rejectDialog;

    String a_r_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        AlertDialog.Builder accept = new AlertDialog.Builder(DoctorActivity.this);
        AlertDialog.Builder reject = new AlertDialog.Builder(DoctorActivity.this);

        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_alert, null);
        LayoutInflater rInflater = this.getLayoutInflater();
        View rView = rInflater.inflate(R.layout.layout_alert_reject, null);

        messageEditText = view.findViewById(R.id.alert_message_textview);
        messageRejectEditText = rView.findViewById(R.id.alert_reject_textview);

        accept.setTitle("Message")
                .setCancelable(false)
                .setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        acceptAppointment(messageEditText.getText());
                        requestAppointmentList();
                    }
                });

        acceptDialog = accept.create();

        reject.setTitle("Message")
                .setCancelable(false)
                .setView(rView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        rejectAppointment(messageRejectEditText.getText());
                        requestAppointmentList();
                    }
                });

        rejectDialog = reject.create();

        searchBloodBtn = findViewById(R.id.bloodgroup_d_search_button);
        editDoctorInfoBtn = findViewById(R.id.edit_doctor_info_button);
        appointmentListRecyclerView = findViewById(R.id.appointment_list_recyclerview);
        mLayoutManager = new LinearLayoutManager(DoctorActivity.this);
        appointmentListRecyclerView.setLayoutManager(mLayoutManager);

        searchBloodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorActivity.this, BloodSearchActivity.class);
                startActivity(intent);
            }
        });

        editDoctorInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorActivity.this, UpdateActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestAppointmentList();
    }

    private void setupRecyclerViewWithAdapter(){
        appointmentListAdapter = new AppointmentListAdapter(appointments);
        appointmentListAdapter.acceptAppoinmentListener = this;
        appointmentListAdapter.rejectAppoinmentListener = this;
        appointmentListRecyclerView.setAdapter(appointmentListAdapter);
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

    private void acceptAppointment(Editable msg){
        final RequestQueue request = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                DoctorLagbeManager.acceptAppointmentsURL(
                        DoctorLagbeManager.getAPIToken(),
                        a_r_id, msg.toString()),

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),""+response, Toast.LENGTH_LONG).show();
                        requestAppointmentList();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Error: " +error.toString(), Toast.LENGTH_LONG).show();
                        Log.e("MESSAGE", error.toString());
                        requestAppointmentList();
                    }
                }
        );

        request.add(stringRequest);
    }

    private void rejectAppointment(Editable msg){
        final RequestQueue request = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                DoctorLagbeManager.rejectAppointmentsURL(
                        DoctorLagbeManager.getAPIToken(),
                        a_r_id, msg.toString()),

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),""+response, Toast.LENGTH_LONG).show();
                        requestAppointmentList();
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Error: " +error.toString(), Toast.LENGTH_LONG).show();
                        Log.e("MESSAGE", error.toString());
                        requestAppointmentList();
                    }
                }
        );

        request.add(stringRequest);
    }

    @Override
    public void acceptAppointment(String id) {
        a_r_id = id;
        acceptDialog.show();
    }

    @Override
    public void rejectAppointment(String id) {
        a_r_id = id;
        rejectDialog.show();
    }
}
