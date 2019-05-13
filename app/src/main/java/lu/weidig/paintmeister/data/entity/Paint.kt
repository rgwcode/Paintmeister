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
    indices = [Index(value = ["manufacturerId"])]
)
data class Paint(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    var name: String = "",
    var color: String = "#FFFFFF",
    var sku: String = "",
    var manufacturerId: Long? = null
)