package com.example.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {
    private Context context;
    private List<ConsolidatedWeather> weatherList;

    public WeatherAdapter(Context context, List<ConsolidatedWeather> weatherList) {
        this.context = context;
        this.weatherList = weatherList;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WeatherViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_weather_timeline, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        ConsolidatedWeather weather = weatherList.get(position);

        String abbr = weather.getWeatherStateAbbr();
        Double theTemp = weather.getTheTemp();


        Picasso.get()
                .load("https://www.metaweather.com/static/img/weather/png/64/" + abbr + "png")
                .into(holder.tempImage);
        holder.temp.setText(String.valueOf(theTemp));
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    class WeatherViewHolder extends RecyclerView.ViewHolder {
        TextView temp;
        ImageView tempImage;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);

            temp = itemView.findViewById(R.id.temp);
            tempImage = itemView.findViewById(R.id.tempImage);
        }
    }
}
