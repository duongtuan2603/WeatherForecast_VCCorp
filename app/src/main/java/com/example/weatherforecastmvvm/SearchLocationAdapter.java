package com.example.weatherforecastmvvm;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforecastmvvm.databinding.EachSearchedLocationBinding;

import java.util.List;

public class SearchLocationAdapter extends RecyclerView.Adapter<SearchLocationAdapter.SearchLocationViewHolder>{
    List<SearchLocation> mSearchLocation;
    AddLocationActivity context;
    long returnedid;

    public SearchLocationAdapter(List<SearchLocation> mSearchLocation, AddLocationActivity context) {
        this.mSearchLocation = mSearchLocation;
        this.context = context;
    }

    @NonNull
    @Override
    public SearchLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        EachSearchedLocationBinding binding = DataBindingUtil.inflate(inflater,R.layout.each_searched_location,parent,false);
        return new SearchLocationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchLocationAdapter.SearchLocationViewHolder holder, int position) {
        SearchLocation searchLocation = mSearchLocation.get(position);
        holder.binding.setSearchedlocation(searchLocation);
        holder.position = position;}

    @Override
    public int getItemCount() {
        return mSearchLocation.size();
    }

    public class SearchLocationViewHolder extends RecyclerView.ViewHolder{
        EachSearchedLocationBinding binding;
        int position;
        public SearchLocationViewHolder(@NonNull EachSearchedLocationBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.eachsearchedlocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Chosen Location", "onClick: "+String.valueOf(mSearchLocation.get(position).id));
                    context.ChosenLocation(mSearchLocation.get(position).name);
                }
            });
        }

    }
}
