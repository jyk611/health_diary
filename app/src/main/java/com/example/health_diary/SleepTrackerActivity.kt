package com.example.health_diary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.example.health_diary.databinding.SleepTrackerBinding
import com.google.firebase.firestore.*


class SleepTracker : AppCompatActivity() {

    private lateinit var binding: SleepTrackerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SleepTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar
        actionBar!!.title = "Sleep Tracker"
        actionBar.setDisplayHomeAsUpEnabled(true)

        loadData()

        loadDataFromSchedule()

        val schedule = binding.scheduleTitle.text.toString()
        if(schedule == "null" || schedule == ""){
            binding.scheduleTitle.text = "SCHEDULE"
            binding.bedTime.text = "-"
            binding.wakeUpTime.text = "-"
            binding.goalHoursText.text = "-"
        }

        binding.sleepButton.setOnClickListener { lullabies() }
        binding.scheduleCardView.setOnClickListener { sleepSchedule() }
        //binding.backButtonTracker.setOnClickListener { healthCategory() }
    }

    private fun loadDataFromSchedule(){
        val schedule = intent.getStringExtra("scheduleChosen").toString()
        val bedTime = intent.getStringExtra("bedTime").toString()
        val wakeTime = intent.getStringExtra("wakeUpTime").toString()
        val sleepGoal = intent.getStringExtra("sleepGoal").toString()

        if(schedule!="null" && bedTime!="null" && wakeTime!="null" && sleepGoal!="null" ){
            binding.scheduleTitle.text = getString(R.string.schedule_edit, schedule)
            binding.bedTime.text = getString(R.string.schedule_edit, bedTime)
            binding.wakeUpTime.text = getString(R.string.schedule_edit, wakeTime)
            binding.goalHoursText.text = getString(R.string.goal_hours_edit, sleepGoal.toInt())
        }
    }

    private fun lullabies() {
        saveData()
        val intent = Intent(this, Lullabies::class.java)
        startActivity(intent)
    }

    private fun sleepSchedule() {
        saveData()
        val intent = Intent(this, SleepSchedule::class.java)
        startActivity(intent)
    }

    private fun healthCategory() {
        saveData()
        val intent = Intent(this, HealthCategoryActivity::class.java)
        var db: FirebaseFirestore = FirebaseFirestore.getInstance()

        val timeList: MutableMap<String, Any> = HashMap()
        timeList["wakeTime"] = binding.wakeUpTime.text.toString()
        timeList["sleepTime"] = binding.bedTime.text.toString()

        db.collection("Time").document("HealthCategory").set(timeList)

        startActivity(intent)
    }

    private fun saveData() {
        var scheduleChosen = binding.scheduleTitle.text
        var bedTime = binding.bedTime.text
        var wakeTime = binding.wakeUpTime.text
        var sleepGoal = binding.goalHoursText.text

        binding.scheduleTitle.text = scheduleChosen
        binding.bedTime.text = bedTime
        binding.wakeUpTime.text = wakeTime
        binding.goalHoursText.text = sleepGoal

        val sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("SCHEDULE_CHOSEN", scheduleChosen.toString())
            putString("BED_TIME", bedTime.toString())
            putString("WAKE_TIME", wakeTime.toString())
            putString("SLEEP_GOAL", sleepGoal.toString())
        }.apply()
    }

    private fun loadData() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)

        val scheduleChosen = sharedPreferences.getString("SCHEDULE_CHOSEN", null)
        val bedTime = sharedPreferences.getString("BED_TIME", null)
        val wakeTime = sharedPreferences.getString("WAKE_TIME", null)
        val sleepGoal = sharedPreferences.getString("SLEEP_GOAL", null)

        binding.scheduleTitle.text = scheduleChosen
        binding.bedTime.text = bedTime
        binding.wakeUpTime.text = wakeTime
        binding.goalHoursText.text = sleepGoal
    }

    override fun onStop() {
        super.onStop()
        saveData()
    }

    override fun onSupportNavigateUp(): Boolean {
        healthCategory()
        onBackPressed()
        return true
    }

}