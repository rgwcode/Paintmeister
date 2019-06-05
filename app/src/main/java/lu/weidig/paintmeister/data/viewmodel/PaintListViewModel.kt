package lu.weidig.paintmeister.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import lu.weidig.paintmeister.data.PaintmeisterRepository
import lu.weidig.paintmeister.data.entity.Paint

class PaintListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PaintmeisterRepository =
        PaintmeisterRepository(application, viewModelScope)
    val paints: LiveData<List<Paint>>


    init {
        paints = repository.paints
    }

//    fun insert(paint: Paint) = viewModelScope.launch(Dispatchers.IO) {
//        repository.insert(paint)
//    }
}