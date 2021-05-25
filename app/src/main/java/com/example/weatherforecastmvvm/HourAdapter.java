package com.example.weatherforecastmvvm;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherforecastmvvm.databinding.EachHourLayoutBinding;

import java.util.List;

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder> {
    List<Hour> mHour;
    Context context;
    SharedPreferences sharedPreferences;
    String filaltemp;

    public HourAdapter(List<Hour> mHour, MainActivity context) {
        this.mHour = mHour;
        this.context = context;
    }

    @NonNull
    @Override
    public HourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        sharedPreferences = context.getSharedPreferences("com.example.weatherforecastmvvm",Context.MODE_PRIVATE);
        filaltemp = sharedPreferences.getString("Temperature Unit","\u2103");
        EachHourLayoutBinding binding = DataBindingUtil.inflate(inflater,R.layout.each_hour_layout,parent,false);
        return new HourViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HourAdapter.HourViewHolder holder, int position) {
        Hour hour = mHour.get(position);
        holder.hourLayoutBinding.setHour(hour);
        ImageView img = holder.hourLayoutBinding.imgeachhour;
        if (filaltemp.equals("\u2103")){holder.hourLayoutBinding.txteachhourtemp.setText(hour.temp_c+"\u2103");}
        else {holder.hourLayoutBinding.txteachhourtemp.setText(hour.temp_f+"\u2109");}
        Glide.with(context).load("http:"+hour.condition.icon).into(img);

    }

    @Override
    public int getItemCount() {
        return mHour.size();
    }

    public class HourViewHolder extends RecyclerView.ViewHolder{
        EachHourLayoutBinding hourLayoutBinding;
        public HourViewHolder(@NonNull EachHourLayoutBinding hourlayoutbinding) {
            super(hourlayoutbinding.getRoot());
            this.hourLayoutBinding = hourlayoutbinding;
        }
    }
}
