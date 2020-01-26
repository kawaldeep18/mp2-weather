package com.example.weather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class WeatherFragment extends Fragment {
    private View root;
    private TextView cityName, temp, airPressure, airPressureText, humidity, humidityText, maxTemp, maxTempText, minTemp, minTempText;
    private ImageView tempImage;
    private RecyclerView recyclerView;
    private WeatherAdapter adapter;
    private List<ConsolidatedWeather> weatherList = new ArrayList<>();
    private Weather weather;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_weather, container, false);

        cityName = root.findViewById(R.id.cityName);
        temp = root.findViewById(R.id.temp);
        airPressure = root.findViewById(R.id.airPressure);
        airPressureText = root.findViewById(R.id.airPressureText);
        humidity = root.findViewById(R.id.humidity);
        humidityText = root.findViewById(R.id.humidityText);
        maxTemp = root.findViewById(R.id.maxTemp);
        maxTempText = root.findViewById(R.id.maxTempText);
        minTemp = root.findViewById(R.id.minTemp);
        minTempText = root.findViewById(R.id.minTempText);
        tempImage = root.findViewById(R.id.tempImage);
        recyclerView = root.findViewById(R.id.recyclerView);

        ApiService api = RetrofitClient.getApiService();
        Call<Weather> call = api.getWeather();


        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if (response.body().getConsolidatedWeather() != null) {
                    weatherList = response.body().getConsolidatedWeather();
                    cityName.setText(response.body().getTitle());
                    temp.setText(String.valueOf(response.body().getConsolidatedWeather().get(0).getTheTemp()));
                    adapter = new WeatherAdapter(root.getContext(), weatherList);
                    airPressureText.setText(String.valueOf(response.body().getConsolidatedWeather().get(0).getAirPressure()));
                    humidityText.setText(String.valueOf(response.body().getConsolidatedWeather().get(0).getHumidity()));
                    maxTempText.setText(String.valueOf(response.body().getConsolidatedWeather().get(0).getMaxTemp()));
                    minTempText.setText(String.valueOf(response.body().getConsolidatedWeather().get(0).getMinTemp()));

                    LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(root.getContext(), "Weather info couldn't be fetched", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Toast.makeText(root.getContext(), "There was a problem fetching the details. Please try again later...", Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }

}
