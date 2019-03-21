package com.death_slash.eishon.daclient.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.death_slash.eishon.daclient.domain.Doctor;
import com.death_slash.eishon.daclient.domain.Patient;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText baseURLEdTxt;
    EditText loginIdEdTxt;
    EditText loginPassEdTxt;
    Button submitBtn;
    Button registerActv;
    CheckBox doctorChck;

    String id, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DoctorLagbeManager.setContext(getApplicationContext());

        baseURLEdTxt = findViewById(R.id.baseURLEditText);
        loginIdEdTxt = findViewById(R.id.loginIdEditText);
        loginPassEdTxt = findViewById(R.id.loginPassIdEditText);
        submitBtn = findViewById(R.id.setBaseURLBtn);
        registerActv = findViewById(R.id.registerAcvtBtn);
        doctorChck = findViewById(R.id.doctorChkBx);
        doctorChck.setChecked(true);

        doctorChck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoctorLagbeManager.userIsDoctor = doctorChck.isChecked();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoctorLagbeManager.setBaseUrl(baseURLEdTxt.getText().toString());

                id = loginIdEdTxt.getText().toString();
                pass = loginPassEdTxt.getText().toString();

                loginRequest(id, pass);

            }
        });

        registerActv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoctorLagbeManager.setBaseUrl(baseURLEdTxt.getText().toString());

                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /*
        String url = "http://10.0.2.2:8080/doctors";
        String url1 = "http://192.168.42.103:8080/topics/123";
        String url2 = "https://jsonplaceholder.typicode.com/todos/1";
        String url3 ="http://www.google.com";
        */

    }

    private void loginRequest(final String id, final String pass){
        RequestQueue request = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                (DoctorLagbeManager.userIsDoctor)
                        ?DoctorLagbeManager.loginDoctorURL()
                        :DoctorLagbeManager.loginPatientURL(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        DoctorLagbeManager.setAPIToken(response);
                        //Toast.makeText(context, ""+response, Toast.LENGTH_LONG).show();
                        if (DoctorLagbeManager.userIsDoctor)
                            getDoctorInfoRequest();
                        else
                            getPatientInfoRequest();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            public byte[] getBody() {
                Map<String,String> params = new HashMap<String, String>();
                params.put("id", id);
                params.put("password",pass);

                String temp = new Gson().toJson(params);

                return temp.getBytes();
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };

        request.add(stringRequest);
    }

    private void getDoctorInfoRequest(){
        RequestQueue request = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                DoctorLagbeManager.getDoctorInfoURL(DoctorLagbeManager.getAPIToken()),
                null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        DoctorLagbeManager.setDoctor(new Gson().fromJson(response.toString(), Doctor.class));
                        //Toast.makeText(context,""+getDoctor().getBloodGroup(),Toast.LENGTH_LONG).show();
                        Log.e("MESSAGE", response.toString());

                        if(DoctorLagbeManager.getDoctor() != null) {
                            Intent intent = new Intent(MainActivity.this, DoctorActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("MESSAGE", error.toString());
                    }
                }
        );

        request.add(jsonObjectRequest);
    }

    private void getPatientInfoRequest(){
        RequestQueue request = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                DoctorLagbeManager.getPatientInfoURL(DoctorLagbeManager.getAPIToken()),
                null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        DoctorLagbeManager.setPatient(new Gson().fromJson(response.toString(), Patient.class));
                        //Toast.makeText(getApplicationContext(),""+DoctorLagbeManager.getPatient().getBloodGroup(),Toast.LENGTH_LONG).show();
                        Log.e("MESSAGE", response.toString());

                        if(DoctorLagbeManager.getPatient() != null) {
                            Intent intent = new Intent(MainActivity.this, PatientActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("MESSAGE", error.toString());
                    }
                }
        );

        request.add(jsonObjectRequest);
    }


}
