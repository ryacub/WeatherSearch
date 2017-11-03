package com.wip.rayyacub.weathersearch.Adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wip.rayyacub.weathersearch.MainActivity;
import com.wip.rayyacub.weathersearch.Model.ListOfWeatherFeatures;
import com.wip.rayyacub.weathersearch.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherVH> {

    private MainActivity mainActivity;
    private List<ListOfWeatherFeatures> list = new ArrayList<>();
    private HashMap<String, Integer> iconMap = new HashMap<>();


    public WeatherAdapter(MainActivity mainActivity, List<ListOfWeatherFeatures> list) {
        this.mainActivity = mainActivity;
        this.list = list;
    }

    @Override
    public WeatherVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_weather, parent, false);
        return new WeatherVH(view);
    }

    @Override
    public void onBindViewHolder(WeatherVH holder, int position) {

        ListOfWeatherFeatures listOfWeatherFeatures = list.get(position);
        iconHashMapInit();
        Glide.with(mainActivity).load(iconMap.get(listOfWeatherFeatures.getWeather().get(0).getIcon())).into(holder.weatherIcon);
        holder.dayOfWeek.setText(listDay(String.valueOf(listOfWeatherFeatures.getDt())));
        holder.weatherStatus.setText(listOfWeatherFeatures.getWeather().get(0).getMain());
        holder.weatherHigh.setText(temperatureFormat(list.get(position).getTemp().getMax()));
        holder.weatherLow.setText(temperatureFormat(list.get(position).getTemp().getMin()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class WeatherVH extends RecyclerView.ViewHolder {
        @BindView(R.id.weatherIcon)
        ImageView weatherIcon;
        @BindView(R.id.day)
        TextView dayOfWeek;
        @BindView(R.id.weather_status)
        TextView weatherStatus;
        @BindView(R.id.weatherMax)
        TextView weatherHigh;
        @BindView(R.id.weatherMin)
        TextView weatherLow;

        public WeatherVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    private void iconHashMapInit() {
        iconMap.put("01d", R.drawable.ic_clear);
        iconMap.put("02d", R.drawable.ic_light_clouds);
        iconMap.put("03d", R.drawable.ic_cloudy);
        iconMap.put("04d", R.drawable.ic_cloudy);
        iconMap.put("09d", R.drawable.ic_light_rain);
        iconMap.put("10d", R.drawable.ic_rain);
        iconMap.put("11d", R.drawable.ic_storm);
        iconMap.put("13d", R.drawable.ic_snow);
        iconMap.put("50d", R.drawable.ic_fog);
        iconMap.put("01n", R.drawable.ic_clear);
        iconMap.put("02n", R.drawable.ic_light_clouds);
        iconMap.put("03n", R.drawable.ic_cloudy);
        iconMap.put("04n", R.drawable.ic_cloudy);
        iconMap.put("09n", R.drawable.ic_light_rain);
        iconMap.put("10n", R.drawable.ic_rain);
        iconMap.put("11n", R.drawable.ic_storm);
        iconMap.put("13n", R.drawable.ic_snow);
        iconMap.put("50n", R.drawable.ic_fog);
    }

    public String listDay(String dt) {
        Calendar mCalender = Calendar.getInstance();
        mCalender.add(Calendar.DAY_OF_MONTH, 1);
        Long timeStamp = Integer.valueOf(dt) * 1000L;
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(timeStamp);
    }

    public String temperatureFormat(Double temperature) {
        String temp = String.valueOf(temperature - 273);
        return temp.substring(0, temp.indexOf(".")) + (char) 0x00B0 + "C";
    }
}
