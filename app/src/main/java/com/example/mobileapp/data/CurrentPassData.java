package com.example.mobileapp.data;
//importing classes
public class CurrentPassData {

    //declaring variables
    private String satelliteName;
    private int noOfSats;
    private double satLat;
    private double satLong;
    private double satAlt;


    //getters and setters
    public CurrentPassData() {
        super();
    }

    public String getSatelliteName() {
        return satelliteName;
    }

    public void setSatelliteName(String satelliteName) {
        this.satelliteName = satelliteName;
    }

    public int getNoOfSats() {
        return noOfSats;
    }

    public void setNoOfSats(int noOfSats) {
        this.noOfSats = noOfSats;
    }

    public double getSatLat() {
        return satLat;
    }

    public void setSatLat(double satLat) {
        this.satLat = satLat;
    }

    public double getSatLong() {
        return satLong;
    }

    public void setSatLong(double satLong) {
        this.satLong = satLong;
    }

    public double getSatAlt() {
        return satAlt;
    }

    public void setSatAlt(double satAlt) {
        this.satAlt = satAlt;
    }

}
