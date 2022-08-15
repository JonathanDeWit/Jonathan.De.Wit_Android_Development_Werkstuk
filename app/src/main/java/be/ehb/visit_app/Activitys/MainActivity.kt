package be.ehb.visit_app.Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import be.ehb.visit_app.Models.ApiCall
import be.ehb.visit_app.R
import be.ehb.visit_app.ViewModels.MainViewModel
import com.android.volley.RequestQueue
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private val requestTag = "MainRequest"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val model: MainViewModel by viewModels()

        //Init navigation host fragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        //Init bottom navigation
        val bottom_nav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottom_nav.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onStop() {
        super.onStop()

    }

    companion object{
        const val EXTRA_MONUMENT_API_ID = "be.ehb.visit_app.EXTRA_MONUMENT_API_ID"
    }
}