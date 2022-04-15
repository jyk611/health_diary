package com.example.health_diary.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.health_diary.R
import com.example.health_diary.databinding.FragmentMeatAndProteinBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeatAndProteinFragment :Fragment(R.layout.fragment_meat_and_protein){

    private lateinit var binding: FragmentMeatAndProteinBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMeatAndProteinBinding.bind(view)

        (activity as AppCompatActivity).supportActionBar?.title = "Meat And Protein"

        binding.backFoodpyramidButton.setOnClickListener {
            val action = MeatAndProteinFragmentDirections.actionMeatAndProteinFragmentToFoodPyramidFragment()
            findNavController().navigate(action)
        }
    }



}