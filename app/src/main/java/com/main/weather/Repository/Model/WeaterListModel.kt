package com.main.weather.Repository.Model

import android.util.Log
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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
 ){
    fun convertTimeToDate(str:String): String {
        val timeD = Date(str.toLong() * 1000L)
        var Time :String=""
        try{
            val sdf = SimpleDateFormat("HH:mm:ss")
            Time = sdf.format(timeD)
        }catch (e:Exception){
            Log.d("pttt", "convertTimeToDate: "+e)
        }

        return Time
    }
}