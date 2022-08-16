package be.ehb.visit_app.room

import android.content.Context
import androidx.room.*
import be.ehb.visit_app.Models.FavoriteMonument
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(FavoriteMonument::class), version = 1, exportSchema = false)
abstract class VisitRoomDatabase : RoomDatabase() {

    abstract fun MonumentDao(): FavoriteMonumentDao

    companion object {
        @Volatile
        private var INSTANCE: VisitRoomDatabase? = null

        fun getDatabase(
            context: Context
        ): VisitRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VisitRoomDatabase::class.java,
                    "visit_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }


    }
}