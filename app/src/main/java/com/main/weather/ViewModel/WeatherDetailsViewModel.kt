package com.main.weather.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.main.weather.Repository.Model.WeaterListModel
import com.main.weather.Repository.Network.OpenWeatherService
import com.main.weather.Repository.Network.RetrofitHelper
import com.main.weather.Repository.Network.WEATHER_API_KEY
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers


class WeatherDetailsViewModel : ViewModel() {

    val retrofitInstance = RetrofitHelper.getInstance().create(OpenWeatherService::class.java)

    val _weatherListLiveData = MutableLiveData<WeaterListModel>()


    fun getWeatherListByName(cityName:String){
        try {
            retrofitInstance.getWeatherListByName(cityName, WEATHER_API_KEY)
                .distinctUntilChanged()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response -> onResponse(response) }, { t -> onFailure(t) })
        }catch (t:Throwable){
        }
    }

    private fun onFailure(t: Throwable?) {
        if (t != null) {
            Log.d("pttt", "onFailure: "+t.message)
        }
    }

    private fun onResponse(response: WeaterListModel?) {
        Log.d("pttt", "onResponse: "+response.toString())
        if(response?.requestCode=="200"){
            _weatherListLiveData.value=response!!
        }
    }
}