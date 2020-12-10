package com.example.mobileapp;

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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.satellitePassesViewHolder> {
    private List<SatelliteData> satellitePasses;
    private Context context;
    private SharedPreferences sharedPreferences;

    public RecyclerViewAdapter(List<SatelliteData> satellitePasses, Context context ) {
        super();
        this.satellitePasses = satellitePasses;
        this.context = context;
    }

    @NonNull
    @Override
    public satellitePassesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(this.context).inflate(R.layout.view_pass, parent, false);
        satellitePassesViewHolder viewHolder = new satellitePassesViewHolder(itemView, this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull satellitePassesViewHolder holder, int position) {

        SatelliteData satellitePass = this.satellitePasses.get(position);
        TextView tv = holder.itemView.findViewById(R.id.tv_satelliteName);
        tv.setText(satellitePass.getSatelliteName());

        TextView tv2 = holder.itemView.findViewById(R.id.tv_NoOfPasses);
        tv2.setText("Number of passes: " + satellitePass.getNoOfPasses());


        long unixStart = satellitePass.getStartTime();
        Date date = new java.util.Date(unixStart*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-0"));
        String formattedDate = sdf.format(date);
        TextView tv3 = holder.itemView.findViewById(R.id.tv_startDir);
        tv3.setText("Start looking: " + satellitePass.getStartDirection() + " at " + formattedDate );

        long unixPeak = satellitePass.getPeakTime();
        Date date2 = new java.util.Date(unixPeak*1000L);
        SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-0"));
        String formattedDate2 = sdf.format(date2);
        TextView tv4 = holder.itemView.findViewById(R.id.tv_PeakDir);
        tv4.setText("Satellite will peak facing " + satellitePass.getPeakDirection() + " at " + formattedDate2);

        long unixEnd = satellitePass.getEndTime();
        Date date3 = new java.util.Date(unixEnd*1000L);
        SimpleDateFormat sdf3 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        String formattedDate3 = sdf.format(date3);
        TextView tv5 = holder.itemView.findViewById(R.id.tv_EndDir);
        tv5.setText("Visibility will end facing " + satellitePass.getEndDirection() + " at " + formattedDate3);

        double satMagnitude = satellitePass.getMagnitude();
        String visibility = "";
        if (satMagnitude == 100000) {
            visibility = "Unknown";
        } else if (satMagnitude > 2.5 && satMagnitude != 100000) {
            visibility = "Poor";
        } else {
            visibility = "Good";
        }
        TextView tv6 = holder.itemView.findViewById(R.id.tv_Visbility);
        tv6.setText("Visibility: " + visibility);

        TextView tv7 = holder.itemView.findViewById(R.id.tv_Duration);
        tv7.setText("Duration: " + satellitePass.getDuration() + " seconds");




    }

    @Override
    public int getItemCount() {
        return this.satellitePasses.size();
    }

    public void setSatelliteData(List<SatelliteData> data) {
        this.satellitePasses = data;
    }

    class satellitePassesViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private RecyclerViewAdapter adapter;

        public satellitePassesViewHolder(@NonNull View itemView, RecyclerViewAdapter adapter) {
            super(itemView);
            this.itemView = itemView;
            this.adapter = adapter;
        }
    }
}
