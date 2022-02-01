package com.main.weather.View.Adapters
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.HttpException
import com.main.weather.R
import com.main.weather.Repository.Model.CityWeatherModel
import com.main.weather.View.Activities.CITY_PARCELABLE_KEY
import com.main.weather.View.Activities.WeatherDetailsActivity


class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    /**
     * wrapper around a view that contains the layout for an individual item  in the list,Defines the appearance of a display for each item.
     **/
    lateinit var LBL_cityName : TextView
    lateinit var LBL_degree : TextView
    lateinit var IMG_weatherImg : ImageView
    lateinit var thisView : View

    init {
        // Define click listener for the ViewHolder's View.
        LBL_cityName=view.findViewById(R.id.city_weather_row_LBL_cityName);
        IMG_weatherImg=view.findViewById(R.id.city_weather_row_IMG)
        LBL_degree=view.findViewById(R.id.city_weather_row_LBL_degree)
        this.thisView = view
    }


    fun bind(data : CityWeatherModel){
        Log.d("pttt", "bind: ")
        LBL_cityName.text = data.cityName
        LBL_degree.text = data.main_details?.fromKalvinToDeg(data.main_details?.temp!!).toString()+"\u00B0"
        loadImage(data)
    }

    private fun loadImage(data : CityWeatherModel){
        val imgUrl="http://openweathermap.org/img/w/" + data.weather!![0].icon + ".png"
        try{
            Glide.with(IMG_weatherImg)
                .load(imgUrl)
                .thumbnail(0.25f)
                .circleCrop()
                .into(IMG_weatherImg)
        }catch (e:HttpException){
            Log.d("pttt", "bind: "+e.message)
        }
    }


}