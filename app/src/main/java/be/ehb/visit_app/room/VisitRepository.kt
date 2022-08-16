package be.ehb.visit_app.room

import androidx.annotation.WorkerThread
import be.ehb.visit_app.Models.FavoriteMonument

class VisitRepository(private val monumentDao: FavoriteMonumentDao) {
    val allMonument: List<FavoriteMonument> = monumentDao.getAllMonument()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(monument: FavoriteMonument){
        monumentDao.insertMonument(monument)
    }
}