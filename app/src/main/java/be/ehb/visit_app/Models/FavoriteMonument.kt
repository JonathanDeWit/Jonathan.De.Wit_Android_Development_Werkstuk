package be.ehb.visit_app.Models

import android.graphics.Bitmap
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Favorite_Monument_Table")
class FavoriteMonument (
    @PrimaryKey
    var apiId:String = "",
    var name:String = "",


    var rate:String = "",
    var kinds:String = "",

    @Ignore
    var picture: Bitmap = Bitmap.createBitmap(1,1, Bitmap.Config.ARGB_8888),

    var city:String= "",
    var road:String?= "",
    var house_number:String= "",
    var country:String= "",
    var source:String= "",
    var longitude:Double = 0.0,
    var latitude:Double = 0.0,
    var description:String?= "",

    ){

    fun toMonumentDetail(): MonumentDetail {
        var monumentDetail = MonumentDetail(
            apiId, name, MonumentAddress(city, road, country, "", house_number), rate,
            kinds, picture, Preview(source, "", "",), WikipediaInfo("", description),
            MonumentCoordinate(longitude, latitude))
        return monumentDetail
    }
    fun toMonument(): Monument {
        var monument = Monument(
            apiId, name, kinds, rate,
            MonumentCoordinate(longitude, latitude))
        return monument
    }
}
