package lu.weidig.paintmeister.data.pojo

import androidx.room.Embedded
import androidx.room.Relation
import lu.weidig.paintmeister.data.entity.Paint
import lu.weidig.paintmeister.data.entity.PaintLine

data class PaintLineWithPaints(
    @Embedded
    val paintLine: PaintLine,
    @Relation(parentColumn = "id", entityColumn = "paintLineId", entity = Paint::class)
    val paints: List<Paint>
)
