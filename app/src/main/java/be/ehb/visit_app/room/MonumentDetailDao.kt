package be.ehb.visit_app.room

import androidx.room.*
import be.ehb.visit_app.Models.MonumentDetail
import be.ehb.visit_app.Models.RoomMonument
import kotlinx.coroutines.flow.Flow

@Dao
interface MonumentDetailDao {

    @Query("SELECT * FROM Favorite_Monument_Table")
    fun getAllMonument(): Flow<List<RoomMonument>>


}