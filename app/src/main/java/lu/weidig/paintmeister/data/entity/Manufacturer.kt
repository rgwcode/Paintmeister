package lu.weidig.paintmeister.data.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Manufacturer(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    var name: String = "",
    var website: String = ""
) {
    @Ignore
    var paintlines: List<PaintLine> = listOf()
}