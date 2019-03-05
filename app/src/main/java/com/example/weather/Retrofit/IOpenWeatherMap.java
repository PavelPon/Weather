package com.example.weather.Retrofit;

import com.example.weather.Model.WeatherResult;
import com.example.weather.Model.FiveDayModel.WeatherForcastResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherMap {

    @GET("weather")
    Observable<WeatherResult> getWeatherByLatLng(@Query("lat") String lat,
                                                 @Query("lon") String lnt,
                                                 @Query("appid")String appid,
                                                 @Query("units")String unit);

    @GET("forecast")
    Observable<WeatherForcastResult> getWeatherForcastByLatLng(@Query("lat") String lat,
                                                               @Query("lon") String lnt,
                                                               @Query("appid")String appid,
                                                               @Query("units")String unit);
}
