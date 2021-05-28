package com.example.weatherforecastmvvm.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforecastmvvm.R;
import com.example.weatherforecastmvvm.Screens.AddLocationActivity;
import com.example.weatherforecastmvvm.SearchLocation;
import com.example.weatherforecastmvvm.databinding.EachSearchedLocationBinding;

import java.util.List;

public class SearchLocationAdapter extends RecyclerView.Adapter<SearchLocationAdapter.SearchLocationViewHolder> {
    List<SearchLocation> mSearchLocation;
    AddLocationActivity mContext;

    public SearchLocationAdapter(List<SearchLocation> mSearchLocation, AddLocationActivity context) {
        this.mSearchLocation = mSearchLocation;
        this.mContext = context;
    }

    @NonNull
    @Override
    public SearchLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        EachSearchedLocationBinding binding = DataBindingUtil.inflate(inflater, R.layout.each_searched_location, parent, false);
        return new SearchLocationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchLocationAdapter.SearchLocationViewHolder holder, int position) {
        SearchLocation searchLocation = mSearchLocation.get(position);
        holder.binding.setSearchedlocation(searchLocation);
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return mSearchLocation.size();
    }

    public class SearchLocationViewHolder extends RecyclerView.ViewHolder {
        EachSearchedLocationBinding binding;
        int position;

        public SearchLocationViewHolder(@NonNull EachSearchedLocationBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.eachsearchedlocation.setOnClickListener(v -> {
                Log.e("Chosen Location", "onClick: " + mSearchLocation.get(position).getId());
                mContext.ChosenLocation(mSearchLocation.get(position).getName());
            });
        }

    }
}
