package be.ehb.visit_app.Models

import android.graphics.Bitmap
import androidx.room.*
import com.google.gson.annotations.SerializedName

data class MonumentDetail(
    @SerializedName("xid")
    var apiId:String ,

    var name:String,

    var address:MonumentAddress = MonumentAddress(),

    var rate:String,
    var kinds:String,
    var picture: Bitmap,

    var preview:Preview,
    @SerializedName("wikipedia_extracts")
    var WikiInfo:WikipediaInfo,


    @SerializedName("point")
    var coordinate:MonumentCoordinate = MonumentCoordinate()){



}


data class Preview (
    var source:String,
    var height:String,
    var width:String,
){
    constructor():this("", "", ""){

    }
}

data class WikipediaInfo(
    var title:String,
    var text:String?,
){
    constructor():this("", ""){

    }
}