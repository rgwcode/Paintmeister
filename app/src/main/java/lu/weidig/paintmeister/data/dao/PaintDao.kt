package lu.weidig.paintmeister.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import lu.weidig.paintmeister.data.entity.Paint

@Dao
interface PaintDao : BasicDao<Paint> {
    @Query("SELECT * from Paint ORDER BY name ASC")
    fun getAllSortedByName(): LiveData<List<Paint>>

    @Query("SELECT * from Paint WHERE id = :paintId")
    fun getPaintById(paintId: Long): Paint

    @Update
    fun updatePaint(paint: Paint)
}