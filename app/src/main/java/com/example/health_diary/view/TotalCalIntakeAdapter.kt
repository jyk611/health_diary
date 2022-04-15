package com.example.health_diary.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.health_diary.databinding.ListTotalCaloriesIntakeBinding
import com.example.health_diary.model.TotalCalIntakeList
import timber.log.Timber

class TotalCalIntakeAdapter(
  var foodList: List<TotalCalIntakeList>,
  private val onMenuClick: (TotalCalIntakeList, View) -> Unit
) : RecyclerView.Adapter<TotalCalIntakeAdapter.TotalCalIntakeVH>() {

  fun insertData(fdList: List<TotalCalIntakeList>) {
    foodList = fdList
    notifyDataSetChanged()
  }

  inner class TotalCalIntakeVH(private val binding: ListTotalCaloriesIntakeBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(totalCalIntakeList: TotalCalIntakeList) {
      with(binding) {
        fName.text = "Food Name: " + totalCalIntakeList.foodName
        fType.text = "Food Type: " + totalCalIntakeList.foodType
        fCal.text = "Calories: " + totalCalIntakeList.foodCal
        fMenus.setOnClickListener {
          onMenuClick(totalCalIntakeList, it)
        }
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TotalCalIntakeVH {
    val binding = ListTotalCaloriesIntakeBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    )
    return TotalCalIntakeVH(binding)
  }

  override fun onBindViewHolder(holder: TotalCalIntakeVH, position: Int) {
      holder.bind(foodList[position])
  }

  override fun getItemCount(): Int {
    return foodList.size
  }

}