package com.example.mobileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static Context context;
    private static double LocLat;
    private static double LocLong;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnEnterLoc = findViewById(R.id.menu_EnterLoc);
        btnEnterLoc.setOnClickListener(this);

        Button btnUseGPS = findViewById(R.id.menu_UseGPS);
        btnUseGPS.setOnClickListener(this);

        Button btnUseWorldMap = findViewById(R.id.menu_UseMap);
        btnUseWorldMap.setOnClickListener(this);

        Button btnViewFavs = findViewById(R.id.menu_viewFav);
        btnViewFavs.setOnClickListener(this);

        Button btnViewHistory = findViewById(R.id.menu_ViewHistory);
        btnViewHistory.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.menu_EnterLoc) {
            Intent intent = new Intent(getApplicationContext(), EnterLocation.class);
            startActivity(intent);
        } else if (view.getId() == R.id.menu_UseMap) {
            Intent intent = new Intent(getApplicationContext(), UseWorldMap.class);
            startActivity(intent);
        } else if (view.getId() == R.id.menu_viewFav) {
            Intent intent = new Intent(getApplicationContext(), ViewFavourites.class);
            startActivity(intent);
        } else if (view.getId() == R.id.menu_ViewHistory) {
            Intent intent = new Intent(getApplicationContext(), ViewHistory.class);
            startActivity(intent);
        } else if (view.getId() == R.id.menu_UseGPS) {
            LocLat = 57.162575;
            LocLong = -2.227294;
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
    public void GetLocation(Location loc) {

    }
}