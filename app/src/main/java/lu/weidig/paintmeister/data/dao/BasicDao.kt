package lu.weidig.paintmeister.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface BasicDao<T> {
    /**
     * Insert an element in the database.
     *
     * @param element the element to be inserted.
     */
    @Insert
    fun insert(element: T)

    /**
     * Insert a collection of elements in the database.
     *
     * @param collection the collection to be inserted.
     */
    @Insert
    fun insert(collection: Collection<T>)

    /**
     * Update an element in the database.
     *
     * @param element the object to be updated
     */
    @Update
    fun update(element: T)

    /**
     * Delete an element from the database
     *
     * @param element the object to be deleted
     */
    @Delete
    fun delete(element: T)
}
