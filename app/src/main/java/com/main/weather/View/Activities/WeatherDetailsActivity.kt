package com.main.weather.View.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.main.weather.R
import com.main.weather.Repository.Model.CityWeatherModel
import com.main.weather.Repository.Model.WeaterListModel
import com.main.weather.View.Adapters.hourDetailsAdapter
import com.main.weather.ViewModel.WeatherDetailsViewModel

const val CITY_PARCELABLE_KEY = "cityDetails"

class WeatherDetailsActivity : AppCompatActivity() {
    private lateinit var rowAdapter : hourDetailsAdapter
    private lateinit var viewModel: WeatherDetailsViewModel
    private lateinit var LBL_cityName: TextView
    private lateinit var LBL_Temperatures: TextView
    private lateinit var LBL_sunsetTime: TextView
    private lateinit var LBL_sunriseTime: TextView
    private lateinit var LST_today: RecyclerView
    private lateinit var prograssBar: ProgressBar
    private var extras :CityWeatherModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_details)
        viewModel= ViewModelProvider(this).get(WeatherDetailsViewModel::class.java)

        extras =intent.getParcelableExtra(CITY_PARCELABLE_KEY)
        findViews()
        initRecyclerView()
       // initViews(extras)
        if(extras!=null){
            Log.d("pttt", "onCreate: extra !=null ")
            loadAPIData(extras!!.cityName!!)
            updateCurentCity()
            loadListObserver()
        }
    }

    private fun loadListObserver() {
        viewModel._weatherListLiveData.observe(this, Observer<WeaterListModel> {
            if(it!=null &&it.requestCode=="200"){
                setAdapter(it.weatherList)
                LBL_sunsetTime.text =it.city.sunset
                LBL_sunriseTime.text =it.city.sunrise
                prograssBar.visibility= View.GONE
            }
        })
    }
    private fun findViews() {
        LBL_cityName = findViewById(R.id.detailsActivity_LBL_cityName)
        LBL_Temperatures = findViewById(R.id.detailsActivity_LBL_Temperatures)
        LBL_sunsetTime = findViewById(R.id.detailsActivity_LBL_sunsetTime)
        LBL_sunriseTime = findViewById(R.id.detailsActivity_LBL_sunriseTime)
        LST_today = findViewById(R.id.detailsActivity_LST_week)
        prograssBar=findViewById(R.id.detailsActivity_list_ProgressBar)
    }
    private fun setAdapter(results: ArrayList<CityWeatherModel>) {
        rowAdapter.dataSet = results
        rowAdapter.notifyDataSetChanged()
    }

    private fun initRecyclerView() {
        LST_today.apply {
            layoutManager = LinearLayoutManager(this.context)
            val decoration  = DividerItemDecoration(this.context.applicationContext, LinearLayout.HORIZONTAL)
            addItemDecoration(decoration)
            rowAdapter = hourDetailsAdapter()
            adapter = rowAdapter
        }
    }

    private fun updateCurentCity() {
        LBL_cityName.text =extras!!.cityName
        LBL_Temperatures.text = extras!!.main_details!!.fromKalvinToDeg( extras!!.main_details!!.temp).toString()+ "\u00B0"
    }

    private fun loadAPIData(city: String) {
        prograssBar.visibility=View.VISIBLE
        viewModel.getWeatherListByName(city)
    }


}