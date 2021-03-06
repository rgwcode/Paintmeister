package lu.weidig.paintmeister.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Manufacturer(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var name: String = "",
    var website: String = ""
)