package be.ehb.visit_app.Activitys

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import be.ehb.visit_app.Models.City
import be.ehb.visit_app.Models.Monument
import be.ehb.visit_app.Models.MonumentDetail
import be.ehb.visit_app.R
import be.ehb.visit_app.ViewModels.MainViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MonumentActivity : AppCompatActivity() {

    val apiKey = "5ae2e3f221c38a28845f05b67c36c020dab83b47cc2a45805f9e033e"
    private val requestTag = "DiscoverRequest"
    private lateinit var queue: RequestQueue

    private lateinit var mNameTextView:TextView
    private lateinit var mCountryTextView:TextView
    private lateinit var mCityTextView:TextView
    private lateinit var mStreetTextView:TextView
    private lateinit var mDescriptionTextView:TextView
    private lateinit var mImageView: ImageView

    lateinit var monument:MonumentDetail


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monument)

        mNameTextView = findViewById<TextView>(R.id.mNameTextView)
        mCountryTextView = findViewById<TextView>(R.id.mCountryTextView)
        mCityTextView = findViewById<TextView>(R.id.mCityTextView)
        mStreetTextView = findViewById<TextView>(R.id.mStreetTextView)
        mDescriptionTextView = findViewById<TextView>(R.id.mDescriptionTextView)
        mImageView = findViewById<ImageView>(R.id.mImageView)


        val extras = intent.extras
        if (extras!= null) {
            var monumentApiId = extras.getString(MainActivity.EXTRA_MONUMENT_API_ID)

            if (monumentApiId != null) {
                queue = Volley.newRequestQueue(this)
                lifecycle.coroutineScope.launch() {
                    withContext(Dispatchers.IO){
                        monument = getMonument(monumentApiId)
                        monument.picture = getMonumentImage()
                    }
                    withContext(Dispatchers.Main){
                        updateTextView()
                        mImageView.setImageBitmap(monument.picture)
                    }
                }


            }
        }

    }

    fun updateTextView(){
        if(monument != null){
            mNameTextView.text = monument.name
            mCountryTextView.text = getString(R.string.country).plus(" ${monument.address.country}")
            mCityTextView.text = getString(R.string.city).plus(" ${monument.address.city}")
            if (monument.address != null){
                mStreetTextView.text = getString(R.string.street)
                    .plus(" ${monument.address.road ?: getString(R.string.no_street)}")
            }
            if (monument.WikiInfo!= null){
                mDescriptionTextView.text = monument.WikiInfo.text ?: getString(R.string.no_description)
            }
        }
    }

    suspend fun getMonument(monumentApiId:String) = suspendCoroutine<MonumentDetail>{ apiMonument ->

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
            queue.add(stringCityRequest)


    }

    suspend fun getMonumentImage() = suspendCoroutine<Bitmap>{ apiImage ->
        var getDetailMonumentUrl = monument.preview.source

        val gson = Gson()
        //Create Get Request for the monument detail
        //API request inspired by heyletscode tutorial (https://www.youtube.com/watch?v=fASThCXLrCc)
        val stringCityRequest = ImageRequest(getDetailMonumentUrl, { response ->
            monument.picture = response
            apiImage.resume(response)
            }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, { error ->
                //Log Error
                Log.e("API_Request_City", error.toString())
            }
        )
        stringCityRequest.tag = requestTag
        queue.add(stringCityRequest)
    }

    override fun onDestroy() {
        super.onDestroy()
        queue.cancelAll(requestTag)
    }
}
