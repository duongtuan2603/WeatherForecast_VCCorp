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
import com.example.weatherforecastmvvm.data.model.getapiforecast.ForecastDay;
import com.example.weatherforecastmvvm.databinding.EachDayLayoutBinding;

import java.util.List;

public class ForecastDayAdapter extends RecyclerView.Adapter<ForecastDayAdapter.ForecastDayViewHolder> {
    List<ForecastDay> mForecastDay;
    Context mContext;
    String mFinalTemp;

    public ForecastDayAdapter(List<ForecastDay> mForecastDay, Context context) {
        this.mForecastDay = mForecastDay;
        this.mContext = context;
        mFinalTemp = SharedPreferenceSettings.getSharedPreferenceSettings(mContext.getApplicationContext()).getTemperatureunit();

    }

    @NonNull
    @Override
    public ForecastDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        EachDayLayoutBinding eachDayLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.each_day_layout, parent, false);
        return new ForecastDayViewHolder(eachDayLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastDayAdapter.ForecastDayViewHolder holder, int position) {
        ForecastDay forecastDay = mForecastDay.get(position);
        holder.eachDayLayoutBinding.setForecastday(forecastDay);
        if (mFinalTemp.equals("\u2103")) {
            holder.eachDayLayoutBinding.eachdaytemp.setText(forecastDay.getDay().getAvgtemp_c() + mFinalTemp);
        } else {
            holder.eachDayLayoutBinding.eachdaytemp.setText(forecastDay.getDay().getAvgtemp_f() + mFinalTemp);
        }
        ImageView img = holder.eachDayLayoutBinding.imgeachdayweatherimage;
        Glide.with(mContext).load("http:" + forecastDay.getDay().getCondition().getIcon()).into(img);

    }

    @Override
    public int getItemCount() {
        return mForecastDay.size();
    }

    public static class ForecastDayViewHolder extends RecyclerView.ViewHolder {
        EachDayLayoutBinding eachDayLayoutBinding;

        public ForecastDayViewHolder(@NonNull EachDayLayoutBinding eachDayLayoutBinding) {
            super(eachDayLayoutBinding.getRoot());
            this.eachDayLayoutBinding = eachDayLayoutBinding;
        }
    }
}
