package com.example.health_diary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.health_diary.R
import com.example.health_diary.databinding.FragmentFruitAndVegetable2Binding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FruitAndVegetable2Fragment : Fragment(R.layout.fragment_fruit_and_vegetable2){
    private lateinit var binding : FragmentFruitAndVegetable2Binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentFruitAndVegetable2Binding.bind(view)

        (activity as AppCompatActivity).supportActionBar?.title = "Fruit And Vegetable"

        binding.backButton.setOnClickListener {
            val action = FruitAndVegetable2FragmentDirections.actionFruitAndVegetable2FragmentToFruitAndVegetable1Fragment()
            findNavController().navigate(action)
        }
    }
}