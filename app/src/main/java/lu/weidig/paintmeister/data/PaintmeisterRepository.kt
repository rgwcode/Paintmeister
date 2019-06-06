package lu.weidig.paintmeister.data

import android.content.Context
import kotlinx.coroutines.CoroutineScope

class PaintmeisterRepository(
    context: Context, scope: CoroutineScope
) {
    private val database = PaintmeisterRoomDatabase.getDatabase(context, scope)
    //    private val paintDao = database.paintDao()
    private val fullDepthManufacturerDao = database.fullDepthManufacturerDao()

    //    val paints: LiveData<List<Paint>> = paintDao.getAllSortedByName()
    val fullDepthManufacturers = fullDepthManufacturerDao.getAllSortedByName()
}