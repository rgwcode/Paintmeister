package lu.weidig.paintmeister.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = PaintLine::class,
            parentColumns = ["id"],
            childColumns = ["paintLineId"],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class Paint(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    var name: String = "",
    var color: String = "#FFFFFF",
    var sku: String = "",
    var paintLineId: Long? = null
)