package lu.weidig.paintmeister.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import lu.weidig.paintmeister.data.dao.ManufacturerDao
import lu.weidig.paintmeister.data.dao.PaintDao
import lu.weidig.paintmeister.data.entity.Manufacturer
import lu.weidig.paintmeister.data.entity.Paint

class PaintmeisterRepository(
    private val paintDao: PaintDao,
    private val manufacturerDao: ManufacturerDao
) {
    val allPaints: LiveData<List<Paint>> = paintDao.getAllSortedByName()
    val allManufacturers: LiveData<List<Manufacturer>> = manufacturerDao.getAll()

    @WorkerThread
    suspend fun insert(paint: Paint) {
        paintDao.insert(paint)
    }

    @WorkerThread
    suspend fun insert(manufacturer: Manufacturer) {
        manufacturerDao.insert(manufacturer)
    }
}