package com.example.health_diary

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.health_diary.databinding.ActivityMainBinding
import com.example.health_diary.databinding.FragmentHealthCategoryBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HealthCategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val homeFragment = HomeFragment()
    private val healthCategoryFragment = HealthCategoryFragment()
    private val settingFragment = SettingFragment()
    private val toolsFragment = ToolsFragment()
    private val summaryFragment = SummaryFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(healthCategoryFragment)

        val navigate = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val menu : Menu = navigate.menu
        val item : MenuItem = menu.getItem(2)
        item.isChecked = true

        navigate.setOnItemSelectedListener {
            when(it.itemId){
                R.id.ic_home -> replaceFragment(homeFragment)
                R.id.ic_healthCategory -> replaceFragment(healthCategoryFragment)
                R.id.ic_setting -> replaceFragment(settingFragment)
                R.id.ic_tools -> replaceFragment(toolsFragment)
                R.id.ic_summary -> replaceFragment(summaryFragment)
                else -> replaceFragment(healthCategoryFragment)
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}

