package com.example.mobileapp.data;
//import classes
import android.content.Context;

import java.util.List;

//creating the repository for the satellite passes database
public class SatelliteRepository {
    private static SatelliteRepository INSTANCE;

    //context
    private Context context;
    //dao
    private SatellitePassesDao satellitePassesDao;

    public static SatelliteRepository getRepository(Context context) {
        if (INSTANCE == null) {
            synchronized (SatelliteRepository.class) {
                if (INSTANCE == null)
                    INSTANCE = new SatelliteRepository();
                    INSTANCE.context = context;
                    LocationDatabase db = LocationDatabase.getDatabase(context);
                    INSTANCE.satellitePassesDao = db.satellitePassesDao();
            }
        }
        return INSTANCE;
    }

    //function to store data into the database
    public void storeSatelliteData(List<SatelliteData> satellitePasses) {
        this.satellitePassesDao.insert(satellitePasses.toArray(new SatelliteData[satellitePasses.size()]));
    }



}
