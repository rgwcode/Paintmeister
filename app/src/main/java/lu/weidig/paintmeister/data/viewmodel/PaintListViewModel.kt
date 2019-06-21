package lu.weidig.paintmeister.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lu.weidig.paintmeister.data.PaintmeisterRepository
import lu.weidig.paintmeister.data.pojo.FullDepthManufacturer

class PaintListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PaintmeisterRepository =
        PaintmeisterRepository(application, viewModelScope)

    val fullDepthManufacturers: LiveData<List<FullDepthManufacturer>>

    init {
        fullDepthManufacturers = repository.fullDepthManufacturers
    }

    fun decreaseOwned(paintId: Long) = viewModelScope.launch(Dispatchers.IO) {
        repository.decreaseOwned(paintId)
    }

    fun increaseOwned(paintId: Long) = viewModelScope.launch(Dispatchers.IO) {
        repository.increaseOwned(paintId)
    }
}