package be.ehb.visit_app

import android.app.Application
import be.ehb.visit_app.room.VisitRepository
import be.ehb.visit_app.room.VisitRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class VisitApplication: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { VisitRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { VisitRepository(database.MonumentDao()) }

}