package com.death_slash.eishon.daclient.activity;

import android.content.Intent;
import android.net.Uri;
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
import com.android.volley.toolbox.Volley;
import com.death_slash.eishon.daclient.DoctorLagbeManager;
import com.death_slash.eishon.daclient.R;
import com.death_slash.eishon.daclient.adapter.BloodSearchListAdapter;
import com.death_slash.eishon.daclient.adapter.CallDonorListener;
import com.death_slash.eishon.daclient.domain.Appointment;
import com.death_slash.eishon.daclient.domain.BloodSearch;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BloodSearchActivity extends AppCompatActivity implements CallDonorListener {

    RecyclerView searchBloodRecyclerView;
    Button searchBloodBtn;
    Spinner bloodSpinner;

    LinearLayoutManager mLayoutManager;
    BloodSearchListAdapter bloodSearchListAdapter;
    List<BloodSearch> bloodSearches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_search);

        searchBloodRecyclerView = findViewById(R.id.bloodgroup_list_recyclerview);
        searchBloodBtn = findViewById(R.id.search_bloodgroup_button);
        bloodSpinner = findViewById(R.id.search_bloodgroup_spinner);

        mLayoutManager = new LinearLayoutManager(BloodSearchActivity.this);
        searchBloodRecyclerView.setLayoutManager(mLayoutManager);

        ArrayAdapter<CharSequence> bloodSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.bloodgroup_array, android.R.layout.simple_spinner_item);
        bloodSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodSpinner.setAdapter(bloodSpinnerAdapter);

        searchBloodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBloodDonors(bloodSpinner.getSelectedItem().toString());
            }
        });

    }

    private void setupRecyclerViewWithAdapter(){
        bloodSearchListAdapter = new BloodSearchListAdapter(bloodSearches);
        bloodSearchListAdapter.callDonorListener = this;
        searchBloodRecyclerView.setAdapter(bloodSearchListAdapter);
    }

    private void getBloodDonors(String bloodGroup){
        final RequestQueue request = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                DoctorLagbeManager.getBloodGroup(bloodGroup),
                null,

                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = null;
                        List<BloodSearch> b = new ArrayList<>();
                        try {
                            jsonArray = response.getJSONArray("all");
                            for(int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                                b.add(new Gson().fromJson(jsonObject.toString(), BloodSearch.class));
                                //Toast.makeText(getApplicationContext(),"" +jsonObject.toString(), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        bloodSearches = b;
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
    public void makeCall(String mobile) {
        Intent intent = new Intent(Intent.ACTION_CALL);

        intent.setData(Uri.parse("tel:" + mobile));
        startActivity(intent);
        //Toast.makeText(getApplicationContext(),""+mobile,Toast.LENGTH_LONG).show();
    }
}
