package lu.weidig.paintmeister.data

import android.content.Context
import android.content.res.AssetManager
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.beust.klaxon.Klaxon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lu.weidig.paintmeister.data.dao.ManufacturerDao
import lu.weidig.paintmeister.data.dao.PaintDao
import lu.weidig.paintmeister.data.dao.PaintLineDao
import lu.weidig.paintmeister.data.entity.Manufacturer
import lu.weidig.paintmeister.data.entity.Paint
import lu.weidig.paintmeister.data.entity.PaintLine

@Database(entities = [Paint::class, Manufacturer::class, PaintLine::class], version = 2)
abstract class PaintmeisterRoomDatabase : RoomDatabase() {
    abstract fun paintDao(): PaintDao
    abstract fun manufacturerDao(): ManufacturerDao
    abstract fun paintLineDao(): PaintLineDao

    companion object {
        @Volatile
        private var INSTANCE: PaintmeisterRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): PaintmeisterRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PaintmeisterRoomDatabase::class.java,
                    "Paintmeister_database"
                ).fallbackToDestructiveMigration().addCallback(
                    PaintmeisterDatabaseCallback
                        (scope, context.assets)
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class PaintmeisterDatabaseCallback(
        private val scope: CoroutineScope, private val
        assetManager: AssetManager
    ) :
        RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database)
                }
            }
        }

        suspend fun populateDatabase(database: PaintmeisterRoomDatabase) {
            if (database.manufacturerDao().getAny() == null) {
                val manufacturerDao = database.manufacturerDao()
                val paintDao = database.paintDao()
                val paintLineDao = database.paintLineDao()

                suspend fun insertAllWithManufacturer(
                    withManufacturer: Manufacturer,
                    paintsToInsert: List<Paint>?
                ) {
                    if (paintsToInsert != null) {
                        val insertId = manufacturerDao.insert(withManufacturer)
                        for (paint in paintsToInsert) {
                            paint.manufacturerId = insertId
                        }
                        paintDao.insert(paintsToInsert)
                    }
                }

                // Reaper
                var paints = Klaxon().parseArray<Paint>(assetManager.open("Reaper.json"))
                insertAllWithManufacturer(
                    Manufacturer(
                        name = "Reaper",
                        website = "https://www.reapermini.com/paints"
                    ), paints
                )

                // Citadel
                paints = Klaxon().parseArray<Paint>(assetManager.open("Citadel.json"))
                insertAllWithManufacturer(
                    Manufacturer(
                        name = "Citadel", website =
                        "https://www.games-workshop.com/en-US/Painting-Modelling"
                    ), paints
                )
            }
        }
    }
}

