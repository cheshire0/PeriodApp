package hu.bme.aut.android.periodapp.data.categories

import androidx.room.TypeConverter

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