package be.ehb.visit_app.Fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.findFragment
import androidx.lifecycle.lifecycleScope
import be.ehb.visit_app.Models.ApiCall
import be.ehb.visit_app.Models.City
import be.ehb.visit_app.Models.Monument
import be.ehb.visit_app.Models.MonumentItem
import be.ehb.visit_app.R
import be.ehb.visit_app.ViewModels.MainViewModel
import com.android.volley.RequestQueue
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.google.maps.android.clustering.ClusterManager

class MapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback {

    lateinit var mGoogleMap:GoogleMap
    lateinit var mMapView: MapView
    var cityInit: City? = null
    private val requestTag = "MapRequest"
    private var queue: RequestQueue? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainModel: MainViewModel by activityViewModels()

        queue = mainModel.getQueue(activity)



        mMapView = view.findViewById<MapView>(R.id.mapView)
        var mapSearchView = view.findViewById<SearchView>(R.id.mapSearchView)

        //Map initialization inspired by Victor Wooding tutorial (https://www.youtube.com/watch?v=0dToEEuPL9Y)
        if(mMapView != null){
            mMapView.onCreate(null)
            mMapView.onResume()
            mMapView.getMapAsync(this)
        }

        //monumentsInit = model.monuments
        if (!mainModel.isMonumentInitialized()){
            lifecycleScope.launch() {
                withContext(Dispatchers.IO){
                    mainModel.city = ApiCall.getCity(mainModel.defaultCityName, requestTag, queue)
                    mainModel.monuments = ApiCall.getMonuments(mainModel.city, requestTag, queue)
                }
                withContext(Dispatchers.Main){
                    updateItem(mainModel.monuments, LatLng(mainModel.city.lat, mainModel.city.lon))
                }
            }
        }else{
            updateItem(mainModel.monuments, LatLng(mainModel.city.lat, mainModel.city.lon))
        }


        mapSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && !query.isNullOrBlank()) {
                    searchAndUpdateMonuments(mainModel, query)
                }
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }
        })
    }

    fun searchAndUpdateMonuments(mainModel:MainViewModel, cityName:String){
        lifecycleScope.launch{
            //get city info
            withContext(Dispatchers.IO){
                mainModel.city = ApiCall.getCity(cityName, requestTag, queue)
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
                        updateItem(mainModel.monuments, LatLng(mainModel.city.lat, mainModel.city.lon))
                    }else{
                        updateItem(emptyList<Monument>(), LatLng(0.0, 0.0))
                    }
                }
            }
        }
    }

    fun updateItem(monuments:List<Monument>, coordinate:LatLng){
        if(monuments.isNotEmpty()){
            mMapView.getMapAsync{
                val clustermanager = ClusterManager<Monument>(activity, mGoogleMap)

                try{
                    mGoogleMap.setOnCameraIdleListener(clustermanager)
                    clustermanager.addItems(monuments)
                    clustermanager.cluster()
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 13F))
                }catch (e: Exception){
                    Log.e("Error", e.toString())
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {

        mGoogleMap = googleMap

        val sydney = LatLng(-34.0, 151.0)
        val bruxelles = LatLng(50.84676530052056, 4.3625619640118325)
        mGoogleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bruxelles, 13F))
    }

}