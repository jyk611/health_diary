package com.example.health_diary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.health_diary.R
import com.example.health_diary.databinding.FragmentFatsAndSweetsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FatsAndSweetsFragment : Fragment(R.layout.fragment_fats_and_sweets){

    private lateinit var binding: FragmentFatsAndSweetsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentFatsAndSweetsBinding.bind(view)

        (activity as AppCompatActivity).supportActionBar?.title = "Fats And Sweets"

        binding.backFoodpyramidButton.setOnClickListener {
            val action = FatsAndSweetsFragmentDirections.actionFatsAndSweetsFragmentToFoodPyramidFragment()
            findNavController().navigate(action)
        }
    }


}