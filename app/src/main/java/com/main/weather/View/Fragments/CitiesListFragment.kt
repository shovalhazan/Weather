package com.main.weather.View.Fragments

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.button.MaterialButton
import com.main.weather.R
import com.main.weather.Repository.Model.CityWeatherModel
import com.main.weather.Utils.GetLocation
import com.main.weather.View.Activities.CITY_PARCELABLE_KEY
import com.main.weather.View.Activities.WeatherDetailsActivity
import com.main.weather.View.Adapters.CityWeatherRowAdapter
import com.main.weather.View.Adapters.PagerAdapter
import com.main.weather.ViewModel.CitiesListViewModel


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

class CitiesListFragment : Fragment() {
    private lateinit var LST_searchListResult: RecyclerView
    private lateinit var rowAdapter : CityWeatherRowAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var LBL_msg: TextView
    private lateinit var BTN_forcase: MaterialButton
    private lateinit var LBL_title: TextView
    private lateinit var viewPager: ViewPager
    private lateinit var viewModel: CitiesListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("pttt", "onCreate: ")
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view:View? = inflater.inflate(R.layout.fragment_cities_list, container, false)
        viewModel= ViewModelProvider(this).get(CitiesListViewModel::class.java)
        findViews(view)
        initViews()
        loadApiData()
        loadListObserver()
        loadChosenObserver()
        return view
    }

    private fun initViews() {
        initRecyclerView()
        BTN_forcase.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                openDetailsActivity()
            }

        })
    }

    private fun loadChosenObserver() {
       viewModel.chosenCity.observe(viewLifecycleOwner, Observer<CityWeatherModel> {
           if (it != null) {
               Log.d("pttt", "loadChosenObserver: "+it)
               LBL_title.visibility=View.VISIBLE
               LBL_title.text=it.cityName
               val detailsList=createListOfDetails(it);
               val pagerAdapter= PagerAdapter(this.requireContext(), detailsList)
               viewPager.adapter = pagerAdapter
               (viewPager.adapter as PagerAdapter).notifyDataSetChanged()
           }
       })
    }

    private fun createListOfDetails(it: CityWeatherModel): ArrayList<String> {
            val list =ArrayList<String>()
            list.add(it.main_details.toString())
            list.add(it.weather!![0].toString())
            list.add(it.wind.toString())
            return list
    }

    private fun loadListObserver() {
        viewModel._citiesWeatherLiveData.observe(viewLifecycleOwner,Observer<ArrayList<CityWeatherModel>>{
            if(it!=null){
                LBL_msg.visibility=View.INVISIBLE
                progressBar.visibility = View.INVISIBLE
                LST_searchListResult.visibility = View.VISIBLE
                setAdapter(it)
            }
        })
    }

    private fun loadApiData(){
        this.context?.let { GetLocation.gatLastLocation(viewModel.locationLiveData, it) }
        viewModel.locationLiveData.observe(viewLifecycleOwner, Observer<Location> {
            if (it != null) {
                viewModel.getMyCitiesList(it.latitude, it.longitude)
                Log.d("pttt", "onActivityCreated: "+it.toString())
            }
        })
    }

    private fun setAdapter(results: ArrayList<CityWeatherModel>) {
        rowAdapter.dataSet = results
        rowAdapter.notifyDataSetChanged()
    }
    private fun findViews(view: View?) {
        if (view != null) {
            LST_searchListResult =view.findViewById(R.id.fragment_recycler_LST_searchList)
            progressBar = view.findViewById(R.id.fragment_recycler_list_ProgressBar)
            LBL_msg=view.findViewById(R.id.fragment_recycler_list_LBL_msg)
            BTN_forcase=view.findViewById(R.id.fragment_recycler_BTN_forcase)
            LBL_title=view.findViewById(R.id.fragment_recycler_list_LBL_title)
            viewPager=view.findViewById(R.id.fragment_recycler_Pager)
        }
    }

    private fun initRecyclerView() {
        LST_searchListResult.visibility= View.INVISIBLE
        LST_searchListResult.apply {
            layoutManager = LinearLayoutManager(this.context)
            val decoration  = DividerItemDecoration(this.context.applicationContext, LinearLayout.VERTICAL)
            addItemDecoration(decoration)
            rowAdapter = CityWeatherRowAdapter(viewModel.chosenCity)
            adapter = rowAdapter
        }
    }

    private fun openDetailsActivity(){
        val intent : Intent = Intent(this.context, WeatherDetailsActivity::class.java)
        intent.putExtra(CITY_PARCELABLE_KEY,viewModel.chosenCity.value)
        context?.startActivity(intent)
    }
}