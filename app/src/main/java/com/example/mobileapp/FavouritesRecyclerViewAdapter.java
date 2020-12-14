package com.example.mobileapp;
//importing classes
import com.example.mobileapp.data.CurrentPassData;
import com.example.mobileapp.data.LocationData;
import com.example.mobileapp.data.SatelliteData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//class for the favourite locations recycler view
public class FavouritesRecyclerViewAdapter extends RecyclerView.Adapter<FavouritesRecyclerViewAdapter.favouriteLocationViewHolder> {
    //declaring variables
    private List<LocationData> favouriteLocation;
    private Context context;
    double LocLat;
    double LocLong;

    public FavouritesRecyclerViewAdapter(List<LocationData> favouriteLocation, Context context) {
        super();
        this.favouriteLocation = favouriteLocation;
        this.context = context;
    }

    //creates a view holder based off of the view_favourite_table xml
    @NonNull
    @Override
    public favouriteLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(this.context).inflate(R.layout.view_favourite_table, parent, false);
        favouriteLocationViewHolder viewHolder = new favouriteLocationViewHolder(itemView, this);
        return viewHolder;
    }

    //fills the data received from the favourite location database into the recycler view
    @Override
    public void onBindViewHolder(@NonNull favouriteLocationViewHolder holder, int position) {
        LocationData favouriteLocation = this.favouriteLocation.get(position);

        TextView tv = holder.itemView.findViewById(R.id.table_latitude);
        tv.setText("Latitude: " + String.valueOf(favouriteLocation.getLatitude()));

        TextView tv2 = holder.itemView.findViewById(R.id.table_longitude);
        tv2.setText("Longitude: " + String.valueOf(favouriteLocation.getLongitude()));
    }

    //gets the size of the favourite location list
    @Override
    public int getItemCount(){
        return this.favouriteLocation.size();
    }

    //sets the data to the list returned by the database
    public void setLocationData(List<LocationData> data) {
        this.favouriteLocation = data;
    }

    //view holder class
    class favouriteLocationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View itemView;
        private FavouritesRecyclerViewAdapter adapter;

        public favouriteLocationViewHolder(@NonNull View itemView, FavouritesRecyclerViewAdapter adapter) {
            super(itemView);
            this.itemView = itemView;
            this.adapter = adapter;
            this.itemView.setOnClickListener(this);
        }

        //listens to whether the location has been clicked, and if so, gives the location and starts the next activity
        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            LocationData location = favouriteLocation.get(position);
            Log.d("msg", String.valueOf(location.getLatitude()));
            Context context = itemView.getContext();

            LocLat = location.getLatitude();
            LocLong = location.getLongitude();

            Intent intent = new Intent(context, SatellitePasses.class);
            intent.putExtra("Latitude", LocLat);
            intent.putExtra("Longitude", LocLong);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);


        }
    }
}
