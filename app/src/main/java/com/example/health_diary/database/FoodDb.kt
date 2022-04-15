package com.example.health_diary.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.health_diary.model.TotalCalIntakeList
import java.util.*


@Database(
  version = 1,
  entities = [
    TotalCalIntakeList::class
  ],
  exportSchema = false
)
@TypeConverters(DbConverter::class)
abstract class FoodDb: RoomDatabase() {
  abstract fun foodDao(): FoodDao
}

//class DateTypeConverter{
//  @TypeConverters
//  fun fromTimestamp(value: Long?): Date? {
//    return if (value == null) null else Date(value)
//  }
//
//  @TypeConverters
//  fun dateToTimestamp(date: Date?):Long?{
//    return date?.time
//  }
//
//}