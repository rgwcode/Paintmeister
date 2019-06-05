package lu.weidig.paintmeister.data.dao

import androidx.room.Dao
import androidx.room.Query
import lu.weidig.paintmeister.data.entity.PaintLine

@Dao
interface PaintLineDao : BasicDao<PaintLine> {
    @Query("SELECT * from PaintLine ORDER BY name ASC")
    fun getAllSortedByName(): List<PaintLine>
}
