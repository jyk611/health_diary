package com.example.health_diary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.health_diary.R
import com.example.health_diary.databinding.FragmentMilkAndDiaryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MilkAndDiaryFragment : Fragment(R.layout.fragment_milk_and_diary){
   private lateinit var binding: FragmentMilkAndDiaryBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMilkAndDiaryBinding.bind(view)

        (activity as AppCompatActivity).supportActionBar?.title = "Milk And Dairy"

        binding.backFoodpyramidButton.setOnClickListener {
            val action = MilkAndDiaryFragmentDirections.actionMilkAndDiaryFragmentToFoodPyramidFragment()
            findNavController().navigate(action)
        }
    }

}