package be.ehb.visit_app.Fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.ehb.visit_app.Models.City
import be.ehb.visit_app.Models.Monument
import be.ehb.visit_app.R
import be.ehb.visit_app.RecyclerView.MonumentRecyclerAdapter
import be.ehb.visit_app.ViewModels.MainViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson

class DiscoverFragment: Fragment(R.layout.fragment_discover) {


    private lateinit var adapter: MonumentRecyclerAdapter
    private val requestTag = "DiscoverRequest"

    private var queue: RequestQueue? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainModel:MainViewModel by activityViewModels()
        queue = mainModel.getQueue(activity)

        val cityTextView = view.findViewById<TextView>(R.id.cityTextView)
        var monumentRecyclerView = view.findViewById<RecyclerView>(R.id.monumentRecyclerView)
        monumentRecyclerView.layoutManager = LinearLayoutManager(activity)
        var citySearchView = view.findViewById<SearchView>(R.id.citySearchView)

        //Default search
        var cityName = "Brussel"
        getMonuments(mainModel, cityName, cityTextView, monumentRecyclerView)


        citySearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            @Override
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && !query.isNullOrBlank()) {
                    getMonuments(mainModel, query, cityTextView, monumentRecyclerView)
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }
        })

    }


    fun getMonuments(mainModel:MainViewModel, cityName:String, cityTextView: TextView, monumentRecyclerView:RecyclerView){
        var getCityUrl = "https://api.opentripmap.com/0.1/en/places/geoname?apikey=${mainModel.ApiKey}&format=json&name=${cityName}"

        val gson = Gson()
        //Create Get Request for the city Information
        //API request inspired by Volley documentation (https://google.github.io/volley/requestqueue.html)
        val stringCityRequest = StringRequest(
            Request.Method.GET, getCityUrl, { response ->
                //Transform Json string to City Kotlin object
                //Save city object in main view model
                //Conversion inspired Coding in Flow tutorial (https://www.youtube.com/watch?v=xbo1G02c2VM&t=247s)
                mainModel.city = gson.fromJson(response.toString(), City::class.java)
                cityTextView.text = getString(R.string.result).plus(" ${mainModel.city.name}")

                //Create Get Request for getting the list of monuments of the city
                //API request inspired by Volley documentation (https://google.github.io/volley/requestqueue.html)
                var getMonumentsUrl = "https://api.opentripmap.com/0.1/en/places/radius?apikey=${mainModel.ApiKey}" +
                        "&radius=1000&limit=20&rate=2&format=json&lon=${mainModel.city.lon}&lat=${mainModel.city.lat}"
                val stringCityRequest = StringRequest(
                    Request.Method.GET, getMonumentsUrl, { response ->
                        //Transform Json string to Monument Kotlin object
                        //Conversion inspired Coding in Flow tutorial (https://www.youtube.com/watch?v=xbo1G02c2VM&t=247s)
                        mainModel.Monuments = gson.fromJson(response.toString(), Array<Monument>::class.java).toList()

                        if (mainModel.Monuments.isNotEmpty()){
                            updateRecyclerAdapter(monumentRecyclerView, mainModel.Monuments)
                        }else{
                            cityTextView.text = getString(R.string.no_result).plus(" ${cityName}")
                            updateRecyclerAdapter(monumentRecyclerView, emptyList<Monument>())
                        }


                    },Response.ErrorListener { error ->
                        //Log Error
                        Log.e("API_Request_Monument", error.toString())
                    }
                )
                stringCityRequest.tag = requestTag
                queue?.add(stringCityRequest)
            },Response.ErrorListener { error ->
                //Log Error
                Log.e("API_Request_City", error.toString())
            }
        )
        stringCityRequest.tag = requestTag
        queue?.add(stringCityRequest)
    }

    fun updateRecyclerAdapter(monumentRecyclerView:RecyclerView, monuments:List<Monument>){
        adapter = MonumentRecyclerAdapter(monuments)
        monumentRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        queue?.cancelAll(requestTag)

    }
}