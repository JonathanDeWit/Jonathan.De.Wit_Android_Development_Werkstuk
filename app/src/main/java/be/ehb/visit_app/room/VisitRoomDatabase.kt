package be.ehb.visit_app.room

import android.content.Context
import androidx.room.*
import be.ehb.visit_app.Models.MonumentDetail
import be.ehb.visit_app.Models.RoomMonument
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(RoomMonument::class), version = 1, exportSchema = false)
abstract class VisitRoomDatabase : RoomDatabase() {

    abstract fun MonumentDao(): MonumentDetailDao

    companion object {
        @Volatile
        private var INSTANCE: VisitRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): VisitRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VisitRoomDatabase::class.java,
                    "word_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }


    }
}