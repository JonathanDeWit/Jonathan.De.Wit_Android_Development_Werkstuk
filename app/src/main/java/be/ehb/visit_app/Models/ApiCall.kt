package be.ehb.visit_app.Models

import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson

class ApiCall {
    companion object{
        fun getCity(cityName:String, test: Response.Listener<String>){

//            var ApiKey = "5ae2e3f221c38a28845f05b67c36c020dab83b47cc2a45805f9e033e"
//            var GetCityUrl = "https://api.opentripmap.com/0.1/en/places/geoname?apikey=${ApiKey}&format=json&name=${cityName}"
//
//            //Create Get Request for the city Information
//
//            val stringCityRequest = StringRequest(
//                Request.Method.GET, GetCityUrl, { response ->
//                    //Transform Json string to City Kotlin object
//                    val gson = Gson()
//                    model.city = gson.fromJson(response.toString(), City::class.java)
//                    textLabel.text = model.city.name
//
//                    //Create Get Request for getting the list of monuments of the city
//                    var GetMonumentsUrl = "https://api.opentripmap.com/0.1/en/places/radius?apikey=${ApiKey}" +
//                            "&radius=1000&limit=10&rate=2&format=json&lon=${model.city.lon}&lat=${model.city.lat}"
//                    val stringCityRequest = StringRequest(
//                        Request.Method.GET, GetMonumentsUrl, { response ->
//                            //Transform Json string to City Kotlin object
//                            val gson = Gson()
//                            model.Monuments = gson.fromJson(response.toString(), Array<Monument>::class.java).toList()
//                            textLabel.text = model.Monuments[0].name
//                        },
//                        Response.ErrorListener { error ->
//                            //Log Error
//                            Log.e("API_Request_Error", error.toString())
//                        }
//                    )
//                    model.getQueue(activity)?.add(stringCityRequest)
//                },
//                Response.ErrorListener { error ->
//                    //Log Error
//                    Log.e("API_Request_Error_City", error.toString())
//                }
        }
    }
}