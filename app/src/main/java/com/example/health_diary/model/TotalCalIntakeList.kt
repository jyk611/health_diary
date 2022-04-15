package com.example.health_diary.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class TotalCalIntakeList(
  @PrimaryKey(autoGenerate = true)
  val id: Int = 0,

  @ColumnInfo(name = "foodName")
  var foodName: String,

  @ColumnInfo(name = "foodType")
  var foodType: String,

  @ColumnInfo(name = "foodCal")
  var foodCal: String,

  @ColumnInfo(name = "createdAt")
  val createdAt: Date,

//  @ColumnInfo(name = "total_cal_date")
//  var date: Date


)

