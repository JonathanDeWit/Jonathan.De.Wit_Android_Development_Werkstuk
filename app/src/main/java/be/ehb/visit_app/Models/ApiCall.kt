package be.ehb.visit_app.Models

import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine



class ApiCall {

    companion object{
        val apiKey = "5ae2e3f221c38a28845f05b67c36c020dab83b47cc2a45805f9e033e"

        suspend fun getCity(cityName:String, requestTag:String, queue: RequestQueue?)= suspendCoroutine<City>{ apiMonuments ->
            var getCityUrl = "https://api.opentripmap.com/0.1/en/places/geoname?apikey=${apiKey}&format=json&name=${cityName}"

            val gson = Gson()
            //Create Get Request for the city Information
            //API request inspired by Volley documentation (https://google.github.io/volley/requestqueue.html)
            val stringCityRequest = StringRequest(
                Request.Method.GET, getCityUrl, { response ->
                    //Transform Json string to City Kotlin object
                    //Save city object in main view model
                    //Conversion inspired Coding in Flow tutorial (https://www.youtube.com/watch?v=xbo1G02c2VM&t=247s)
                    val city = gson.fromJson(response.toString(), City::class.java)
                    apiMonuments.resume(city)
                },Response.ErrorListener { error -> Log.e("API_Request_City", error.toString())}
            )
            stringCityRequest.tag = requestTag
            queue?.add(stringCityRequest)
        }

        suspend fun getMonuments(city:City, requestTag:String, queue: RequestQueue?)= suspendCoroutine<List<Monument>>{ apiMonuments ->
            val getMonumentsUrl = "https://api.opentripmap.com/0.1/en/places/radius?apikey=${apiKey}" +
                    "&radius=5000&rate=2&format=json&lon=${city.lon}&lat=${city.lat}"

            val gson = Gson()
            //Create Get Request for getting the list of monuments of the city
            //API request inspired by Volley documentation (https://google.github.io/volley/requestqueue.html)
            val stringCityRequest = StringRequest(
                Request.Method.GET, getMonumentsUrl, { response ->
                    //Transform Json string to Monument Kotlin object
                    //Conversion inspired Coding in Flow tutorial (https://www.youtube.com/watch?v=xbo1G02c2VM&t=247s)
                    val monuments = gson.fromJson(response.toString(), Array<Monument>::class.java).toList()
                    apiMonuments.resume(monuments)
                },Response.ErrorListener { error ->{
                        Log.e("API_Request_Monument", error.toString())
                        apiMonuments.resume(emptyList<Monument>())
                    }
                }
            )
            stringCityRequest.tag = requestTag
            queue?.add(stringCityRequest)
        }

        suspend fun getMonument(monumentApiId:String, requestTag:String, queue: RequestQueue?) = suspendCoroutine<MonumentDetail>{ apiMonument ->

            var getDetailMonumentUrl = "https://api.opentripmap.com/0.1/en/places/xid/${monumentApiId}?apikey=${apiKey}"

            val gson = Gson()
            //Create Get Request for the monument detail
            //API request inspired by Volley documentation (https://google.github.io/volley/requestqueue.html)
            val stringCityRequest = StringRequest(
                Request.Method.GET, getDetailMonumentUrl, { response ->
                    //Transform Json string to DetailMonument Kotlin object
                    //Save DetailMonument object in main view model
                    //Conversion inspired Coding in Flow tutorial (https://www.youtube.com/watch?v=xbo1G02c2VM&t=247s)
                    var monumentObject = gson.fromJson(response.toString(), MonumentDetail::class.java)
                    apiMonument.resume(monumentObject)
                }, Response.ErrorListener { error ->
                    //Log Error
                    Log.e("API_Request_City", error.toString())
                }
            )
            stringCityRequest.tag = requestTag
            queue?.add(stringCityRequest)
        }

        suspend fun getMonumentImage(monumentImageURL:String, requestTag:String, queue: RequestQueue?) = suspendCoroutine<Bitmap>{ apiImage ->
            //Create Get Request for the monument detail
            //API request inspired by heyletscode tutorial (https://www.youtube.com/watch?v=fASThCXLrCc)
            val stringCityRequest = ImageRequest(monumentImageURL, { response ->
                apiImage.resume(response)
            }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, { error ->
                //Log Error
                Log.e("API_Request_City", error.toString())
            }
            )
            stringCityRequest.tag = requestTag
            queue?.add(stringCityRequest)
        }
    }
}