package be.ehb.visit_app.Models

import android.graphics.Bitmap
import com.google.android.gms.maps.model.LatLng
import com.google.gson.annotations.SerializedName
import com.google.maps.android.clustering.ClusterItem


class Monument (
    //Unique Id in API
    @SerializedName("xid")
    val apiId:String,

    var name:String,
    var kinds:String,
    var rate:String,
    @SerializedName("point")
    var coordinate:MonumentCoordinate
): ClusterItem{

    override fun getPosition(): LatLng {
        return LatLng(coordinate.latitude, coordinate.longitude)
    }

    override fun getTitle(): String? {
        return name
    }

    override fun getSnippet(): String? {
        return ""
    }

    fun toFavoriteMonument(): FavoriteMonument {
        var favoriteMonument = FavoriteMonument(apiId, name, rate, kinds, Bitmap.createBitmap(1,1, Bitmap.Config.ARGB_8888)
        , "", "", "", "", "", coordinate.longitude, coordinate.latitude, "")

        return favoriteMonument
    }

}

data class MonumentAddress(
    var city:String,
    var road:String?,
    var country:String,
    var postcode:String,
    var house_number:String,
){
    constructor():this("", "", "", "", ""){

    }
}
data class MonumentPreview(var source:String){
    constructor():this(""){

    }
}
data class MonumentCoordinate (
    @SerializedName("lon")
    var longitude:Double,
    @SerializedName("lat")
    var latitude:Double){
    constructor():this(0.0, 0.0){

    }
}
