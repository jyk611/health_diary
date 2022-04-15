package com.example.health_diary

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.TimePicker
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.example.health_diary.databinding.SleepScheduleBinding
import java.util.*


class SleepSchedule : AppCompatActivity() {

    private lateinit var binding: SleepScheduleBinding
    private var sleepHour = 0
    private var sleepMin = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SleepScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar
        actionBar!!.title = "Sleep Schedule"
        actionBar.setDisplayHomeAsUpEnabled(true)

        loadData()

        if(binding.sleepGoalHours.text == ""){
            binding.sleepGoalHours.text = getString(R.string.goal_hours_schedule, 8)
        }

//        binding.backSleepTrackerButton.setOnClickListener { sleepTracker() }

        binding.addGoalButton.setOnClickListener {
            addGoalHours()
        }

        binding.minusGoalButton.setOnClickListener {
            minusGoalHours()
        }

        binding.everydaySwitch.setOnCheckedChangeListener { compoundButton, b ->
            if (binding.everydaySwitch.isChecked) {
                binding.weekdaySwitch.isChecked = false
                binding.weekendSwitch.isChecked = false
            } else {
                binding.everydaySwitch.isChecked = false
            }
        }

        binding.weekdaySwitch.setOnCheckedChangeListener { compoundButton, b ->
            if (binding.weekdaySwitch.isChecked) {
                binding.everydaySwitch.isChecked = false
                binding.weekendSwitch.isChecked = false
            } else {
                binding.weekdaySwitch.isChecked = false
            }
        }

        binding.weekendSwitch.setOnCheckedChangeListener { compoundButton, b ->
            if (binding.weekendSwitch.isChecked) {
                binding.everydaySwitch.isChecked = false
                binding.weekdaySwitch.isChecked = false
            } else {
                binding.weekendSwitch.isChecked = false
            }
            // setContent = true
        }

        binding.everydayEdit.setOnClickListener {
            clickSleep("everyday")
        }

        binding.weekdayEdit.setOnClickListener {
            clickSleep("weekday")
        }

        binding.weekendEdit.setOnClickListener {
            clickSleep("weekend")
        }
    }

    private fun sleepTracker() {
        saveData()
        val intent = Intent(this, SleepTracker::class.java)
        var scheduleChosen = ""
        var bedTime = ""
        var wakeUpTime = ""
        val sleepGoal = binding.sleepGoalHours.text.toString()

        when {
            binding.everydaySwitch.isChecked -> {
                scheduleChosen = "Everyday"
                bedTime = binding.everydayBedtimeField.text.toString()
                wakeUpTime = binding.everydayWakeTimeField.text.toString()
            }
            binding.weekdaySwitch.isChecked -> {
                scheduleChosen = "Weekday"
                bedTime = binding.weekdayBedtimeField.text.toString()
                wakeUpTime = binding.weekdayWakeTimeField.text.toString()
            }
            binding.weekendSwitch.isChecked -> {
                scheduleChosen = "Weekend"
                bedTime = binding.weekEndBedtimeField.text.toString()
                wakeUpTime = binding.weekEndWakeTimeField.text.toString()
            }
        }

        intent.putExtra("scheduleChosen", scheduleChosen)
        intent.putExtra("bedTime", bedTime)
        intent.putExtra("wakeUpTime", wakeUpTime)
        intent.putExtra("sleepGoal", sleepGoal)
        intent.putExtra("sleepHour", sleepHour.toString())
        intent.putExtra("sleepMin", sleepMin.toString())

        startActivity(intent)
    }

    private fun addGoalHours() {
        if(binding.sleepGoalHours.text.toString()!=""){
            var goal = binding.sleepGoalHours.text.toString().toInt()
            goal++
            if (goal > 12) {
                goal--
                Toast.makeText(
                    applicationContext,
                    "Sleep Duration should not more than 12 hours.",
                    LENGTH_SHORT
                ).show()
            }
            binding.sleepGoalHours.text = getString(R.string.goal_hours_schedule, goal)
        }
    }

    private fun minusGoalHours() {
        if(binding.sleepGoalHours.text.toString()!=""){
            var goal = binding.sleepGoalHours.text.toString().toInt()
            goal--
            if (goal < 6) {
                goal++
                Toast.makeText(
                    applicationContext,
                    "Sleep Duration should be more than 6 hours.",
                    LENGTH_SHORT
                ).show()
            }
            binding.sleepGoalHours.text = getString(R.string.goal_hours_schedule, goal)
        }
    }

    private fun clickSleep(choice: String) {
        val cal = Calendar.getInstance()

        Toast.makeText(
            applicationContext,
            "Please choose your Sleep Time first then Wake Up Time.",
            LENGTH_SHORT
        ).show()

        var sleepHours: Int
        var sleepMins: Int
        var sleepAmPm: String
        var bedTimeString: String

        val timeSetListener1 =
            TimePickerDialog.OnTimeSetListener { _: TimePicker, hour: Int, minute: Int ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                sleepHours = hour
                sleepMins = minute
                sleepAmPm = if (sleepHours < 12) {
                    "A.M."
                } else {
                    "P.M."
                }
                sleepHour = sleepHours
                sleepMin = sleepMins

                bedTimeString = String.format("%02d:%02d %s", sleepHours, sleepMins, sleepAmPm)

                when (choice) {
                    "everyday" -> {
                        binding.everydayBedtimeField.text =
                            getString(R.string.everyday_bedTime_edit, bedTimeString)
                    }
                    "weekday" -> {
                        binding.weekdayBedtimeField.text =
                            getString(R.string.weekday_bedTime_edit, bedTimeString)
                    }
                    "weekend" -> {
                        binding.weekEndBedtimeField.text =
                            getString(R.string.weekend_bedTime_edit, bedTimeString)
                    }
                }
            }

        var wakeHour: Int
        var wakeMins: Int
        var wakeAmPm: String
        var wakeTimeString: String

        val timeSetListener2 =
            TimePickerDialog.OnTimeSetListener { _: TimePicker, hour: Int, minute: Int ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                wakeHour = hour
                wakeMins = minute
                wakeAmPm = if (wakeHour < 12) {
                    "A.M."
                } else {
                    "P.M."
                }
                wakeTimeString = String.format("%02d:%02d %s", wakeHour, wakeMins, wakeAmPm)
                when (choice) {
                    "everyday" -> {
                        binding.everydayWakeTimeField.text =
                            getString(R.string.everyday_wakeTime_edit, wakeTimeString)
                    }
                    "weekday" -> {
                        binding.weekdayWakeTimeField.text =
                            getString(R.string.weekday_wakeTime_edit, wakeTimeString)
                    }
                    "weekend" -> {
                        binding.weekEndWakeTimeField.text =
                            getString(R.string.weekend_wakeTime_edit, wakeTimeString)
                    }
                }

            }

        TimePickerDialog(
            this,
            timeSetListener2,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()
        TimePickerDialog(
            this,
            timeSetListener1,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()
    }

    private fun saveData() {
        var sleepGoal = binding.sleepGoalHours.text
        var everydaySwitch = binding.everydaySwitch.isChecked
        var weekdaySwitch = binding.weekdaySwitch.isChecked
        var weekEndSwitch = binding.weekendSwitch.isChecked
        var everydayBed = binding.everydayBedtimeField.text
        var everydayWake = binding.everydayWakeTimeField.text
        var weekdayBed = binding.weekdayBedtimeField.text
        var weekdayWake = binding.weekdayWakeTimeField.text
        var weekEndBed = binding.weekEndBedtimeField.text
        var weekEndWake = binding.weekEndWakeTimeField.text

        binding.sleepGoalHours.text = sleepGoal
        binding.everydaySwitch.isChecked = everydaySwitch
        binding.weekdaySwitch.isChecked = weekdaySwitch
        binding.weekendSwitch.isChecked = weekEndSwitch
        binding.everydayBedtimeField.text = everydayBed
        binding.everydayWakeTimeField.text = everydayWake
        binding.weekdayBedtimeField.text = weekdayBed
        binding.weekdayWakeTimeField.text = weekdayWake
        binding.weekEndBedtimeField.text = weekEndBed
        binding.weekEndWakeTimeField.text = weekEndWake

        val sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("SLEEP_GOAL_SCHEDULE", sleepGoal.toString())
            putString("EVERYDAY_SWITCH", everydaySwitch.toString())
            putString("WEEKDAY_SWITCH", weekdaySwitch.toString())
            putString("WEEKEND_SWITCH", weekEndSwitch.toString())
            putString("EVERYDAY_BED", everydayBed.toString())
            putString("EVERYDAY_WAKE", everydayWake.toString())
            putString("WEEKDAY_BED", weekdayBed.toString())
            putString("WEEKDAY_WAKE", weekdayWake.toString())
            putString("WEEKEND_BED", weekEndBed.toString())
            putString("WEEKEND_WAKE", weekEndWake.toString())
        }.apply()
    }

    private fun loadData() {
        val sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE)

        val sleepGoal = sharedPreferences.getString("SLEEP_GOAL_SCHEDULE", null)
        val everydaySwitch = sharedPreferences.getString("EVERYDAY_SWITCH", null).toBoolean()
        val weekdaySwitch = sharedPreferences.getString("WEEKDAY_SWITCH", null).toBoolean()
        val weekendSwitch = sharedPreferences.getString("WEEKEND_SWITCH", null).toBoolean()
        val everydayBed = sharedPreferences.getString("EVERYDAY_BED", null)
        val everydayWake = sharedPreferences.getString("EVERYDAY_WAKE", null)
        val weekdayBed = sharedPreferences.getString("WEEKDAY_BED", null)
        val weekdayWake = sharedPreferences.getString("WEEKDAY_WAKE", null)
        val weekendBed = sharedPreferences.getString("WEEKEND_BED", null)
        val weekendWake = sharedPreferences.getString("WEEKEND_WAKE", null)

        binding.sleepGoalHours.text = sleepGoal
        binding.everydaySwitch.isChecked = everydaySwitch
        binding.weekdaySwitch.isChecked = weekdaySwitch
        binding.weekendSwitch.isChecked = weekendSwitch
        binding.everydayBedtimeField.text = everydayBed
        binding.everydayWakeTimeField.text = everydayWake
        binding.weekdayBedtimeField.text = weekdayBed
        binding.weekdayWakeTimeField.text = weekdayWake
        binding.weekEndBedtimeField.text = weekendBed
        binding.weekEndWakeTimeField.text = weekendWake
    }

    override fun onStop() {
        super.onStop()
        saveData()
    }

    override fun onSupportNavigateUp(): Boolean {
        saveData()
        val intent = Intent(this, SleepTracker::class.java)
        var scheduleChosen = ""
        var bedTime = ""
        var wakeUpTime = ""
        val sleepGoal = binding.sleepGoalHours.text.toString()

        when {
            binding.everydaySwitch.isChecked -> {
                scheduleChosen = "Everyday"
                bedTime = binding.everydayBedtimeField.text.toString()
                wakeUpTime = binding.everydayWakeTimeField.text.toString()
            }
            binding.weekdaySwitch.isChecked -> {
                scheduleChosen = "Weekday"
                bedTime = binding.weekdayBedtimeField.text.toString()
                wakeUpTime = binding.weekdayWakeTimeField.text.toString()
            }
            binding.weekendSwitch.isChecked -> {
                scheduleChosen = "Weekend"
                bedTime = binding.weekEndBedtimeField.text.toString()
                wakeUpTime = binding.weekEndWakeTimeField.text.toString()
            }
        }

        intent.putExtra("scheduleChosen", scheduleChosen)
        intent.putExtra("bedTime", bedTime)
        intent.putExtra("wakeUpTime", wakeUpTime)
        intent.putExtra("sleepGoal", sleepGoal)
        intent.putExtra("sleepHour", sleepHour.toString())
        intent.putExtra("sleepMin", sleepMin.toString())

        startActivity(intent)
        onBackPressed()
        return true
    }
}