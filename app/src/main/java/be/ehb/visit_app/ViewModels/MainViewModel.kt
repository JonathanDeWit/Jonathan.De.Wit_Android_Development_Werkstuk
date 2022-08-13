package be.ehb.visit_app.ViewModels


import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import be.ehb.visit_app.Models.City
import be.ehb.visit_app.Models.Monument
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley


class MainViewModel(): ViewModel() {
    private var queue: RequestQueue? = null
    lateinit var city: City
    lateinit var Monuments: List<Monument>
    val ApiKey = "5ae2e3f221c38a28845f05b67c36c020dab83b47cc2a45805f9e033e"


    private fun initQueue(context: FragmentActivity){
        queue = Volley.newRequestQueue(context)
    }

    fun getQueue(context: FragmentActivity?): RequestQueue? {
        if (queue == null && context != null){
            initQueue(context)
        }
        return queue
    }
    lateinit var getall:String

    fun test(){
        if (this::getall.isInitialized){

        }
    }
}
