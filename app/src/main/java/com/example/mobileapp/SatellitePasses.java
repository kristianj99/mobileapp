package com.example.mobileapp;
//import classes
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
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobileapp.data.CurrentPassData;
import com.example.mobileapp.data.FavouritesRepository;
import com.example.mobileapp.data.FindSatellitePasses;
import com.example.mobileapp.data.LocationData;
import com.example.mobileapp.data.SatelliteData;
import com.example.mobileapp.data.SatelliteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class SatellitePasses extends AppCompatActivity implements View.OnClickListener {
    //declaring variables
    public static Context context;
    private static final String TAG = "SatelliteApp";
    private static final String SHARED_PREF_FILE_NAME = "com.example.mobileapp";

    private SharedPreferences sharedPreferences;

    private SatelliteData passes;

    private RecyclerViewAdapter adapter;

    private CurrentPassesRecyclerViewAdapter currentPassAdapter;

    private SatelliteData LocData;

    public static double LocLat;
    public static double LocLong;
    int noOfDays;
    String url;

    String prefLat = "pref_latitude";
    String prefLong = "pref_longitude";

    String locLatKey = "location_latitude";
    String locLongKey = "location_longitude";

    String[] satellites;

    public int history;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //when the app is created, loads the satellite_passes xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.satellite_passes);

        //gets the sharedpreferences file
        sharedPreferences = getSharedPreferences(SHARED_PREF_FILE_NAME, MODE_PRIVATE);


        //sets listeners for the buttons in the activity to listen for when they are pressed
        Button btnSearch = findViewById(R.id.satPass_Search);
        btnSearch.setOnClickListener(this);

        Button btnCurrentPass = findViewById(R.id.satPass_CurrentPass);
        btnCurrentPass.setOnClickListener(this);

        ImageButton btnFavourite = findViewById(R.id.satPass_Fav);
        btnFavourite.setOnClickListener(this);

        //creates new lists for the data
        List<SatelliteData> satellitePasses = new ArrayList<SatelliteData>();

        List<CurrentPassData> currentPasses = new ArrayList<CurrentPassData>();

        List<LocationData> favouriteLocation = new ArrayList<LocationData>();

        //creates the recycler view and the adapter it will use
        RecyclerView recyclerView = findViewById(R.id.satPass_UpcomingPass);
        adapter = new RecyclerViewAdapter(satellitePasses, getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //sets a seperate adapter for the current pass part of the app
        currentPassAdapter = new CurrentPassesRecyclerViewAdapter(currentPasses, getApplicationContext());

        //collects the history intent from the main activity class. if previous location has been used, 1 will be given, otherwise the value will be 0
        history = getIntent().getIntExtra("History", 0);

        //checks whether the history button has been pressed or not
        //if it has, then it checks the sharedpreferences to find the last used location by the app
        //otherwise, it will take the location from whichever button was previously pressed in the app before
        if (history == 1) {
            prefLat = sharedPreferences.getString(locLatKey, "0");
            prefLong = sharedPreferences.getString(locLongKey, "0");
            LocLat = Double.parseDouble(prefLat);
            LocLong = Double.parseDouble(prefLong);
        } else if (history == 0) {
            LocLat = getIntent().getDoubleExtra("Latitude", 0);
            LocLong = getIntent().getDoubleExtra("Longitude", 0);
        }


        //chooses the default number of days searched by the app
        noOfDays = 1;

        //gives the findsatellitepasses class the latitude and longitude being used
        Intent intent = new Intent(getApplicationContext(),FindSatellitePasses.class);
        intent.putExtra("Latitude", LocLat);
        intent.putExtra("Longitude", LocLong);

        //gives the sharedpreferences the value being used for the latitude and longitude as a string
        prefLat = String.valueOf(LocLat);
        prefLong = String.valueOf(LocLong);

        //creates an array of the amount of starlink satellite id's. there are hundreds, but i had difficulty with the stability of the api at doing multiple searches, with many times it returning the value as null
        //although it is only 1 satellite, as multiple are launched at the same time it would be likely the viewer would see multiple satellites as the same time as the one it displays on screen
        //in future this could be used to search multiple trains of satellites, and it did work when adding extra id's into the app, however it wasn't stable and often returned errors, so for the sake of the app working
        // in demonstration i chose to only show one
        //46798, 46729, and 46591 were the id's i also used to test in development. if you want you can try and add these to the array, but as i said, sometimes it just returns null for an unknown reason, and sometimes it displays
        //the whole list
        satellites = new String[]{"47181"};

        //list that will combine the 4 api searches
        final List<SatelliteData> combined = new ArrayList<SatelliteData>();

        //for however long the satellites array is, send requests to the the api, receive the response, store it in a database, add the lists together, and add them to the recycler view
        for (int i=0, j = satellites.length ;i<j;i++) {
            url = "https://api.n2yo.com/rest/v1/satellite/visualpasses/" + satellites[i] + "/" + String.valueOf(LocLat) + "/" + String.valueOf(LocLong) + "/0/" + noOfDays + "/60&apiKey=V8FYL3-4BLBUJ-DA4B2A-4LO9";
            StringRequest stringRequest =
                    new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            FindSatellitePasses findPasses = new FindSatellitePasses();
                            List<SatelliteData> satellitePasses = findPasses.processSatelliteData(response);
                            SatelliteRepository.getRepository(getApplicationContext()).storeSatelliteData(satellitePasses);
                            combined.addAll(satellitePasses);
                            adapter.setSatelliteData(combined);
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
    //for when a button is clicked
    @Override
    public void onClick(View view) {

        //if the search by days button is pressed, it takes the users input and turns it into an integer
        //it then searches the api in the same way the previous version does, however this time it changes the number of days it is searching for

        //if the current pass button is pressed, the program starts a new activity that handles the request

        //if the favourite location button is pressed, the location is taken by the app and added into the database for it
        if (view.getId() == R.id.satPass_Search) {
            EditText etDays = findViewById((R.id.editTextNumber));
            String etDaysString = etDays.getText().toString();
            int etDaysInt = Integer.parseInt(etDaysString);
            noOfDays = etDaysInt;
            Log.d(String.valueOf(noOfDays), "msg");
            final List<SatelliteData> combined = new ArrayList<SatelliteData>();
            for (int i=0, j = satellites.length ;i<j;i++) {
                url = "https://api.n2yo.com/rest/v1/satellite/visualpasses/" + satellites[i] + "/" + String.valueOf(LocLat) + "/" + String.valueOf(LocLong) + "/0/" + noOfDays + "/60&apiKey=V8FYL3-4BLBUJ-DA4B2A-4LO9";
                StringRequest stringRequest2 =
                        new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                FindSatellitePasses findPasses = new FindSatellitePasses();
                                List<SatelliteData> satellitePasses = findPasses.processSatelliteData(response);
                                combined.addAll(satellitePasses);
                                adapter.setSatelliteData(combined);
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
            }


        } else if (view.getId() == R.id.satPass_CurrentPass) {

            Intent intent = new Intent(getApplicationContext(), CurrentPassesRecyclerView.class);
            startActivity(intent);
        } else if (view.getId() == R.id.satPass_Fav) {
            List<LocationData> favouriteLocation = ProcessFavouriteLocation();
            FavouritesRepository.getRepository(getApplicationContext()).storeLocationData(favouriteLocation);
        }
    }

    //when the activity is paused, the app takes the location and adds it into sharedpreferences
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor spe = sharedPreferences.edit();
        spe.clear();
        spe.putString(locLatKey, prefLat);
        spe.putString(locLongKey, prefLong);
        spe.apply();
    }

    //processes the location to add to the favourite location database
    public List<LocationData> ProcessFavouriteLocation() {
        //creates a new list for the data
        List<LocationData> favouriteLocation = new ArrayList<LocationData>();
        LocationData data = null;

        //creates a new instance of locationdata and adds the latitude and longitude to it, then returns it
        data = new LocationData();
        data.setLatitude(LocLat);
        data.setLongitude(LocLong);
        favouriteLocation.add(data);

        return favouriteLocation;
    }
}