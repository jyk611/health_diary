package com.example.health_diary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.health_diary.R
import com.example.health_diary.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

  private lateinit var binding: FragmentMainBinding

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    binding = FragmentMainBinding.bind(view)

    (activity as AppCompatActivity).supportActionBar?.title = "Food Plan"

    binding.foodpyramidPagebox.setOnClickListener {
      val action = MainFragmentDirections.actionMainFragmentToFoodPyramidFragment()
      findNavController().navigate(action)
    }

    binding.caloriestrackerPagebox.setOnClickListener {
      val action = MainFragmentDirections.actionMainFragmentToCaloriesTrackerFragment()
      findNavController().navigate(action)
    }
  }

}