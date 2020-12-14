package com.example.mobileapp;
//import classes
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

//class for the menu of the app
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //declaring variables
    public static Context context;
    private static double LocLat;
    private static double LocLong;
    private LocationManager locationManager;
    private int history;

    //when the app is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //sets the layout to the activity_main xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creates 4 on click listeners for the buttons in the menu, to listen for when they are pressed
        Button btnUseGPS = findViewById(R.id.menu_UseGPS);
        btnUseGPS.setOnClickListener(this);

        Button btnUseWorldMap = findViewById(R.id.menu_UseMap);
        btnUseWorldMap.setOnClickListener(this);

        Button btnViewFavs = findViewById(R.id.menu_viewFav);
        btnViewFavs.setOnClickListener(this);

        Button btnViewHistory = findViewById(R.id.menu_ViewHistory);
        btnViewHistory.setOnClickListener(this);

        //checks for permission for accessing the phone gps. if not, it will request permission
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        //checks for permission for accessing the phones internet. if not, it will request permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE},1);
        }

        //requests location from the gps (this code is repeated as I was having trouble with it actually registering the gps first time round, sometimes it enters the location as 0.0/0.0
        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 10, 0, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                LocLat = location.getLatitude();
                LocLong = location.getLongitude();
            }
        });

    }

    //function for when a button is pressed
    @Override
    public void onClick(View view) {
        //if the use map button is pressed, the useworldmap class is opened
        //if the view favourites button is pressed, the viewfavourites class is opened
        //if the view history button is pressed, the satellitepasses class is opened. an indicator that the history button has been used is also sent with the user
        //if the use gps button is pressed, the users permissions for gps are checked, the location is given, and the user moves forward to the satellitepasses class
        if (view.getId() == R.id.menu_UseMap) {
            Intent intent = new Intent(getApplicationContext(), UseWorldMap.class);
            startActivity(intent);
        } else if (view.getId() == R.id.menu_viewFav) {
            Intent intent = new Intent(getApplicationContext(), ViewFavourites.class);
            startActivity(intent);
        } else if (view.getId() == R.id.menu_ViewHistory) {
            Intent intent = new Intent(getApplicationContext(), SatellitePasses.class);
            history = 1;
            intent.putExtra("History", history);
            startActivity(intent);
        } else if (view.getId() == R.id.menu_UseGPS) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE},1);
            }
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 10, 0, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    LocLat = location.getLatitude();
                    LocLong = location.getLongitude();
                }
            });
            Log.i(String.valueOf(LocLat), String.format("Latitude is ", LocLat));
            Log.i(String.valueOf(LocLong), String.format("Longitude is ", LocLong));
            Intent intent = new Intent(getApplicationContext(), SatellitePasses.class);
            intent.putExtra("Latitude", LocLat);
            intent.putExtra("Longitude", LocLong);
            startActivity(intent);

        }
    }
}