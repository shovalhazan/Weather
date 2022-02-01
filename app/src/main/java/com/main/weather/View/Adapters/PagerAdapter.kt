package com.main.weather.View.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.main.weather.R

class PagerAdapter(val context : Context, val cardList : ArrayList<String>) : PagerAdapter() {

    override fun getCount(): Int {
        return cardList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var view = LayoutInflater.from(context).inflate(R.layout.adapter_page_details,container,false)
        val model = cardList[position]
        val text: TextView =view.findViewById<TextView>(R.id.page_LBL_text)
        text.text =model
        container.addView(view,position)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}