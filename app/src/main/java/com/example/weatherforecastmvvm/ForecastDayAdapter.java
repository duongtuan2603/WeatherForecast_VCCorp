package com.example.weatherforecastmvvm;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weatherforecastmvvm.databinding.EachDayLayoutBinding;

import java.util.List;

public class ForecastDayAdapter extends RecyclerView.Adapter<ForecastDayAdapter.ForecastDayViewHolder> {
    List<ForecastDay> mForecastDay;
    Context context;
    SharedPreferences sharedPreferences;
    String finaltemp;

    public ForecastDayAdapter(List<ForecastDay> mForecastDay, Context context) {
        this.mForecastDay = mForecastDay;
        this.context = context;
    }

    @NonNull
    @Override
    public ForecastDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        sharedPreferences = context.getSharedPreferences("com.example.weatherforecastmvvm",Context.MODE_PRIVATE);
        finaltemp = sharedPreferences.getString("Temperature Unit","\u2103");
        EachDayLayoutBinding eachDayLayoutBinding = DataBindingUtil.inflate(inflater,R.layout.each_day_layout,parent,false);
        return new ForecastDayViewHolder(eachDayLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastDayAdapter.ForecastDayViewHolder holder, int position) {
        ForecastDay forecastDay = mForecastDay.get(position);
        holder.eachDayLayoutBinding.setForecastday(forecastDay);
        if(finaltemp.equals("\u2103")){holder.eachDayLayoutBinding.eachdaytemp.setText(forecastDay.day.avgtemp_c+finaltemp);}
        else {holder.eachDayLayoutBinding.eachdaytemp.setText(forecastDay.day.avgtemp_f+finaltemp);}
        ImageView img = holder.eachDayLayoutBinding.imgeachdayweatherimage;
        Glide.with(context).load("http:"+forecastDay.day.condition.icon).into(img);

    }

    @Override
    public int getItemCount() {
        return mForecastDay.size();
    }

    public class ForecastDayViewHolder extends RecyclerView.ViewHolder{
        EachDayLayoutBinding eachDayLayoutBinding;
        public ForecastDayViewHolder(@NonNull EachDayLayoutBinding eachDayLayoutBinding) {
            super(eachDayLayoutBinding.getRoot());
            this.eachDayLayoutBinding = eachDayLayoutBinding;
        }
    }
}
