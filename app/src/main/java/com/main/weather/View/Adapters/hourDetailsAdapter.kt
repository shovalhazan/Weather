package com.main.weather.View.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.main.weather.R
import com.main.weather.Repository.Model.CityWeatherModel

class hourDetailsAdapter : RecyclerView.Adapter<forcaseViewHolder>() {
    var dataSet = ArrayList<CityWeatherModel>()
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): forcaseViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.adapter_forecase_row, viewGroup, false)
        return forcaseViewHolder(view)
    }
    override fun getItemCount() = dataSet.size
    override fun onBindViewHolder(holder: forcaseViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }
}