package be.ehb.visit_app.Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import be.ehb.visit_app.R
import be.ehb.visit_app.ViewModels.MainViewModel
import be.ehb.visit_app.VisitApplication
import be.ehb.visit_app.room.MonumentModelFactory
import be.ehb.visit_app.room.RoomViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView



class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private val requestTag = "MainRequest"
    private val model: MainViewModel by viewModels()
//    private val roomViewModel: RoomViewModel by viewModels {
//        MonumentModelFactory((application as VisitApplication).repository)
//    }




    override fun onCreate(savedInstanceState: Bundle?) {
        //model.repository = (application as VisitApplication).repository

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        //var monuments = roomViewModel.allWords





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