package lu.weidig.paintmeister.data.dao

import androidx.room.Dao
import androidx.room.Query
import lu.weidig.paintmeister.data.entity.Manufacturer

@Dao
interface ManufacturerDao : BasicDao<Manufacturer> {
    @Query("SELECT * FROM Manufacturer ORDER BY name ASC")
    fun getAllSortedByName(): List<Manufacturer>

    @Query("SELECT * FROM Manufacturer WHERE id = :id")
    fun getById(id: Int): Manufacturer?

    @Query("SELECT * FROM Manufacturer WHERE name = :name")
    fun getByName(name: String): Manufacturer?

    // Used to test for empty database
    @Query("SELECT * FROM MANUFACTURER LIMIT 1")
    fun getAny(): Manufacturer?
}