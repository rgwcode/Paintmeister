package lu.weidig.paintmeister.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Manufacturer::class,
            parentColumns = ["id"],
            childColumns = ["manufacturerId"],
            onDelete = ForeignKey.NO_ACTION
        )
    ],
    indices = [
        Index("manufacturerId")
    ]
)
data class PaintLine(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var name: String = "",
    var manufacturerId: Long? = null
)