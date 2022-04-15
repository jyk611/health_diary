package com.example.health_diary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.health_diary.R
import com.example.health_diary.databinding.FragmentTotalFoodCaloriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TotalFoodCaloriesFragment : Fragment(R.layout.fragment_total_food_calories){

    private lateinit var binding: FragmentTotalFoodCaloriesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentTotalFoodCaloriesBinding.bind(view)

    }
}
