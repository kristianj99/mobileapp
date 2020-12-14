package com.example.mobileapp;
//import classes
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class UseWorldMap extends AppCompatActivity implements View.OnClickListener {
    //creates a new google map
    private GoogleMap mMap;
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;

    //declare variables
    Double LocLat;
    Double LocLong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //uses the layout use_world_map xml
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_world_map);

        //creates a listener for the continue button
        Button btnChooseLocation = findViewById(R.id.worldMap_Continue);
        btnChooseLocation.setOnClickListener(this);


        //code to create a google map
        //when the map is clicked, it will add a marker and get the location from it
        //if there is already a marker, it will remove that one and add a new one
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setOnCameraIdleListener(onCameraIdleListener);
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.getUiSettings().isScrollGesturesEnabled();
                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        mMap.clear();
                        Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));
                        LocLat = marker.getPosition().latitude;
                        LocLong = marker.getPosition().longitude;

                    }
                });

                mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                    @Override
                    public void onMapLoaded() {
                        mMap = googleMap;
                    }
                });
            }
        });




    }
    //when the continue button is pressed, it takes the location from the marker placed and starts the satellitepasses activity
    public void onClick(View view) {
        if (view.getId() == R.id.worldMap_Continue) {
            Intent intent = new Intent(getApplicationContext(), SatellitePasses.class);
            intent.putExtra("Latitude", LocLat);
            intent.putExtra("Longitude", LocLong);
            Log.i(String.valueOf(LocLat), String.format("Latitude is ", LocLat));
            Log.i(String.valueOf(LocLong), String.format("Longitude is ", LocLong));
            startActivity(intent);

        }
    }

}