package com.main.weather.Repository.Model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
//"http://openweathermap.org/img/w/" + iconcode + ".png";
data class WeatherModel(
        @SerializedName("id")
        @Expose
        var id:Int,
        @SerializedName("main")
        @Expose
        var main: String?,
        @SerializedName("description")
        @Expose
        var description: String?,
        @SerializedName("icon")
        @Expose
        var icon: String?
):Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeInt(id)
                parcel.writeString(main)
                parcel.writeString(description)
                parcel.writeString(icon)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<WeatherModel> {
                override fun createFromParcel(parcel: Parcel): WeatherModel {
                        return WeatherModel(parcel)
                }

                override fun newArray(size: Int): Array<WeatherModel?> {
                        return arrayOfNulls(size)
                }
        }

        override fun toString(): String {
                val str="MAIN DETAILS:\n\n"+main+"\n"+description
                return str
        }
}