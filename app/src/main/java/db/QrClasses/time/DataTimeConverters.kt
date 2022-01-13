package db.QrClasses.time

import androidx.room.TypeConverter
import java.util.*

class DataTimeConverters {

    @TypeConverter
    fun toCalender(l :Long) : Calendar{
        val c = Calendar.getInstance()
        c!!.timeInMillis = l
        return  c
    }
  @TypeConverter
  fun fromCalender(c :Calendar?): Long? {
      return c?.time?.time
  }
}