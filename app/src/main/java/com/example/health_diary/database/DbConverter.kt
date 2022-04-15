package com.example.health_diary.database

import androidx.room.TypeConverter
import java.util.*

object DbConverter {
  @TypeConverter
  fun fromTimestamp(value: Long?): Date? {
    return value?.let { Date(it) }
  }

  @TypeConverter
  fun dateToTimestamp(date: Date?): Long? {
    return date?.time?.toLong()
  }
}