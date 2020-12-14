package com.example.mobileapp;
//importing classes
import com.example.mobileapp.data.CurrentPassData;
import com.example.mobileapp.data.SatelliteData;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//class for the current passes adapter
public class CurrentPassesRecyclerViewAdapter extends RecyclerView.Adapter<CurrentPassesRecyclerViewAdapter.currentPassesViewHolder> {
    private List<CurrentPassData> currentPasses;
    private Context context;


    public CurrentPassesRecyclerViewAdapter(List<CurrentPassData> currentPasses, Context context) {
        super();
        this.currentPasses = currentPasses;
        this.context = context;
    }

    //creates a view holder based off of the current_pass xml
    @NonNull
    @Override
    public currentPassesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(this.context).inflate(R.layout.current_pass, parent, false);
        currentPassesViewHolder viewHolder = new currentPassesViewHolder(itemView, this);
        return viewHolder;
    }

    //fills the data received from the api into the recycler view
    @Override
    public void onBindViewHolder(@NonNull currentPassesViewHolder holder, int position) {
        CurrentPassData currentPass = this.currentPasses.get(position);
        TextView tv = holder.itemView.findViewById(R.id.tv_noOfSats);
        tv.setText("Number of visible satellites: " + String.valueOf(currentPass.getNoOfSats()));

        TextView tv2 = holder.itemView.findViewById(R.id.tv_SatName);
        tv2.setText(currentPass.getSatelliteName());

        TextView tv3 = holder.itemView.findViewById(R.id.tv_SatLat);
        tv3.setText("Satellite latitude: " + String.valueOf(currentPass.getSatLat()));

        TextView tv4 = holder.itemView.findViewById(R.id.tv_SatLong);
        tv4.setText("Satellite longitude: " + String.valueOf(currentPass.getSatLong()));

        TextView tv5 = holder.itemView.findViewById(R.id.tv_SatAlt);
        tv5.setText("Satellite altitude: " + String.valueOf(currentPass.getSatAlt()) + "km");



    }

    //gets the size of the currentpasses list
    @Override
    public int getItemCount() {
        return this.currentPasses.size();
    }

    //sets the list to the data returned by the api
    public void setCurrentPassData(List<CurrentPassData> data) {
        this.currentPasses = data;
    }

    //view holder class
    class currentPassesViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private CurrentPassesRecyclerViewAdapter adapter;

        public currentPassesViewHolder(@NonNull View itemView, CurrentPassesRecyclerViewAdapter adapter) {
            super(itemView);
            this.itemView = itemView;
            this.adapter = adapter;
        }
    }
}
