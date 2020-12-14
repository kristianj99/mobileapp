package com.example.mobileapp;
//importing classes
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileapp.data.CurrentPassData;
import com.example.mobileapp.data.FindSatellitePasses;

import java.util.ArrayList;
import java.util.List;

import static com.example.mobileapp.SatellitePasses.LocLat;
import static com.example.mobileapp.SatellitePasses.LocLong;

//class for viewing the current overhead passes
public class CurrentPassesRecyclerView extends AppCompatActivity {

    //declaring variables
    private static final String TAG = "SatelliteApp";

    private CurrentPassesRecyclerViewAdapter adapter;

    String url;

    //runs when the app is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //sets the layout xml file
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_pass_activity);


        //creates a new list
        List<CurrentPassData> currentPasses = new ArrayList<CurrentPassData>();

        //creates the recycler view and gets the adapter
        RecyclerView recyclerView = findViewById(R.id.rv_CurrentPass);
        adapter = new CurrentPassesRecyclerViewAdapter(currentPasses, getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //creates the dynamic url that will be used to search the api and receive json response
        url = "https://api.n2yo.com/rest/v1/satellite/above/" + String.valueOf(LocLat) + "/" + String.valueOf(LocLong) + "/0/90/52/&apiKey=V8FYL3-4BLBUJ-DA4B2A-4LO9";
        //creates a new string response
        StringRequest stringRequest =
                new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //creates a new findsatellitepasses class
                        FindSatellitePasses findPasses = new FindSatellitePasses();
                        //processes the data it receives in the findsatellitepasses process function
                        List<CurrentPassData> satellitePasses = findPasses.processCurrentSatellites(response);
                        //adds to the adapter and updates it
                        adapter.setCurrentPassData(satellitePasses);
                        adapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //error handling
                        Log.d(TAG, "error " + error.getLocalizedMessage());
                    }
                });
        //adds the request to the queue and executes it
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }
}
