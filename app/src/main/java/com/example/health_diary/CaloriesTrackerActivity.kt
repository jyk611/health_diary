package com.example.health_diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.health_diary.databinding.FragmentCaloriesTrackerBinding
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.health_diary.R
import com.example.health_diary.database.FoodDb
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CaloriesTrackerActivity : AppCompatActivity() {

    private lateinit var binding: FragmentCaloriesTrackerBinding
    private lateinit var navHostFrag: NavHostFragment
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentCaloriesTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFrag = supportFragmentManager.findFragmentById(R.id.main_container_view) as NavHostFragment
        navController = navHostFrag.navController

//        binding.foodpyramidPagebox.setOnClickListener {
//            startActivity(Intent(this, FoodPyramid::class.java))
//        }
//
//        binding.caloriestrackerPagebox.setOnClickListener{
//            startActivity(Intent(this, CaloriesTracker::class.java))
//        }
    }
}