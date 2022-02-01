package com.main.weather.Repository.Network
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val WEATHER_API_KEY :String = "eeb3e5c42fb6fa4c2dabb8ad1dc50d63"
const val HTTPS_OPEN_WEATHER_API_BASE_URL :String ="https://api.openweathermap.org/data/2.5/"
object RetrofitHelper {
    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(HTTPS_OPEN_WEATHER_API_BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}