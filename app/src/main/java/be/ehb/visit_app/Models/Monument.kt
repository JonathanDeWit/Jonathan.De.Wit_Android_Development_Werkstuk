package be.ehb.visit_app.Models

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
    var image:String,
    var address:MonumentAddress,
    var preview:MonumentPreview,
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
