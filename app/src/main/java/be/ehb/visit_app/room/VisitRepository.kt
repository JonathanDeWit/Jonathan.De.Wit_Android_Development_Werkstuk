package be.ehb.visit_app.room

import androidx.annotation.WorkerThread
import be.ehb.visit_app.Models.MonumentDetail
import be.ehb.visit_app.Models.RoomMonument
import kotlinx.coroutines.flow.Flow

class VisitRepository(private val monumentDao: MonumentDetailDao) {
    val allMonument: Flow<List<RoomMonument>> = monumentDao.getAllMonument()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(monument: RoomMonument){
        //monumentDao.insertMonument(monument)
    }
}