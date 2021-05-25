package com.example.weatherforecastmvvm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class LocationPageAdapter extends FragmentStatePagerAdapter {
    private List<SavedLocation> mSavedLocation;

    public LocationPageAdapter(@NonNull FragmentManager fm, int behavior, List<SavedLocation> list) {
        super(fm, behavior);
        this.mSavedLocation = list;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        SavedLocation savedLocation = mSavedLocation.get(position);
        LocationPageFragment locationPageFragment = new LocationPageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Location Page Fragment", savedLocation.locationname);
        locationPageFragment.setArguments(bundle);
        return locationPageFragment;
    }

    @Override
    public int getCount() {
        return mSavedLocation.size();
    }
}
