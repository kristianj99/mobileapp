package com.example.mobileapp.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mobileapp.SatellitePasses;

import java.util.List;

@Dao
public interface SatellitePassesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(SatelliteData... satellitePasses);

    @Update
    public void update(SatelliteData... satellitePasses);

    @Delete
    public void delete(SatelliteData... satellitePasses);

    @Query("SELECT * from SatelliteData")
    public List<SatelliteData> getSatellitePasses();
}
