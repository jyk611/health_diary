package com.example.health_diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.health_diary.databinding.ActivityMainBinding
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.health_diary.database.FoodDb
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.text.Typography.dagger

@AndroidEntryPoint
class FoodCaloriesMain : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFrag: NavHostFragment
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navHostFrag = supportFragmentManager.findFragmentById(R.id.main_container_view) as NavHostFragment
        navController = navHostFrag.navController

    }
}