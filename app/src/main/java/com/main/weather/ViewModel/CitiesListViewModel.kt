package com.main.weather.ViewModel

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.main.weather.Repository.Model.CityWeatherModel
import com.main.weather.Repository.Network.OpenWeatherService
import com.main.weather.Repository.Network.RetrofitHelper
import com.main.weather.Repository.Network.WEATHER_API_KEY
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Function4
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException

val NEWYORK_CORD= arrayOf(40.7143, -74.006)
val LONDON= arrayOf(51.5085,-0.1257)
val MIAMI= arrayOf(25.7743,-80.1937)

class CitiesListViewModel  : ViewModel() {

    val retrofitInstance = RetrofitHelper.getInstance().create(OpenWeatherService::class.java)
    val _citiesWeatherLiveData: MutableLiveData<ArrayList<CityWeatherModel>> = MutableLiveData()
    val chosenCity:MutableLiveData<CityWeatherModel> = MutableLiveData()
    val locationLiveData: MutableLiveData<Location> = MutableLiveData()

    fun getMyCitiesList(lat:Double,lon:Double) {
        Log.d("pttt", "getHeroesConnectionsById: ")
        try {
            val o1: Observable<CityWeatherModel> =retrofitInstance.getCurrentWeatherByCord(NEWYORK_CORD[0], NEWYORK_CORD[1], WEATHER_API_KEY).subscribeOn(Schedulers.io())
            val o2: Observable<CityWeatherModel> =retrofitInstance.getCurrentWeatherByCord(LONDON[0],LONDON[1], WEATHER_API_KEY).subscribeOn(Schedulers.io())
            val o3: Observable<CityWeatherModel> =retrofitInstance.getCurrentWeatherByCord(MIAMI[0],MIAMI[1], WEATHER_API_KEY).subscribeOn(Schedulers.io())
            val o4: Observable<CityWeatherModel> =retrofitInstance.getCurrentWeatherByCord(lat,lon, WEATHER_API_KEY).subscribeOn(Schedulers.io())
            Observable.zip(o4,o2,o3,o1,
                Function4<CityWeatherModel, CityWeatherModel, CityWeatherModel, CityWeatherModel,ArrayList<CityWeatherModel>>{ a, b, c, d -> onResponse(a,b,c,d)})
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ temp_list ->  _citiesWeatherLiveData.postValue(temp_list);chosenCity.postValue(temp_list[0])},
                    { t ->  Log.d("pttt", "onFailure: FROM ZIP "+t.toString()+" "+t.stackTrace) ;  })
        } catch (e: HttpException){
            e.stackTrace
        }
    }

    private fun onResponse(a: CityWeatherModel, b: CityWeatherModel, c: CityWeatherModel, d: CityWeatherModel): ArrayList<CityWeatherModel> {
        val temp =ArrayList<CityWeatherModel>()

        temp.add(a)
        temp.add(b)
        temp.add(c)
        temp.add(d)
        Log.d("pttt", "onResponse: "+temp.toString())
        Log.d("pttt", "onResponse: "+a+" "+b+" "+c+" "+d)

        return temp;
    }

}