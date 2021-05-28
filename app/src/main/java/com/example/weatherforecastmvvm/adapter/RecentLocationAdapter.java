package com.example.weatherforecastmvvm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherforecastmvvm.getapilocation.RecentLocation;
import com.example.weatherforecastmvvm.R;
import com.example.weatherforecastmvvm.databinding.EachRecentLocationBinding;
import com.example.weatherforecastmvvm.getapilocation.SavedLocation;

import java.util.List;

public class RecentLocationAdapter extends RecyclerView.Adapter<RecentLocationAdapter.RecentLocationViewHolder> {
    List<SavedLocation> mSavedLocation;
    Context mContext;

    public RecentLocationAdapter(List<SavedLocation> mRecentLocation, Context mContext) {
        this.mSavedLocation = mRecentLocation;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecentLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        EachRecentLocationBinding mBinding = DataBindingUtil.inflate(inflater, R.layout.each_recent_location,parent,false);
        return new RecentLocationViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentLocationViewHolder holder, int position) {
        RecentLocation recentLocation = new RecentLocation(mSavedLocation.get(mSavedLocation.size()-1-position).getLocationname());
        holder.binding.setRecentlocation(recentLocation);

    }

    @Override
    public int getItemCount() {
        return mSavedLocation.size();
    }

    public class RecentLocationViewHolder extends RecyclerView.ViewHolder {
        EachRecentLocationBinding binding;
        public RecentLocationViewHolder(@NonNull EachRecentLocationBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
