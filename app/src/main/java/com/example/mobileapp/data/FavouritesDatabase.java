package com.example.mobileapp.data;
//import classes
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mobileapp.SatellitePasses;
import com.example.mobileapp.data.SatelliteData;


//creating the database with the location data class as the entities
@Database(entities = {LocationData.class}, version = 1)
public abstract class FavouritesDatabase extends RoomDatabase {
    //dao for the database
    public abstract FavouritesDao favouritesDao();
    private static FavouritesDatabase INSTANCE = null;

    //creating the database
    public static FavouritesDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FavouritesDatabase.class) {
                INSTANCE = Room.databaseBuilder(context, FavouritesDatabase.class, "favourites_database")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return INSTANCE;
    }


}
