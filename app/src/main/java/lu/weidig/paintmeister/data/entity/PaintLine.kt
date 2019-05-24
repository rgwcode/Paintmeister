package lu.weidig.paintmeister.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Manufacturer::class,
            parentColumns = ["id"],
            childColumns = ["manufacturerId"],
            onDelete = ForeignKey.NO_ACTION
        )
    ]
)
data class PaintLine(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    var name: String = "",
    var manufacturerId: Long? = null
)