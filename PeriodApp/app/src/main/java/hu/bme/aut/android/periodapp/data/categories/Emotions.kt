package hu.bme.aut.android.periodapp.data.categories

import androidx.room.TypeConverter

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