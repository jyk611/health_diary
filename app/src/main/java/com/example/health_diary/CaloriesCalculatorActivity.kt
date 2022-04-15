package com.example.health_diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.health_diary.databinding.ActivityCaloriesCalculatorBinding

class CaloriesCalculatorActivity : AppCompatActivity() {

    lateinit var binding: ActivityCaloriesCalculatorBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCaloriesCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar
        actionBar!!.title = "Calories Intake Calculator"
        actionBar.setDisplayHomeAsUpEnabled(true)

        var calActivitySpinner = binding.inputCaloriesActivity
        val calActivityOption = arrayOf("No exercise", "1-3 days per week", "3-5 days per week", "Everyday exercise")


        calActivitySpinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, calActivityOption)
        binding.caloriesCalcButton.setOnClickListener {
            calTrackerfinalResult()
        }



    }

    private fun calTrackerfinalResult() {
        /**Get input from user**/
        var calActivitySpinner = binding.inputCaloriesActivity

        val calActivityGender = binding.genderOption.setOnCheckedChangeListener { radioGroup, i -> 0 }
        val calActivityAge = binding.inputCaloriesAge.text
        val calActivityHeight = binding.inputCaloriesHeight.text
        val calActivityWeight = binding.inputCaloriesWeight.text

        var activityBMRresult = 0
        var activityAMRresult = 0
        var calActivityFinalResult = binding.estimateCalResult

        /**Radio button**/
        val activityAge = calActivityAge.toString().toInt()
        val activityHeight = calActivityHeight.toString().toInt()
        val activityWeight = calActivityWeight.toString().toInt()
        val calGenderOption = binding.genderOption.checkedRadioButtonId

        var calTrackerGender=
            if (calGenderOption == R.id.calories_calc_male) {
                activityBMRresult = ((10 * activityWeight) + (6.25 * activityHeight) - (5 * activityAge) + 5).toInt()
            }

            else {
                activityBMRresult = ((10 * activityWeight) + (6.25 * activityHeight) - (5 * activityAge) - 161).toInt()
            }

        /**Spinner**/
        var activityOption = calActivitySpinner.selectedItem.toString()

        if(activityOption == "No exercise"){
            activityAMRresult = (activityBMRresult * 1.2).toInt()

        } else if(activityOption == "1-3 days per week"){
            activityAMRresult = (activityBMRresult * 1.375).toInt()

        } else if(activityOption == "3-5 days per week"){
            activityAMRresult = (activityBMRresult * 1.55).toInt()

        } else {
            activityAMRresult = (activityBMRresult * 1.9).toInt()
        }

        calActivityFinalResult.text = getString(R.string.estimate_cal_result, activityAMRresult.toString())

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
