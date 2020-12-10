package com.example.mobileapp.data;

import android.content.Context;

import java.util.List;

public class SatelliteRepository {
    private static SatelliteRepository INSTANCE;

    private Context context;

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

    public void storeSatelliteData(List<SatelliteData> satellitePasses) {
        this.satellitePassesDao.insert(satellitePasses.toArray(new SatelliteData[satellitePasses.size()]));
    }



}
