package com.example.health_diary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.health_diary.R
import com.example.health_diary.databinding.FragmentFruitAndVegetable1Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FruitAndVegetable1Fragment :Fragment(R.layout.fragment_fruit_and_vegetable1){
    private lateinit var binding: FragmentFruitAndVegetable1Binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentFruitAndVegetable1Binding.bind(view)

        (activity as AppCompatActivity).supportActionBar?.title = "Fruit And Vegetable"

        binding.backFoodpyramidButton.setOnClickListener {
            val action = FruitAndVegetable1FragmentDirections.actionFruitAndVegetable1FragmentToFoodPyramidFragment()
            findNavController().navigate(action)
        }

        binding.nextButton.setOnClickListener {
            val action = FruitAndVegetable1FragmentDirections.actionFruitAndVegetable1FragmentToFruitAndVegetable2Fragment()
            findNavController().navigate(action)
        }

    }
}