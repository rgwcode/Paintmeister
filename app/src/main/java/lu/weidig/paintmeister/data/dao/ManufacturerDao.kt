package lu.weidig.paintmeister.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import lu.weidig.paintmeister.data.entity.Manufacturer

@Dao
interface ManufacturerDao {
    @Insert
    suspend fun insert(manufacturer: Manufacturer): Long

    @Query("SELECT * FROM Manufacturer ORDER BY name ASC")
    fun getAll(): LiveData<List<Manufacturer>>

    @Query("SELECT * FROM Manufacturer WHERE id = :id")
    fun getById(id: Int): Manufacturer?

    @Query("SELECT * FROM Manufacturer WHERE name = :name")
    fun getByName(name: String): Manufacturer?

    @Query("DELETE FROM Manufacturer")
    fun deleteAll()

    // Used to test for empty database
    @Query("SELECT * FROM MANUFACTURER LIMIT 1")
    fun getAny(): Manufacturer?
}