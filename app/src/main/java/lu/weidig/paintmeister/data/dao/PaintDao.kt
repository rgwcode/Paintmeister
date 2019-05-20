package lu.weidig.paintmeister.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import lu.weidig.paintmeister.data.entity.Paint

@Dao
interface PaintDao : BasicDao<Paint> {
    @Query("SELECT * from Paint ORDER BY name ASC")
    fun getAllSortedByName(): LiveData<List<Paint>>

    @Query("SELECT * from Paint ORDER BY color ASC")
    fun getAllSortedByColor(): LiveData<List<Paint>>

    @Query("DELETE FROM Paint")
    fun deleteAll()
}