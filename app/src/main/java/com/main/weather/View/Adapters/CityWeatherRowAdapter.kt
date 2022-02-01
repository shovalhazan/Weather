package com.main.weather.View.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.main.weather.R
import com.main.weather.Repository.Model.CityWeatherModel

class CityWeatherRowAdapter(val chosenCity: MutableLiveData<CityWeatherModel>) : RecyclerView.Adapter<ViewHolder>() {
    var dataSet = ArrayList<CityWeatherModel>()
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_city_row, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(dataSet[position])
        viewHolder.thisView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                chosenCity.postValue(dataSet[position])
                Log.d("pttt", "onClick: "+dataSet[position])
            }
        })
    }

    override fun getItemCount() = dataSet.size
}