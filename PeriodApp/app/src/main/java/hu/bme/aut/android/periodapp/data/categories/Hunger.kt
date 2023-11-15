package hu.bme.aut.android.periodapp.data.categories

import androidx.room.TypeConverter

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