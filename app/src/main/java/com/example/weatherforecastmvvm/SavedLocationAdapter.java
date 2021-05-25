package com.example.weatherforecastmvvm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherforecastmvvm.databinding.EachSavedLocationBinding;

import java.util.List;

public class SavedLocationAdapter extends RecyclerView.Adapter<SavedLocationAdapter.ViewHolder> {
    List<SavedLocation> mSavedLocation;
    LocationListActivity context;
    SharedPreferences sharedPreferences;
    String finaltemp;

    public SavedLocationAdapter(List<SavedLocation> mSavedLocation, LocationListActivity context) {
        this.mSavedLocation = mSavedLocation;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        sharedPreferences = parent.getContext().getSharedPreferences("com.example.weatherforecastmvvm",Context.MODE_PRIVATE);
        finaltemp = sharedPreferences.getString("Temperature Unit","\u2103");
        LayoutInflater inflater = LayoutInflater.from(context);
        EachSavedLocationBinding binding = DataBindingUtil.inflate(inflater,R.layout.each_saved_location,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedLocationAdapter.ViewHolder holder, int position) {
        SavedLocation savedLocation = mSavedLocation.get(position);
        holder.position = position;
        holder.binding.setSavedlocation(savedLocation);
        holder.binding.eachsavedlocationtempunit.setText(finaltemp);
        if(finaltemp.equals("\u2103")){holder.binding.eachsavedlocationcurrenttemp.setText(String.valueOf((int)savedLocation.currenttemp_c));}
        else {holder.binding.eachsavedlocationcurrenttemp.setText(String.valueOf((int)savedLocation.currenttemp_f));}
        Glide.with(context).load("http:"+savedLocation.currentweathericon).into(holder.binding.eachlocationweatherimage);

    }

    @Override
    public int getItemCount() {
        return mSavedLocation.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        EachSavedLocationBinding binding;
        int position;

        public ViewHolder(@NonNull EachSavedLocationBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;

            binding.deleteeachlocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSavedLocation.remove(position);
                    notifyItemRemoved(position);
                    context.deletelocation(position);
                }
            });
        }
    }
}
