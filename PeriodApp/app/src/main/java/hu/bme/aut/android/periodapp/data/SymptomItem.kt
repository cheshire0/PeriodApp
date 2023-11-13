package hu.bme.aut.android.periodapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "symptomitem")
data class SymptomItem(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "bleeding") var bleeding: Bleeding?= null,
    @ColumnInfo(name = "pain") var pain: Pain?= null,
    @ColumnInfo(name = "emotions") var emotions: Emotions?= null,
    @ColumnInfo(name = "hunger") var hunger: Hunger?= null,
    @ColumnInfo(name = "description") var description: String?= null
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
            fun toInt(bleeding: Bleeding?): Int {
                return bleeding?.ordinal ?: 0
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
            fun toInt(pain: Pain?): Int {
                if(pain!=null)
                    return pain.ordinal
                return 0
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
            fun toInt(emotions: Emotions?): Int {
                if(emotions!=null)
                    return emotions.ordinal
                return 0
            }
        }
    }

    enum class Hunger {
        LOW, MEDIUM, HIGH;

        companion object {
            @JvmStatic
            @TypeConverter
            fun getByOrdinal(ordinal: Int): Hunger? {
                var ret: Hunger? = null
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
            fun toInt(other: Hunger?): Int {
                if(other!=null)
                    return other.ordinal
                return 0
            }
        }
    }
}