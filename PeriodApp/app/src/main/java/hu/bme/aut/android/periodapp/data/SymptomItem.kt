package hu.bme.aut.android.periodapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import hu.bme.aut.android.periodapp.data.categories.Bleeding

@Entity(tableName = "symptomitem")
data class SymptomItem(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "category") var category: String,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "description") var description: String?= null
)