package com.example.datashop.Weather.Api

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private val retorfit=Retrofit.Builder().baseUrl("https://api.weatherapi.com").addConverterFactory(GsonConverterFactory.create()).build()
val WeatherService= retorfit.create(WeatherApi::class.java)

interface WeatherApi {
    @GET("/v1/current.json")
    suspend fun getWeather(
        @Query("key")apiKey:String,
        @Query("q")city:String):WeatherModel
}