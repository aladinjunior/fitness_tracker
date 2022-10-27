package co.aladinjunior.fitnesstracker.model

import androidx.room.TypeConverter
import java.util.*

object DateConverter {
    @TypeConverter
    fun toDate(longDate: Long?) : Date? {
        return if (longDate != null) Date(longDate) else null
    }



    @TypeConverter
    fun fromDate(date: Date?) : Long?{
        return date?.time
    }
}