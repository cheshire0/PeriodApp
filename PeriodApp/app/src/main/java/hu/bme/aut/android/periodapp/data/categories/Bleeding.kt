package hu.bme.aut.android.periodapp.data.categories

import androidx.room.TypeConverter

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