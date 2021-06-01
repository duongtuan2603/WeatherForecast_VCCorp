package com.example.weatherforecastmvvm.ui.adapter;

import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherforecastmvvm.R;
import com.example.weatherforecastmvvm.data.local.SavedLocation;
import com.example.weatherforecastmvvm.data.local.SharedPreferenceSettings;
import com.example.weatherforecastmvvm.databinding.EachSavedLocationBinding;
import com.example.weatherforecastmvvm.ui.activities.LocationListActivity;

import java.util.List;

public class SavedLocationAdapter extends RecyclerView.Adapter<SavedLocationAdapter.ViewHolder> {
    List<SavedLocation> mSavedLocation;
    LocationListActivity mContext;
    SharedPreferences mSharedPreferences;
    String mFinalTemp;

    public SavedLocationAdapter(List<SavedLocation> mSavedLocation, LocationListActivity context) {
        this.mSavedLocation = mSavedLocation;
        this.mContext = context;
        mFinalTemp = SharedPreferenceSettings.getSharedPreferenceSettings(mContext.getApplicationContext()).getTemperatureunit();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        EachSavedLocationBinding binding = DataBindingUtil.inflate(inflater, R.layout.each_saved_location, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedLocationAdapter.ViewHolder holder, int position) {
        SavedLocation savedLocation = mSavedLocation.get(position);
        holder.position = position;
        holder.binding.setSavedlocation(savedLocation);
        holder.binding.eachsavedlocationtempunit.setText(mFinalTemp);
        if (mFinalTemp.equals("\u2103")) {
            holder.binding.eachsavedlocationcurrenttemp.setText(String.valueOf((int) savedLocation.getCurrenttemp_c()));
        } else {
            holder.binding.eachsavedlocationcurrenttemp.setText(String.valueOf((int) savedLocation.getCurrenttemp_f()));
        }
        Glide.with(mContext).load("http:" + savedLocation.getCurrentweathericon()).into(holder.binding.eachlocationweatherimage);

    }

    @Override
    public int getItemCount() {
        return mSavedLocation.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EachSavedLocationBinding binding;
        int position;

        public ViewHolder(@NonNull EachSavedLocationBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.deleteeachlocation.setOnClickListener(v -> {
                mSavedLocation.remove(position);
                notifyItemRemoved(position);
                mContext.deletelocation(position);
            });
            binding.eachlocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.onItemClicked(position);
                }
            });
        }
    }
}
