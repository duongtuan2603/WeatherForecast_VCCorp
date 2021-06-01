package com.example.weatherforecastmvvm.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherforecastmvvm.R;
import com.example.weatherforecastmvvm.data.local.SharedPreferenceSettings;
import com.example.weatherforecastmvvm.data.model.getapiforecast.Hour;
import com.example.weatherforecastmvvm.databinding.EachHourLayoutBinding;
import com.example.weatherforecastmvvm.ui.activities.MainActivity;

import java.util.List;

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder> {
    List<Hour> mHour;
    Context mContext;
    String mFinalTemp;

    public HourAdapter(List<Hour> mHour, MainActivity context) {
        this.mHour = mHour;
        this.mContext = context;
        mFinalTemp = SharedPreferenceSettings.getSharedPreferenceSettings(mContext.getApplicationContext()).getTemperatureunit();
    }

    @NonNull
    @Override
    public HourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        EachHourLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.each_hour_layout, parent, false);
        return new HourViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HourAdapter.HourViewHolder holder, int position) {
        Hour hour = mHour.get(position);
        holder.hourLayoutBinding.setHour(hour);
        ImageView img = holder.hourLayoutBinding.imgeachhour;
        if (mFinalTemp.equals("\u2103")) {
            holder.hourLayoutBinding.txteachhourtemp.setText(hour.getTemp_c() + "\u2103");
        } else {
            holder.hourLayoutBinding.txteachhourtemp.setText(hour.getTemp_f() + "\u2109");
        }
        Glide.with(mContext).load("http:" + hour.getCondition().getIcon()).into(img);

    }

    @Override
    public int getItemCount() {
        return mHour.size();
    }

    public static class HourViewHolder extends RecyclerView.ViewHolder {
        EachHourLayoutBinding hourLayoutBinding;

        public HourViewHolder(@NonNull EachHourLayoutBinding hourlayoutbinding) {
            super(hourlayoutbinding.getRoot());
            this.hourLayoutBinding = hourlayoutbinding;
        }
    }
}
