package be.ehb.visit_app.RecyclerView

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.ehb.visit_app.Activitys.MainActivity
import be.ehb.visit_app.Models.Monument
import be.ehb.visit_app.Activitys.MonumentActivity
import be.ehb.visit_app.R

class MonumentRecyclerAdapter (var monuments: List<Monument>): RecyclerView.Adapter<MonumentRecyclerAdapter.MonumentViewHolder>(){
    inner class MonumentViewHolder(monumentItemView: View): RecyclerView.ViewHolder(monumentItemView){
        var nameTextView:TextView
        var kindtextView:TextView
        var moreInfoButton:Button


        init {
            nameTextView = monumentItemView.findViewById(R.id.nameTextView)
            kindtextView = monumentItemView.findViewById(R.id.kindsTextView)
            moreInfoButton = monumentItemView.findViewById(R.id.moreInfoButton)

        }
    }

    var context: Context? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonumentViewHolder {
        var monumentItemView = LayoutInflater.from(parent.context).inflate(R.layout.monument_list_item, parent, false)
        return MonumentViewHolder(monumentItemView)
    }

    override fun onBindViewHolder(holder: MonumentViewHolder, position: Int) {
        var monument = monuments.get(position)
        holder.nameTextView.text = monument.name
        holder.kindtextView.text = holder.kindtextView.text.toString().plus(": ${monument.kinds}")
        holder.moreInfoButton.setOnClickListener {
            val intent = Intent(context, MonumentActivity::class.java)
            intent.putExtra(MainActivity.EXTRA_MONUMENT_API_ID, monument.apiId)
            context?.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return monuments.size
    }
}