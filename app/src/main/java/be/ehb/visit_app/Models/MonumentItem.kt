package be.ehb.visit_app.Models

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class MonumentItem(monument: Monument, snippet: String):ClusterItem {

    private val monument: Monument
    private val snippet: String
    private val position: LatLng

    fun getMonument(): LatLng {
        return LatLng(monument.coordinate.latitude, monument.coordinate.longitude)
    }

    init {
        this.snippet = snippet
        this.monument = monument
        this.position = LatLng(monument.coordinate.latitude, monument.coordinate.latitude)
    }
    override fun getPosition(): LatLng {
        return position
    }

    override fun getTitle(): String? {
        return monument.name
    }

    override fun getSnippet(): String? {
        return snippet
    }
}