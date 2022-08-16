package be.ehb.visit_app.room

import androidx.room.*
import be.ehb.visit_app.Models.FavoriteMonument
@Dao
interface FavoriteMonumentDao {

    @Query("SELECT * FROM Favorite_Monument_Table")
    fun getAllMonument(): List<FavoriteMonument>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMonument(roomMonument: FavoriteMonument)

    @Delete
    suspend fun deleteMonument(roomMonument: FavoriteMonument)





}