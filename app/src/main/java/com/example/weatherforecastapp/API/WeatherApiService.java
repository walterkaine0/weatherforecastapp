package com.example.weatherforecastapp.API;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import com.example.weatherforecastapp.Model.weatherBased;

public interface WeatherApiService {

    @GET("data/2.5/forecast")
    Call<weatherBased> getForecast(
            @Query("q") String cityName,
            @Query("appid") String apiKey,
            @Query("units") String units,
            @Query("lang") String lang
    );

    @GET("data/2.5/forecast")
    Call<weatherBased> getForecastByCoordinates(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("appid") String apiKey,
            @Query("units") String units,
            @Query("lang") String lang
    );
}
