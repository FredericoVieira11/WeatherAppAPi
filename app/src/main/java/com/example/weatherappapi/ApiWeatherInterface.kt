package com.example.weatherappapi

import com.example.weatherappapi.model.ApiWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiWeatherInterface {
    @GET("data/2.5/weather?")
    fun getCurrentWeatherByCity(@Query("q") city: String,
                                @Query("units") units: String,
                                @Query("APPID") appId: String): retrofit2.Call<ApiWeather>

}