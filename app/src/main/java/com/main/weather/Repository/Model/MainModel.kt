package com.main.weather.Repository.Model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MainModel (
    @SerializedName("temp")
    @Expose
    var temp: Float,
    @SerializedName("feels_like")
    @Expose
    var feels_like: Float,
    @SerializedName("temp_min")
    @Expose
    var temp_min: Float,
    @SerializedName("temp_max")
    @Expose
    var temp_max: Float,
    @SerializedName("pressure")
    @Expose
    var pressure: Float,
    @SerializedName("humidity")
    @Expose
    var humidity: Int
):Parcelable
{
    constructor(parcel: Parcel) : this(
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readFloat(),
        parcel.readInt()
    ) {
    }

    fun fromKalvinToDeg(temp:Float): Int {
        return (temp-273.15).toInt();
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(temp)
        parcel.writeFloat(feels_like)
        parcel.writeFloat(temp_min)
        parcel.writeFloat(temp_max)
        parcel.writeFloat(pressure)
        parcel.writeInt(humidity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MainModel> {
        override fun createFromParcel(parcel: Parcel): MainModel {
            return MainModel(parcel)
        }
        override fun newArray(size: Int): Array<MainModel?> {
            return arrayOfNulls(size)
        }
    }




    override fun toString(): String {
        val str ="TEMPERATURE DETAILS:\n\n"+"current temperature: "+fromKalvinToDeg(temp).toString()+"\u00B0\n"+
                "feels like: "+fromKalvinToDeg(feels_like).toString()+"\u00B0\n"+"minimum temperature: "+fromKalvinToDeg(temp_min).toString()+"\u00B0\n"+
                "maximum temperature: "+fromKalvinToDeg(temp_max).toString()+"\u00B0\n"+
                "pressure: "+fromKalvinToDeg(pressure).toString()+"\nhumidity: "+humidity +"%"
        return str
    }
}