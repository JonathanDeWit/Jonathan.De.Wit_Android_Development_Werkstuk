package be.ehb.visit_app.Fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import be.ehb.visit_app.Models.FavoriteMonument
import be.ehb.visit_app.Models.Monument
import be.ehb.visit_app.R
import be.ehb.visit_app.RecyclerView.MonumentRecyclerAdapter
import be.ehb.visit_app.ViewModels.MainViewModel
import be.ehb.visit_app.room.FavoriteMonumentDao
import be.ehb.visit_app.room.VisitRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.log


class FavoritFragment : Fragment(R.layout.fragment_favorit) {


    private lateinit var adapter: MonumentRecyclerAdapter
    var favoritMonuments = emptyList<FavoriteMonument>()
    lateinit var favoriteMonumentDAO:FavoriteMonumentDao


    override fun onAttach(context: Context) {
        super.onAttach(context)
        var db = VisitRoomDatabase.getDatabase(context)
        favoriteMonumentDAO = db.MonumentDao()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainModel: MainViewModel by activityViewModels()

        var monumentRecyclerView = view.findViewById<RecyclerView>(R.id.favoriteMonumentRecyclerView)
        monumentRecyclerView.layoutManager = LinearLayoutManager(activity)


        lifecycleScope.launch{
            withContext(Dispatchers.IO){
                favoritMonuments = favoriteMonumentDAO.getAllMonument()
            }
            withContext(Dispatchers.Main){
                updateRecyclerAdapter(monumentRecyclerView)
            }
        }

//        var favoritMonumentDao = db?.favoriteMonumentDao()

//        if (favoritMonumentDao != null) {
//            favoritMonuments = favoritMonumentDao.getAllMonument()
//
//            updateRecyclerAdapter()
//        }

    }

    fun updateRecyclerAdapter(monumentRecyclerView: RecyclerView){
        var monuments = ArrayList<Monument>()
        for (monument in favoritMonuments){
            monuments.add(monument.toMonument())
        }

        adapter = MonumentRecyclerAdapter(monuments.toList(), favoriteMonumentDAO, favoritMonuments)
        monumentRecyclerView.adapter = adapter
    }
}