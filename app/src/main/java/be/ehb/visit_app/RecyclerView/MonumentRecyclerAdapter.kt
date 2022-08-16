package be.ehb.visit_app.RecyclerView

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.ehb.visit_app.Activitys.MainActivity
import be.ehb.visit_app.Models.Monument
import be.ehb.visit_app.Activitys.MonumentActivity
import be.ehb.visit_app.Models.FavoriteMonument
import be.ehb.visit_app.R
import be.ehb.visit_app.room.FavoriteMonumentDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MonumentRecyclerAdapter (var monuments: List<Monument>, var favoriteMonumentDAO:FavoriteMonumentDao, var favoritMonuments:List<FavoriteMonument>): RecyclerView.Adapter<MonumentRecyclerAdapter.MonumentViewHolder>(){
    inner class MonumentViewHolder(monumentItemView: View): RecyclerView.ViewHolder(monumentItemView){
        var nameTextView:TextView
        var kindtextView:TextView
        var moreInfoButton:Button
        var favoriteButton:Button


        init {
            nameTextView = monumentItemView.findViewById(R.id.nameTextView)
            kindtextView = monumentItemView.findViewById(R.id.kindsTextView)
            moreInfoButton = monumentItemView.findViewById(R.id.moreInfoButton)
            favoriteButton = monumentItemView.findViewById(R.id.favoriteButton)

        }
    }

    var context: Context? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonumentViewHolder {
        val monumentItemView = LayoutInflater.from(parent.context).inflate(R.layout.monument_list_item, parent, false)
        return MonumentViewHolder(monumentItemView)
    }

    override fun onBindViewHolder(holder: MonumentViewHolder, position: Int) {
        val monument = monuments.get(position)
        holder.nameTextView.text = monument.name
        holder.kindtextView.text = holder.kindtextView.text.toString().plus(": ${monument.kinds}")
        holder.moreInfoButton.setOnClickListener {
            val intent = Intent(context, MonumentActivity::class.java)
            intent.putExtra(MainActivity.EXTRA_MONUMENT_API_ID, monument.apiId)
            context?.startActivity(intent)
        }

        holder.favoriteButton.setOnClickListener {
            if (!favoritMonuments.any { m -> m.apiId.equals(monument.apiId)}){
                runBlocking {
                    val list = mutableListOf<FavoriteMonument>(monument.toFavoriteMonument())
                    list+=favoritMonuments
                    favoritMonuments = list
                    withContext(Dispatchers.IO){
                        holder.favoriteButton.background.setTint(Color.RED)
                        favoriteMonumentDAO.insertMonument(monument.toFavoriteMonument())
                    }
                }
            }else{
                runBlocking {
                    withContext(Dispatchers.IO){
                        favoriteMonumentDAO.deleteMonument(monument.toFavoriteMonument())
                        holder.favoriteButton.background.setTint(Color.GRAY)
                    }
                }
            }

        }

    }

    override fun getItemCount(): Int {
        return monuments.size
    }
}