package com.example.mobileapp.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "SatelliteData")
public class SatelliteData {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int uid;

    public int getUid(){
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    private String satelliteName;
    private int noOfPasses;
    private String startDirection;
    private int startTime;
    private String peakDirection;
    private int peakTime;
    private String endDirection;
    private int endTime;
    private double magnitude;
    private int duration;
    private double UserLocLat;
    private double UserLocLong;

    public SatelliteData() {
        super();
    }

    public String getSatelliteName() {
        return satelliteName;
    }

    public void setSatelliteName(String satelliteName) {
        this.satelliteName = satelliteName;
    }

    public int getNoOfPasses() {
        return noOfPasses;
    }

    public void setNoOfPasses(int noOfPasses) {
        this.noOfPasses = noOfPasses;
    }

    public String getStartDirection() {
        return startDirection;
    }

    public void setStartDirection(String startDirection) {
        this.startDirection = startDirection;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public String getPeakDirection() {
        return peakDirection;
    }

    public void setPeakDirection(String peakDirection) {
        this.peakDirection = peakDirection;
    }

    public int getPeakTime() {
        return peakTime;
    }

    public void setPeakTime(int peakTime) {
        this.peakTime = peakTime;
    }

    public String getEndDirection() {
        return endDirection;
    }

    public void setEndDirection(String endDirection) {
        this.endDirection = endDirection;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getUserLocLat() {
        return UserLocLat;
    }

    public void setUserLocLat(double UserLocLat) {
        this.UserLocLat = UserLocLat;
    }

    public double getUserLocLong() {
        return UserLocLong;
    }

    public void setUserLocLong(double UserLocLong) {
        this.UserLocLong = UserLocLong;
    }
}
