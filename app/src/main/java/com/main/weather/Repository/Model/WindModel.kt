package com.main.weather.Repository.Model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WindModel(
        @SerializedName("speed")
        @Expose
        var speed : Float,
        @SerializedName("deg")
        @Expose
        var deg : Int,
        @SerializedName("gust")
        @Expose
        var gust:Float
):Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readFloat(),
                parcel.readInt(),
                parcel.readFloat()
        ) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeFloat(speed)
                parcel.writeInt(deg)
                parcel.writeFloat(gust)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<WindModel> {
                override fun createFromParcel(parcel: Parcel): WindModel {
                        return WindModel(parcel)
                }

                override fun newArray(size: Int): Array<WindModel?> {
                        return arrayOfNulls(size)
                }
        }

        override fun toString(): String {
                val str ="WIND :\n\nSPEED: "+speed+"\nDEG: "+deg+"\nGUST: "+gust
                return str
        }
}