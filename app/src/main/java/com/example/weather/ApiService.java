package com.example.weather;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("3534/")
    Call<Weather> getWeather();
}
