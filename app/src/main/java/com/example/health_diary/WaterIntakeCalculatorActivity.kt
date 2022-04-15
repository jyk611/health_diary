package com.example.health_diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.health_diary.databinding.ActivityWaterIntakeCalculatorBinding
import kotlin.math.roundToInt

class WaterIntakeCalculatorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWaterIntakeCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaterIntakeCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar = supportActionBar
        actionBar!!.title = "Water Intake Calculator"
        actionBar.setDisplayHomeAsUpEnabled(true)

        val waterIntakeActivitySpinnerOption = binding.waterIntakeActivityOption
        val waterIntakeClimateSpinnerOption = binding.waterIntakeClimateOption

        val waterIntakeActivityOption = arrayOf("Sedentary", "Light Activity", "Moderate", "High", "Extreme")
        val waterIntakeClimateOption = arrayOf("Cold", "Temperate", "Tropical")

        waterIntakeActivitySpinnerOption.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, waterIntakeActivityOption)
        waterIntakeClimateSpinnerOption.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, waterIntakeClimateOption)

        binding.calculateButton.setOnClickListener {
            calculateWaterRequired()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun calculateWaterRequired() {
        val waterIntakeActivitySpinnerOption = binding.waterIntakeActivityOption
        val waterIntakeClimateSpinnerOption = binding.waterIntakeClimateOption
        val weight = binding.waterCalculatorInputKilogram.text
        val inputWeight = weight.toString().toDoubleOrNull()
        val waterIntakeResult = binding.waterIntakeResult
        val waterIntakeResult2 = binding.waterIntakeResult2
        var weightResult = 0
        var cupResult = 0


        if (inputWeight == null) { Toast.makeText(applicationContext, "Please key in your weight", Toast.LENGTH_LONG).show()
            return
        }

        val ounce= inputWeight * 2.2047 * 0.67
        val activityOption = waterIntakeActivitySpinnerOption.selectedItem.toString()
        val climateOption = waterIntakeClimateSpinnerOption.selectedItem.toString()

        when (activityOption) {
            "Sedentary" -> {
                weightResult = when (climateOption){
                    "Cold" -> (ounce * 0.8 * 29.5735).roundToInt()
                    "Temperate" -> (ounce * 29.5735).roundToInt()
                    else -> (ounce * 1.3 * 29.5735).roundToInt()
                }
            }
            "Light Activity" -> {
                weightResult = when (climateOption) {
                    "Cold" -> ((ounce + 12) * 0.8 * 29.5735).roundToInt()
                    "Temperate" -> ((ounce + 12) * 29.5735).roundToInt()
                    else -> ((ounce + 12) * 1.3 * 29.5735).roundToInt()
                }
            }
            "Moderate" -> {
                weightResult = when (climateOption) {
                    "Cold" -> ((ounce + 20) * 0.8 * 29.5735).roundToInt()
                    "Temperate" -> ((ounce + 20) * 29.5735).roundToInt()
                    else -> ((ounce + 20) * 1.3 * 29.5735).roundToInt()
                }
            }
            "High" -> {
                weightResult = when (climateOption) {
                    "Cold" -> ((ounce + 28) * 0.8 * 29.5735).roundToInt()
                    "Temperate" -> ((ounce + 28) * 29.5735).roundToInt()
                    else -> ((ounce + 28) * 1.3 * 29.5735).roundToInt()
                }
            }
            else -> {
                weightResult = when (climateOption) {
                    "Cold" -> ((ounce + 34) * 0.8 * 29.5735).roundToInt()
                    "Temperate" -> ((ounce + 34) * 29.5735).roundToInt()
                    else -> ((ounce + 34) * 1.3 * 29.5735).roundToInt()
                }
            }
        }

        cupResult = (weightResult / 200)

        waterIntakeResult.text = getString(R.string.water_intake_result, weightResult.toString())
        waterIntakeResult2.text = getString(R.string.water_intake_result2, cupResult.toString())
    }




}