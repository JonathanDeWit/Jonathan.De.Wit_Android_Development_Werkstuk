package be.ehb.visit_app.ViewModels


import android.app.Activity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import be.ehb.visit_app.Models.City
import be.ehb.visit_app.Models.Monument
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley


class MainViewModel(): ViewModel() {
    private var queue: RequestQueue? = null
    lateinit var city: City
    lateinit var monuments: List<Monument>
    var defaultCityName = "Brussel"

    private fun initQueue(context: FragmentActivity){
        queue = Volley.newRequestQueue(context)
    }

    fun getQueue(context: FragmentActivity?): RequestQueue {

        if (queue == null && context != null){
            initQueue(context)
        }
        return queue!!
    }

    lateinit var getall:String

    fun isCityInitialized():Boolean{
        return this::city.isInitialized
    }
    fun isMonumentInitialized():Boolean{
        return this::monuments.isInitialized
    }

    override fun onCleared() {
        super.onCleared()
        queue?.stop()
    }
}
