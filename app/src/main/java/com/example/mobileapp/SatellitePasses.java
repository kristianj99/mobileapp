package com.example.mobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileapp.data.FindSatellitePasses;
import com.example.mobileapp.data.SatelliteData;
import com.example.mobileapp.data.SatelliteRepository;

import java.util.ArrayList;
import java.util.List;


public class SatellitePasses extends AppCompatActivity implements View.OnClickListener {
    public static Context context;
    private static final String TAG = "SatelliteApp";
    private static final String SHARED_PREF_FILE_NAME = "com.example.mobileapp";

    private SharedPreferences sharedPreferences;

    private SatelliteData passes;

    private RecyclerViewAdapter adapter;

    private SatelliteData LocData;

    public static double LocLat;
    public static double LocLong;
    int noOfDays;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.satellite_passes);

        sharedPreferences = getSharedPreferences(SHARED_PREF_FILE_NAME, MODE_PRIVATE);

        Button btnSearch = findViewById(R.id.satPass_Search);
        btnSearch.setOnClickListener(this);

        Button btnCurrentPass = findViewById(R.id.satPass_CurrentPass);
        btnCurrentPass.setOnClickListener(this);

                List<SatelliteData> satellitePasses = new ArrayList<SatelliteData>();

        RecyclerView recyclerView = findViewById(R.id.satPass_UpcomingPass);
        adapter = new RecyclerViewAdapter(satellitePasses, getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        LocLat = getIntent().getDoubleExtra("Latitude", 0);
        LocLong = getIntent().getDoubleExtra("Longitude", 0);
        noOfDays = 1;

        LocData = new SatelliteData();
        LocData.setUserLocLat(LocLat);
        LocData.setUserLocLong(LocLong);


        url = "https://api.n2yo.com/rest/v1/satellite/visualpasses/47181/" + String.valueOf(LocLat) + "/" + String.valueOf(LocLong) + "/0/" + noOfDays + "/60&apiKey=V8FYL3-4BLBUJ-DA4B2A-4LO9";
        StringRequest stringRequest =
                new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        FindSatellitePasses findPasses = new FindSatellitePasses();
                        List<SatelliteData> satellitePasses = findPasses.processSatelliteData(response);
                        SatelliteRepository.getRepository(getApplicationContext()).storeSatelliteData(satellitePasses);
                        adapter.setSatelliteData(satellitePasses);
                        adapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "error " + error.getLocalizedMessage());
                    }
                });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);






    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.satPass_Search) {
            EditText etDays = findViewById((R.id.editTextNumber));
            String etDaysString = etDays.getText().toString();
            int etDaysInt = Integer.parseInt(etDaysString);
            noOfDays = etDaysInt;
            Log.d(String.valueOf(noOfDays), "msg");
            url = "https://api.n2yo.com/rest/v1/satellite/visualpasses/47181/" + String.valueOf(LocLat) + "/" + String.valueOf(LocLong) + "/0/" + noOfDays + "/60&apiKey=V8FYL3-4BLBUJ-DA4B2A-4LO9";
            StringRequest stringRequest2 =
                    new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            FindSatellitePasses findPasses = new FindSatellitePasses();
                            List<SatelliteData> satellitePasses = findPasses.processSatelliteData(response);
                            adapter.setSatelliteData(satellitePasses);
                            adapter.notifyDataSetChanged();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "error " + error.getLocalizedMessage());
                        }
                    });
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(stringRequest2);

        } else if (view.getId() == R.id.satPass_CurrentPass) {
            url = "https://api.n2yo.com/rest/v1/satellite/above/" + String.valueOf(LocLat) + "/" + String.valueOf(LocLong) + "/0/90/52/&apiKey=V8FYL3-4BLBUJ-DA4B2A-4LO9";
            StringRequest stringRequest =
                    new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            FindSatellitePasses findPasses = new FindSatellitePasses();
                            List<SatelliteData> satellitePasses = findPasses.processSatelliteData(response);
                            adapter.setSatelliteData(satellitePasses);
                            adapter.notifyDataSetChanged();

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "error " + error.getLocalizedMessage());
                        }
                    });
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(stringRequest);
        }
    }
}