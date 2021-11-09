package com.example.adamdeilsinki;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherAPI {
    @GET("weather")
    Call<Example> getWeather(@Query("q") String cityName,
                             @Query("appid") String apikey );

}