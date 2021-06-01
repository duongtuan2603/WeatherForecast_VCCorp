package com.example.weatherforecastmvvm.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.weatherforecastmvvm.data.local.SavedLocation;

import java.util.List;

@Dao
public interface SavedLocationDao {
    @Insert
    void insert(SavedLocation location);

    @Query("DELETE FROM SavedLocation WHERE locationname = :name")
    void deletebyname(String name);

    @Query("SELECT * FROM SavedLocation")
    List<SavedLocation> getalllocations();
}
