package com.example.mobileapp.data;
//importing classes
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mobileapp.SatellitePasses;
import com.example.mobileapp.data.SatelliteData;


//creating database for saving the location, with the satellite data class as the entity
@Database(entities = {SatelliteData.class}, version = 1)
public abstract class LocationDatabase extends RoomDatabase {

    public abstract SatellitePassesDao satellitePassesDao();
    private static LocationDatabase INSTANCE = null;

    //creates the database
    public static LocationDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LocationDatabase.class) {
                INSTANCE = Room.databaseBuilder(context, LocationDatabase.class, "location_database")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return INSTANCE;
    }


}
