package hu.bme.aut.android.periodapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface SymptomItemDao {
    @Query("SELECT * FROM symptomitem")
    fun getAll(): List<SymptomItem>

    @Insert
    fun insert(shoppingItems: SymptomItem): Long

    @Update
    fun update(shoppingItem: SymptomItem)

    @Delete
    fun deleteItem(shoppingItem: SymptomItem)
}