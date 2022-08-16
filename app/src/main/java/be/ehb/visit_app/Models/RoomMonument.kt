package be.ehb.visit_app.Models

import android.graphics.Bitmap
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Favorite_Monument_Table")
data class RoomMonument (
    @PrimaryKey
    var apiId:String = "",
    var name:String = "",


    var rate:String = "",
    var kinds:String = "",

    @Ignore
    var picture: Bitmap = Bitmap.createBitmap(0,0, Bitmap.Config.ARGB_8888),

    var city:String= "",
    var road:String?= "",
    var house_number:String= "",
    var country:String= "",
    var source:String= "",
    var longitude:Double = 0.0,
    var latitude:Double = 0.0,
    var description:String?= "",

    ){
}