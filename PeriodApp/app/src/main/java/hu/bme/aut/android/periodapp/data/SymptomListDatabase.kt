package hu.bme.aut.android.periodapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [SymptomItem::class], version = 1)
@TypeConverters(value = [SymptomItem.Bleeding::class,
                         SymptomItem.Pain::class,
                         SymptomItem.Emotions::class,
                         SymptomItem.Hunger::class])
abstract class SymptomListDatabase : RoomDatabase() {
    abstract fun symptomItemDao(): SymptomItemDao

    companion object {
        fun getDatabase(applicationContext: Context): SymptomListDatabase {
            return Room.databaseBuilder(
                applicationContext,
                SymptomListDatabase::class.java,
                "symptom-list"
            ).build();
        }
    }
}