package be.ehb.visit_app.Fragments

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.ehb.visit_app.Models.ApiCall
import be.ehb.visit_app.Models.Monument
import be.ehb.visit_app.R
import be.ehb.visit_app.RecyclerView.MonumentRecyclerAdapter
import be.ehb.visit_app.ViewModels.MainViewModel
import com.android.volley.RequestQueue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DiscoverFragment : Fragment(R.layout.fragment_discover){


    private lateinit var cityTextView:TextView
    private lateinit var monumentRecyclerView:RecyclerView
    private lateinit var citySearchView:SearchView
    private lateinit var adapter: MonumentRecyclerAdapter
    private val requestTag = "DiscoverRequest"

    private var queue: RequestQueue? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mainModel:MainViewModel by activityViewModels()
        super.onViewCreated(view, savedInstanceState)
        queue = mainModel.getQueue(activity)

        cityTextView = view.findViewById<TextView>(R.id.cityTextView)
        monumentRecyclerView = view.findViewById<RecyclerView>(R.id.monumentRecyclerView)
        monumentRecyclerView.layoutManager = LinearLayoutManager(activity)
        citySearchView = view.findViewById<SearchView>(R.id.citySearchView)

        if (!mainModel.isCityInitialized() && !mainModel.isMonumentInitialized()){
            updateMonuments(mainModel, mainModel.defaultCityName, mainModel.getQueue(activity))
        }
        else{
            cityTextView.text = getString(R.string.result).plus(": ${mainModel.city.name}")

            if (mainModel.monuments.isNotEmpty()){
                updateRecyclerAdapter(monumentRecyclerView, mainModel.monuments)
            }else{
                cityTextView.text = getString(R.string.no_result).plus(" ${mainModel.city.name}")
                updateRecyclerAdapter(monumentRecyclerView, emptyList<Monument>())
            }
        }



        citySearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && !query.isNullOrBlank()) {
                    updateMonuments(mainModel, query, mainModel.getQueue(activity))
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }
        })

    }




    fun updateMonuments(mainModel:MainViewModel, cityName:String, queue: RequestQueue?){
        lifecycleScope.launch {
            //get city info
            withContext(Dispatchers.IO){
                mainModel.city = ApiCall.getCity(cityName, requestTag, queue)
            }
            //update screen city
            withContext(Dispatchers.Main){
                cityTextView.text = getString(R.string.result).plus(": ${mainModel.city.name}")
            }


            //check if city was found
            if (mainModel.city.name != null)
            {
                //get monument list
                withContext(Dispatchers.IO){
                    mainModel.monuments = ApiCall.getMonuments(mainModel.city, requestTag, queue)
                }
                //update screen with monuments

                withContext(Dispatchers.Main){
                    if (mainModel.monuments.isNotEmpty()){
                        updateRecyclerAdapter(monumentRecyclerView, mainModel.monuments)
                    }else{
                        cityTextView.text = getString(R.string.no_result).plus(" ${cityName}")
                        updateRecyclerAdapter(monumentRecyclerView, emptyList<Monument>())
                    }
                }
            }
        }
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