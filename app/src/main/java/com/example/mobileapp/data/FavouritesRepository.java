package com.example.mobileapp.data;
//importing classes

import android.content.Context;

import java.util.List;

//creating the repository for the favourite locations
public class FavouritesRepository {

    private static FavouritesRepository INSTANCE;
    //context
    private Context context;

    //dao
    private FavouritesDao favouritesDao;


    public static FavouritesRepository getRepository(Context context) {
        if (INSTANCE == null) {
            synchronized (FavouritesRepository.class) {
                if (INSTANCE == null)
                    INSTANCE = new FavouritesRepository();
                INSTANCE.context = context;
                FavouritesDatabase db = FavouritesDatabase.getDatabase(context);
                INSTANCE.favouritesDao = db.favouritesDao();
            }
        }
        return INSTANCE;
    }

    //function to store the data in the database
    public void storeLocationData(List<LocationData> favouriteLocation) {
        this.favouritesDao.insert(favouriteLocation.toArray(new LocationData[favouriteLocation.size()]));
    }

    //returns function to get data in the database from the dao
    public List<LocationData> getFavouriteLocation() {
        return favouritesDao.getFavouriteLocation();
    }

    //returns function to delete the favourite locations from the database
    public int deleteFavouriteLocations() {
        return favouritesDao.deleteFavouriteLocations();
    }



}
