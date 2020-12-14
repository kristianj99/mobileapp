package com.example.mobileapp.data;
//importing classses
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.nfc.Tag;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;

import com.example.mobileapp.SatellitePasses;

//class to process the JSON received from the API
public class FindSatellitePasses {
    //declaring variables
    public static Context context;
    double LocLat;
    double LocLong;

    //function to process the data for the current passes
    public List<CurrentPassData> processCurrentSatellites(String response) {
        //creates a list to put the multiple responses into
        List<CurrentPassData> currentPasses = new ArrayList<CurrentPassData>();
        CurrentPassData data = null;

        try {
            //gets the json object received from the API
            JSONObject jsonObject = new JSONObject(response);
            //gets the array of data from the object
            JSONArray aboveArray = jsonObject.getJSONArray("above");
            //checks the length of the array, then loops until it reaches the end
            for (int i =0, j = aboveArray.length() ;i<j;i++) {
                //creates a new currentpassdata class, and fills it with data from the json, putting it in the list once complete
                data = new CurrentPassData();
                JSONObject infoObject = jsonObject.getJSONObject("info");
                data.setNoOfSats(infoObject.getInt("satcount"));
                JSONObject aboveObj = aboveArray.getJSONObject(i);
                data.setSatelliteName(aboveObj.getString("satname"));
                data.setSatLat(aboveObj.getDouble("satlat"));
                data.setSatLong(aboveObj.getDouble("satlng"));
                data.setSatAlt(aboveObj.getDouble("satalt"));
                currentPasses.add(data);
            }


        //json error handling
        } catch (JSONException e) {
            data = null;
            e.printStackTrace();
        }
        //returns the list
        return currentPasses;
    }

    //function to process the data for upcoming passes
    public List<SatelliteData> processSatelliteData(String response) {
        //creates a list to put the data into
        List<SatelliteData> satellitePasses = new ArrayList<SatelliteData>();
        SatelliteData data = null;

        try {
            //gets the object from the json
            JSONObject jsonObject = new JSONObject(response);

            //gets the passes array from the object
            JSONArray passesArray = jsonObject.getJSONArray("passes");

            //creates a for loop, the length of the array, to collect the data
            for (int i =0, j = passesArray.length() ;i<j;i++) {
                //puts the data into a new satellitedata
                JSONObject passesObj = passesArray.getJSONObject(i);
                data = new SatelliteData();
                JSONObject infoObject = jsonObject.getJSONObject("info");
                data.setSatelliteName(infoObject.getString("satname"));
                data.setNoOfPasses(infoObject.getInt("passescount"));
                Log.i(passesObj.getString("startAzCompass"), "blah");
                data.setStartDirection(passesObj.getString("startAzCompass"));
                data.setStartTime(passesObj.getInt("startUTC"));
                data.setPeakDirection(passesObj.getString("maxAzCompass"));
                data.setPeakTime(passesObj.getInt("maxUTC"));
                data.setEndDirection(passesObj.getString("endAzCompass"));
                data.setEndTime(passesObj.getInt("endUTC"));
                data.setMagnitude(passesObj.getDouble("mag"));
                data.setDuration(passesObj.getInt("duration"));

                satellitePasses.add(data);
            }


        //error handling for the json
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //returns the list
        return satellitePasses;
    }
}
