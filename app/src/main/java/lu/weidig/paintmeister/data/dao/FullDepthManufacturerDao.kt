package lu.weidig.paintmeister.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import lu.weidig.paintmeister.data.pojo.FullDepthManufacturer

@Dao
interface FullDepthManufacturerDao {
    @Transaction
    @Query("SELECT * FROM Manufacturer ORDER BY NAME ASC")
    fun getAllSortedByName(): LiveData<List<FullDepthManufacturer>>
}