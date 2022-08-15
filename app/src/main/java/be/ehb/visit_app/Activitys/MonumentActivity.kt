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
import be.ehb.visit_app.Models.ApiCall
import be.ehb.visit_app.Models.ApiCall.Companion.getMonumentImage
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


    private val requestTag = "DetailMonumentRequest"
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
                        monument = ApiCall.getMonument(monumentApiId, requestTag, queue)
                        monument.picture = getMonumentImage(monument.preview.source, requestTag, queue)
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

    override fun onDestroy() {
        super.onDestroy()
        queue.cancelAll(requestTag)
    }
}
