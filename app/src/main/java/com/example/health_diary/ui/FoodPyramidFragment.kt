package com.example.health_diary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.health_diary.R
import com.example.health_diary.databinding.FragmentFoodPyramidBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodPyramidFragment : Fragment(R.layout.fragment_food_pyramid) {

  private lateinit var binding: FragmentFoodPyramidBinding

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding = FragmentFoodPyramidBinding.bind(view)

    (activity as AppCompatActivity).supportActionBar?.title = "Food Pyramid"

    binding.backFoodplanButton.setOnClickListener {
      val action = FoodPyramidFragmentDirections.actionFoodPyramidFragmentToMainFragment()
      findNavController().navigate(action)
    }

    binding.fatsweetPyramid.setOnClickListener {
      val action = FoodPyramidFragmentDirections.actionFoodPyramidFragmentToFatsAndSweetsFragment()
      findNavController().navigate(action)
    }

    binding.milkdiaryPyramid.setOnClickListener {
      val action = FoodPyramidFragmentDirections.actionFoodPyramidFragmentToMilkAndDiaryFragment()
      findNavController().navigate(action)
    }

    binding.meatproteinPyramid.setOnClickListener {
      val action = FoodPyramidFragmentDirections.actionFoodPyramidFragmentToMeatAndProteinFragment()
      findNavController().navigate(action)
    }

    binding.fruitvegePyramid.setOnClickListener {
      val action = FoodPyramidFragmentDirections.actionFoodPyramidFragmentToFruitAndVegetable1Fragment()
      findNavController().navigate(action)
    }

    binding.ricegrainPyramid.setOnClickListener {
      val action = FoodPyramidFragmentDirections.actionFoodPyramidFragmentToRiceAndGrainsFragment()
      findNavController().navigate(action)
    }
  }


}