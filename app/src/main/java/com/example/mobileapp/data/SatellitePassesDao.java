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


//dao for satellite passes database
@Dao
public interface SatellitePassesDao {

    //for inserting data into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(SatelliteData... satellitePasses);

    //for updating data if it already exists in the database
    @Update
    public void update(SatelliteData... satellitePasses);

    //for deleting data in the database
    @Delete
    public void delete(SatelliteData... satellitePasses);

    //query to get data from the database
    @Query("SELECT * from SatelliteData")
    public List<SatelliteData> getSatellitePasses();
}
