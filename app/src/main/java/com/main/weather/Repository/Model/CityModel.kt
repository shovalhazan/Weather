package com.main.weather.Repository.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CityModel (

        @SerializedName("sunrise")
        @Expose
        var sunrise :String,
        @SerializedName("sunset")
        @Expose
        var sunset :String
)