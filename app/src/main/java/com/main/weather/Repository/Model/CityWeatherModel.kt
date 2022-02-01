package com.main.weather.Repository.Model

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class CityWeatherModel(
    @Expose
    @SerializedName("dt")
    var date: String?,
    @SerializedName("main")
    @Expose
    var main_details: MainModel?,
    @SerializedName("weather")
    @Expose
    var weather: ArrayList<WeatherModel>?,
    @SerializedName("wind")
    @Expose
    var wind: WindModel?,
    @Expose
    @SerializedName("name")
    var cityName: String?,
    @Expose
    @SerializedName("dt_txt")
    var dt_txt: String?

):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(MainModel::class.java.classLoader),
        parcel.readArrayList(WeatherModel::class.java.classLoader) as ArrayList<WeatherModel>?,
        parcel.readParcelable(WindModel::class.java.classLoader),
        parcel.readString(),
        parcel.readString()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(date)
        parcel.writeParcelable(main_details, flags)
        parcel.writeList(weather)
        parcel.writeParcelable(wind, flags)
        parcel.writeString(cityName)
        parcel.writeString(dt_txt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CityWeatherModel> {
        override fun createFromParcel(parcel: Parcel): CityWeatherModel {
            return CityWeatherModel(parcel)
        }

        override fun newArray(size: Int): Array<CityWeatherModel?> {
            return arrayOfNulls(size)
        }
    }
    fun getHour(): Int {
        lateinit var myDate:Date
        Log.d("pttt", "getHour: "+dt_txt)
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        if(dt_txt!=null){
            try{
                myDate = format.parse(dt_txt)
            }catch(e:Exception){
                Log.d("pttt", "getHour: "+e)
            }
        }

        Log.d("pttt", "getHour: " + myDate.hours)
        return myDate.hours
    }
}



