package com.example.weatherforecastmvvm;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SavedLocationDao {
    @Insert
    void insert(SavedLocation location);
    @Query("DELETE FROM SavedLocation WHERE locationname = :name")
    void deletebyname(String name);
    @Query("SELECT * FROM SavedLocation")
    List<SavedLocation> getalllocations();
    @Update
    void update(SavedLocation savedLocation);
}
