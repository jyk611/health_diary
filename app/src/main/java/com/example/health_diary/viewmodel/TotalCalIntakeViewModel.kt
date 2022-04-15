package com.example.health_diary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.health_diary.database.FoodDao
import com.example.health_diary.model.TotalCalIntakeList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TotalCalIntakeViewModel @Inject constructor(
  private val foodDao: FoodDao
): ViewModel() {

  val allFood = foodDao.getAllFood()
  val allCalories = foodDao.getCalories()

  //fun allLiveCaloriesData (foodType: String.Companion, foodCal: String.Companion) = foodDao.getCaloriesPieChart(foodType, foodCal)

  fun addFood(foodName: String, foodType: String, foodCal: String) {
    viewModelScope.launch {
      foodDao.insert(
        TotalCalIntakeList(
          foodName = foodName,
          foodType = foodType,
          foodCal = foodCal,
          createdAt = Calendar.getInstance().time
        )
      )
    }
  }

  fun deleteFood(food: TotalCalIntakeList) {
    viewModelScope.launch {
      foodDao.delete(food)
    }
  }

  fun editFood(id: Int, fName: String, fType: String, fCal: String) {
    viewModelScope.launch {
      foodDao.edit(
        id, fName, fType, fCal
      )
    }
  }
}