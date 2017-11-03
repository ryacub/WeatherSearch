package com.wip.rayyacub.weathersearch.Networking;


import com.wip.rayyacub.weathersearch.Model.Forecast;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//interface for different network calls
public interface ApiService {

    @GET("data/2.5/forecast/daily")
    Observable<Forecast> getWeatherObservable(@Query("q") String location, @Query("cnt") String dayCount, @Query("appid") String apiKey);

}
