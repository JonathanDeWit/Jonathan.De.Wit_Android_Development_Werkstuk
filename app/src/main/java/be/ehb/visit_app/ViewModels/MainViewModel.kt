package be.ehb.visit_app.ViewModels


import android.app.Activity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import be.ehb.visit_app.Models.City
import be.ehb.visit_app.Models.Monument
import be.ehb.visit_app.Models.MonumentDetail
import be.ehb.visit_app.room.VisitRepository
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley


class MainViewModel(): ViewModel() {
    private var queue: RequestQueue? = null
    lateinit var repository: VisitRepository
    lateinit var city: City
    lateinit var monuments: List<Monument>
    var defaultCityName = "Brussel"

    fun getQueue(context: FragmentActivity?): RequestQueue {

        if (queue == null && context != null){
            queue = Volley.newRequestQueue(context)
        }
        return queue!!
    }

    fun isRepositoryInitialized(): Boolean {
        return this::repository.isInitialized
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
