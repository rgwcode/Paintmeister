package lu.weidig.paintmeister.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import eu.davidea.flexibleadapter.items.IFlexible
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import lu.weidig.paintmeister.data.PaintmeisterRepository
import lu.weidig.paintmeister.data.pojo.FullDepthManufacturer
import lu.weidig.paintmeister.item.ManufacturerItem
import lu.weidig.paintmeister.item.PaintItem
import lu.weidig.paintmeister.item.PaintLineItem

class PaintListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PaintmeisterRepository =
        PaintmeisterRepository(application, viewModelScope)

    val fullDepthManufacturers = MediatorLiveData<ArrayList<IFlexible<ManufacturerItem
    .ManufacturerItemViewHolder>>>()
    private val dbFullDepthManufacturers: LiveData<List<FullDepthManufacturer>>

    init {
        dbFullDepthManufacturers = repository.fullDepthManufacturers
        fullDepthManufacturers.addSource(dbFullDepthManufacturers) {
            if (isDataInitialized()) {
                val manufacturerList = ArrayList<IFlexible<ManufacturerItem
                .ManufacturerItemViewHolder>>()
                for (fullDepthManufacturer in it) {
                    val manufacturerItem = ManufacturerItem(fullDepthManufacturer.manufacturer)
                    manufacturerList.add(manufacturerItem)
                    for (paintLine in fullDepthManufacturer.paintLinesByName) {
                        val paintLineItem = PaintLineItem(paintLine.paintLine, manufacturerItem)
                        manufacturerItem.addSubItem(paintLineItem)
                        for (paint in paintLine.paintsByName) {
                            paintLineItem.addSubItem(PaintItem(paint, paintLineItem))
                        }
                    }
                }
                fullDepthManufacturers.value = manufacturerList
            }
        }
    }

    fun decreaseOwned(paintId: Long) = viewModelScope.launch(Dispatchers.IO) {
        repository.decreaseOwned(paintId)
    }

    fun increaseOwned(paintId: Long) = viewModelScope.launch(Dispatchers.IO) {
        repository.increaseOwned(paintId)
    }

    fun isDataInitialized(): Boolean {
        return repository.isDataInitialized()
    }
}