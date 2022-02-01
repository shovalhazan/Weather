package com.main.weather.Repository.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeaterListModel (
    @SerializedName("cod")
    @Expose
    var requestCode :String ,
    @SerializedName("message")
    @Expose
    var message :String ,
    @SerializedName("list")
    @Expose
    var weatherList :ArrayList<CityWeatherModel>,
    @SerializedName("city")
    @Expose
    var city :CityModel ,
 )