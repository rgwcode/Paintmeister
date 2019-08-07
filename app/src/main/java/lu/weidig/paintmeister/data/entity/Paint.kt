package lu.weidig.paintmeister.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = PaintLine::class,
            parentColumns = ["id"],
            childColumns = ["paintLineId"],
            onDelete = ForeignKey.NO_ACTION
        )
    ],
    indices = [
        Index("paintLineId")
    ]
)
data class Paint(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var name: String = "",
    var color: String = "#FFFFFF",
    var sku: String = "",
    var numOwned: Int = 0,
    // for special things like thinners, mediums...
    var nonPaint: Boolean = false,
    var metallic: Boolean = false,
    var wishListed: Boolean = false,
    var notes: String = "",
    var paintLineId: Long? = null
)