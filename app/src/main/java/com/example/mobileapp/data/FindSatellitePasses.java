package com.example.mobileapp.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.nfc.Tag;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.example.mobileapp.SatellitePasses;

public class FindSatellitePasses {
    public static Context context;

    public SatelliteData processSatellitePasses(String response) {
        SatelliteData data = null;
        try {
            data = new SatelliteData();
            JSONObject jsonObject = new JSONObject(response);

            JSONObject infoObject = jsonObject.getJSONObject("info");
            data.setSatelliteName(infoObject.getString("satname"));
            data.setNoOfPasses(infoObject.getInt("passescount"));

            JSONArray passesArray = jsonObject.getJSONArray("passes");
            for (int i =0, j = passesArray.length() ;i<j;i++) {
                JSONObject passesObj = passesArray.getJSONObject(i);
                data.setStartDirection(passesObj.getString("startAzCompass"));
                data.setStartTime(passesObj.getInt("startUTC"));
                data.setPeakDirection(passesObj.getString("maxAzCompass"));
                data.setPeakTime(passesObj.getInt("maxUTC"));
                data.setEndDirection(passesObj.getString("endAzCompass"));
                data.setEndTime(passesObj.getInt("endUTC"));
                data.setMagnitude(passesObj.getDouble("mag"));
                data.setDuration(passesObj.getInt("duration"));
            }



        } catch (JSONException e) {
            data = null;
            e.printStackTrace();
        }

        return data;
    }
    public List<SatelliteData> processSatelliteData(String response) {
        List<SatelliteData> satellitePasses = new ArrayList<SatelliteData>();
        SatelliteData data = null;

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray passesArray = jsonObject.getJSONArray("passes");



            for (int i =0, j = passesArray.length() ;i<j;i++) {
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



        } catch (JSONException e) {
            e.printStackTrace();
        }

        return satellitePasses;
    }
}
