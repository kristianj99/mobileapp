package com.example.mobileapp.data;
//importing classes
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//class for the data for favourite locations, creates a table called locationdata
@Entity(tableName = "LocationData")
public class LocationData {

    //primary key uid for the program, get and set
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int uid;

    public int getUid(){
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    //getter and setters

    double Longitude;
    double Latitude;

    public LocationData() {
        super();
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double Longitude) {
        this.Longitude = Longitude;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double Latitude) {
        this.Latitude = Latitude;
    }
}
