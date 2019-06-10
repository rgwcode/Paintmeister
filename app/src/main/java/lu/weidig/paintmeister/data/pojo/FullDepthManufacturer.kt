package lu.weidig.paintmeister.data.pojo

import androidx.room.Embedded
import androidx.room.Relation
import lu.weidig.paintmeister.data.entity.Manufacturer
import lu.weidig.paintmeister.data.entity.PaintLine

data class FullDepthManufacturer(
    @Embedded
    val manufacturer: Manufacturer,
    @Relation(parentColumn = "id", entityColumn = "manufacturerId", entity = PaintLine::class)
    val paintLines: List<PaintLineWithPaints>
) {
    val paintLinesByName: List<PaintLineWithPaints>
        get() {
            return paintLines.sortedBy { it.paintLine.name }
        }
}