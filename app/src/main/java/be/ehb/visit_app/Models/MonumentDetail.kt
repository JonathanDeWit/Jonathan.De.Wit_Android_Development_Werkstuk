package be.ehb.visit_app.Models

import com.google.gson.annotations.SerializedName

class MonumentDetail (
    @SerializedName("xid")
    val apiId:String,
    var name:String,

    var address:MonumentAddress,

    var rate:String,
    var kinds:String,

    var image:String,

    var preview:Preview,
    @SerializedName("wikipedia_extracts")
    var WikiInfo:WikipediaInfo,

    @SerializedName("point")
    var coordinate:MonumentCoordinate

)

class Preview (
    var source:String,
    var height:String,
    var width:String,
)
class WikipediaInfo(
    var title:String,
    var text:String?,
)