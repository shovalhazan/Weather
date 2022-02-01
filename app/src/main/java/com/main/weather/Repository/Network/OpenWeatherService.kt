package com.main.weather.Repository.Network
import com.main.weather.Repository.Model.CityWeatherModel
import com.main.weather.Repository.Model.WeaterListModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {
    @GET("weather")
    fun getCurrentWeatherByCord(@Query("lat") cityLatitude: Double, @Query("lon") cityLongitude:Double, @Query("appid") appid:String ) : Observable<CityWeatherModel>

    @GET("forecast")
    fun getWeatherListByName(@Query("q") cityName:String,@Query("appid") appid:String ): Observable<WeaterListModel>
}