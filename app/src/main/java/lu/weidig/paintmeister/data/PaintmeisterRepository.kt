package lu.weidig.paintmeister.data

import android.content.Context
import androidx.annotation.WorkerThread
import kotlinx.coroutines.CoroutineScope

class PaintmeisterRepository(
    context: Context, scope: CoroutineScope
) {
    private val database = PaintmeisterRoomDatabase.getDatabase(context, scope)
    private val paintDao = database.paintDao()
    private val fullDepthManufacturerDao = database.fullDepthManufacturerDao()

    val fullDepthManufacturers = fullDepthManufacturerDao.getAllSortedByName()

    @WorkerThread
    fun decreaseOwned(paintId: Long) {
        val paint = paintDao.getPaintById(paintId)
        if (paint.numOwned > 0) {
            paint.numOwned--
            paintDao.updatePaint(paint)
        }
    }

    @WorkerThread
    fun increaseOwned(paintId: Long) {
        val paint = paintDao.getPaintById(paintId)
        paint.numOwned++
        paintDao.updatePaint(paint)
    }

    fun isDataInitialized(): Boolean {
        return database.initialized
    }
}