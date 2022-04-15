package com.example.health_diary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.health_diary.R
import com.example.health_diary.databinding.FragmentFoodPlanBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodPlanFragment : Fragment(R.layout.fragment_food_plan) {

  private lateinit var binding: FragmentFoodPlanBinding

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding = FragmentFoodPlanBinding.bind(view)

    (activity as AppCompatActivity).supportActionBar?.title = "Food Plan"

    binding.foodpyramidPagebox.setOnClickListener {
      val action = FoodPlanFragmentDirections.actionFoodPlanFragmentToFoodPyramidFragment()
      findNavController().navigate(action)
    }

    binding.caloriestrackerPagebox.setOnClickListener {
      val action = FoodPlanFragmentDirections.actionFoodPlanFragmentToCaloriesTrackerFragment()
      findNavController().navigate(action)
    }
  }

}