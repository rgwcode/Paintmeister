package lu.weidig.paintmeister.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import lu.weidig.paintmeister.data.PaintmeisterRepository
import lu.weidig.paintmeister.data.pojo.FullDepthManufacturer

class PaintListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PaintmeisterRepository =
        PaintmeisterRepository(application, viewModelScope)
//    val paints: LiveData<List<Paint>>

    val fullDepthManufacturers: LiveData<List<FullDepthManufacturer>>

    init {
//        paints = repository.paints
        fullDepthManufacturers = repository.fullDepthManufacturers
    }

//    fun insert(paint: Paint) = viewModelScope.launch(Dispatchers.IO) {
//        repository.insert(paint)
//    }
}