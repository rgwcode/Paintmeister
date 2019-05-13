package lu.weidig.paintmeister.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lu.weidig.paintmeister.data.PaintmeisterRepository
import lu.weidig.paintmeister.data.PaintmeisterRoomDatabase
import lu.weidig.paintmeister.data.dao.ManufacturerDao
import lu.weidig.paintmeister.data.dao.PaintDao
import lu.weidig.paintmeister.data.entity.Manufacturer
import lu.weidig.paintmeister.data.entity.Paint

class PaintListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PaintmeisterRepository
    val allPaints: LiveData<List<Paint>>
    val allManufacturers: LiveData<List<Manufacturer>>

    init {
        val paintDao: PaintDao =
            PaintmeisterRoomDatabase.getDatabase(application, viewModelScope).paintDao()
        val manufacturerDao: ManufacturerDao =
            PaintmeisterRoomDatabase.getDatabase(application, viewModelScope).manufacturerDao()
        repository = PaintmeisterRepository(paintDao, manufacturerDao)
        allPaints = repository.allPaints
        allManufacturers = repository.allManufacturers
    }

    fun insert(paint: Paint) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(paint)
    }
}