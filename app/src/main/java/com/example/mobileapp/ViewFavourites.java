package com.example.mobileapp;
//import classes
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;

import com.example.mobileapp.data.FavouritesRepository;
import com.example.mobileapp.data.LocationData;

import java.util.ArrayList;
import java.util.List;

public class ViewFavourites extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //creates the activity with the view_favourites xml layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_favourites);

        //creates a listener for the delete button
        ImageButton btnFavourite = findViewById(R.id.fav_Delete);
        btnFavourite.setOnClickListener(this);

        //creates a new list
        List<LocationData> favouriteLocations = new ArrayList<LocationData>();

        //creates the recycler view and adapter for displaying the favourite locations and displays them
        RecyclerView recyclerView = findViewById(R.id.fav_Recycler);
        FavouritesRecyclerViewAdapter adapter = new FavouritesRecyclerViewAdapter(favouriteLocations, getApplicationContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        List<LocationData> savedFavourites = FavouritesRepository.getRepository(getApplicationContext()).getFavouriteLocation();

        adapter.setLocationData(savedFavourites);
        adapter.notifyDataSetChanged();


    }
    //if the button is clicked, then all of the favourites are deleted
    //i would like to be able to delete them individually, but did not add due to time constraints
    public void onClick (View view) {
        if (view.getId() == R.id.fav_Delete) {
            FavouritesRepository.getRepository(getApplicationContext()).deleteFavouriteLocations();
        }
    }
}