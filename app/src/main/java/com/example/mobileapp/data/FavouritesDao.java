package com.example.mobileapp.data;
//importing classes
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mobileapp.SatellitePasses;

import java.util.List;
//dao for the database of favourite locations
@Dao
public interface FavouritesDao {
    //function to insert data into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(LocationData... favouriteLocation);

    //function to update data in the database
    @Update
    public void update(LocationData... favouriteLocation);

    //query to get all data from the database
    @Query("SELECT * from LocationData")
    public List<LocationData> getFavouriteLocation();

    //query to delete data from the database
    @Query("DELETE from LocationData")
    int deleteFavouriteLocations();
}
