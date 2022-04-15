package com.example.health_diary.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.health_diary.model.TotalCalIntakeList
import java.util.*

@Dao
interface FoodDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(data: TotalCalIntakeList)

  @Query("SELECT * FROM totalcalintakelist")
  fun getAllFood(): LiveData<List<TotalCalIntakeList>>

  @Delete
  suspend fun delete(data: TotalCalIntakeList)

  @Query("UPDATE totalcalintakelist SET foodName = :fName, foodType = :fType, foodCal = :fCal WHERE id = :id")
  suspend fun edit(id: Int, fName: String, fType: String, fCal: String)

  @Query("SELECT foodCal FROM totalcalintakelist")
  fun getCalories(): LiveData<List<String>>

//  @Query("SELECT foodType, foodCal FROM totalcalintakelist")
//  fun getCaloriesPieChart(foodType: String.Companion, foodCal: String.Companion):List<String>

  //get date
  //@Query("SELECT ")
  //fun getCalTrackDate(currentDate: Date)
}