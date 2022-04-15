package com.example.health_diary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.health_diary.R
import com.example.health_diary.databinding.FragmentRiceAndGrainsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RiceAndGrainsFragment : Fragment(R.layout.fragment_rice_and_grains){
   private lateinit var binding : FragmentRiceAndGrainsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentRiceAndGrainsBinding.bind(view)

        (activity as AppCompatActivity).supportActionBar?.title = "Rice And Grains"

        binding.backFoodpyramidButton.setOnClickListener {
            val action = RiceAndGrainsFragmentDirections.actionRiceAndGrainsFragmentToFoodPyramidFragment()
            findNavController().navigate(action)
        }
    }
}