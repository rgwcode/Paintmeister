package lu.weidig.paintmeister.data

import android.content.Context
import android.content.res.AssetManager
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lu.weidig.paintmeister.data.dao.FullDepthManufacturerDao
import lu.weidig.paintmeister.data.dao.ManufacturerDao
import lu.weidig.paintmeister.data.dao.PaintDao
import lu.weidig.paintmeister.data.dao.PaintLineDao
import lu.weidig.paintmeister.data.entity.Manufacturer
import lu.weidig.paintmeister.data.entity.Paint
import lu.weidig.paintmeister.data.entity.PaintLine
import org.json.JSONObject
import java.nio.charset.Charset

@Database(
    entities = [Paint::class, Manufacturer::class, PaintLine::class], version = 6
)
abstract class PaintmeisterRoomDatabase : RoomDatabase() {
    abstract fun paintDao(): PaintDao
    abstract fun paintLineDao(): PaintLineDao
    abstract fun manufacturerDao(): ManufacturerDao
    abstract fun fullDepthManufacturerDao(): FullDepthManufacturerDao

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
                ).build()
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

        fun populateDatabase(database: PaintmeisterRoomDatabase) {
            if (database.manufacturerDao().getAny() == null) {
                val manufacturerDao = database.manufacturerDao()
                val paintDao = database.paintDao()
                val paintLineDao = database.paintLineDao()
                val manufacturerFiles = assetManager.list("manufacturers")

                if (manufacturerFiles != null) {
                    for (manufacturerFile in manufacturerFiles) {
                        val jsonStream = assetManager.open("manufacturers/$manufacturerFile")
                        val jsonSize = jsonStream.available()
                        val jsonBuffer = ByteArray(jsonSize)

                        jsonStream.read(jsonBuffer)
                        jsonStream.close()
                        val jsonString = String(jsonBuffer, Charset.forName("UTF-8"))
                        val manufacturerJsonObject = JSONObject(jsonString)

                        // Manufacturers
                        val manufacturer = Manufacturer(
                            name = manufacturerJsonObject["name"].toString(),
                            website = manufacturerJsonObject["website"].toString()
                        )
                        val manufacturerId = manufacturerDao.insert(manufacturer)

                        // PaintLines
                        val paintLinesJsonArray = manufacturerJsonObject.getJSONArray("paintlines")
                        for (i in 0 until paintLinesJsonArray.length()) {
                            val paintLineJsonObject = paintLinesJsonArray.getJSONObject(i)
                            val paintLine = PaintLine(
                                name = paintLineJsonObject["name"].toString
                                    (), manufacturerId = manufacturerId
                            )
                            val paintLineId = paintLineDao.insert(paintLine)

                            // Paints
                            val paintsJsonArray = paintLineJsonObject.getJSONArray("paints")
                            for (j in 0 until paintsJsonArray.length()) {
                                val paintObject = paintsJsonArray.getJSONObject(j)
                                val paint = Paint(
                                    name = paintObject["name"].toString(),
                                    color = paintObject["color"].toString(),
                                    paintLineId = paintLineId
                                )
                                if (paintObject.has("metallic")) {
                                    paint.metallic = true
                                }
                                paintDao.insert(paint)
                            }
                        }
                    }
                }
            }
        }
    }
}

