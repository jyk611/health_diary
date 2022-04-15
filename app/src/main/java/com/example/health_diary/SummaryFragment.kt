package com.example.health_diary

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.health_diary.databinding.FragmentSummaryBinding


class SummaryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSummaryBinding.inflate(layoutInflater)

        binding.goSleepSummaryButton.setOnClickListener {
            val intent = Intent(context,SleepSummaryActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}