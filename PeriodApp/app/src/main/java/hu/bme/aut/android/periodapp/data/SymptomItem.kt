package hu.bme.aut.android.periodapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.Date

@Entity(tableName = "symptomitem")
data class SymptomItem(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "bleeding") var bleeding: Bleeding,
    @ColumnInfo(name = "pain") var pain: Pain,
    @ColumnInfo(name = "emotions") var emotions: Emotions,
    @ColumnInfo(name = "other") var other: Other,
    @ColumnInfo(name = "description") var description: String
) {
    enum class Bleeding {
        LIGHT, MEDIUM, HEAVY, SPOTTING;

        companion object {
            @JvmStatic
            @TypeConverter
            fun getByOrdinal(ordinal: Int): Bleeding? {
                var ret: Bleeding? = null
                for (ble in values()) {
                    if (ble.ordinal == ordinal) {
                        ret = ble
                        break
                    }
                }
                return ret
            }

            @JvmStatic
            @TypeConverter
            fun toInt(bleeding: Bleeding): Int {
                return bleeding.ordinal
            }
        }
    }

    enum class Pain {
        CRAMPS, HEADACHE, TENDER_BREASTS;

        companion object {
            @JvmStatic
            @TypeConverter
            fun getByOrdinal(ordinal: Int): Pain? {
                var ret: Pain? = null
                for (ble in values()) {
                    if (ble.ordinal == ordinal) {
                        ret = ble
                        break
                    }
                }
                return ret
            }

            @JvmStatic
            @TypeConverter
            fun toInt(pain: Pain): Int {
                return pain.ordinal
            }
        }
    }

    enum class Emotions {
        HAPPY, SENSITIVE, SAD, PMS;

        companion object {
            @JvmStatic
            @TypeConverter
            fun getByOrdinal(ordinal: Int): Emotions? {
                var ret: Emotions? = null
                for (ble in values()) {
                    if (ble.ordinal == ordinal) {
                        ret = ble
                        break
                    }
                }
                return ret
            }

            @JvmStatic
            @TypeConverter
            fun toInt(emotions: Emotions): Int {
                return emotions.ordinal
            }
        }
    }

    enum class Other {
        LOW_HUNGER;

        companion object {
            @JvmStatic
            @TypeConverter
            fun getByOrdinal(ordinal: Int): Other? {
                var ret: Other? = null
                for (ble in values()) {
                    if (ble.ordinal == ordinal) {
                        ret = ble
                        break
                    }
                }
                return ret
            }

            @JvmStatic
            @TypeConverter
            fun toInt(other: Other): Int {
                return other.ordinal
            }
        }
    }
}