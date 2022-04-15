package com.example.health_diary

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.example.health_diary.databinding.FragmentToolsBinding

class ToolsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "Tools"

        val binding = FragmentToolsBinding.inflate(layoutInflater)

        binding.toolsBmiCalculatorBox.setOnClickListener{
            val intent = Intent(context, BMICalculatorActivity::class.java)
            startActivity(intent)
        }

        binding.toolsCaloriesCalculatorBox.setOnClickListener{
            val intent = Intent(context, CaloriesCalculatorActivity::class.java)
            startActivity(intent)
        }

        binding.toolsWaterIntakeCalculatorBox.setOnClickListener{
            val intent = Intent(context, WaterIntakeCalculatorActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }
}