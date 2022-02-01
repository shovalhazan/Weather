package com.main.weather.View.Adapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.HttpException
import com.main.weather.R
import com.main.weather.Repository.Model.CityWeatherModel

class forcaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    lateinit var LBL_date : TextView
    lateinit var IMG_weatherImg : ImageView
    lateinit var LBL_degree : TextView
    init {
        // Define click listener for the ViewHolder's View.
        LBL_date=view.findViewById(R.id.hour_row_LBL_date);
        IMG_weatherImg=view.findViewById(R.id.hour_row_IMG)
        LBL_degree=view.findViewById(R.id.hour_row_LBL_degree)
    }


    fun bind(data : CityWeatherModel){
        Log.d("pttt", "bind: ")
        LBL_date.text = data.dt_txt
        LBL_degree.text = data.main_details?.fromKalvinToDeg(data.main_details?.temp!!).toString()+"\u00B0"

        loadImage(data.weather!![0].icon!!)
    }

    private fun loadImage(data : String){
        val imgUrl="http://openweathermap.org/img/w/" + data + ".png"
        try{
            Glide.with(IMG_weatherImg)
                .load(imgUrl)
                .thumbnail(0.25f)
                .circleCrop()
                .into(IMG_weatherImg)
        }catch (e: HttpException){
            Log.d("pttt", "bind: "+e.message)
        }
    }

}