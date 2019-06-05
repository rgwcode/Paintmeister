package lu.weidig.paintmeister.data

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import lu.weidig.paintmeister.data.entity.Paint

class PaintmeisterRepository(
    context: Context, scope: CoroutineScope
) {
    private val database = PaintmeisterRoomDatabase.getDatabase(context, scope)
    private val paintDao = database.paintDao()

    val paints: LiveData<List<Paint>> = paintDao.getAllSortedByName()
}