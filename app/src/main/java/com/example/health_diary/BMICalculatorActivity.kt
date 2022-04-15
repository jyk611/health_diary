package com.example.health_diary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.health_diary.databinding.ActivityBmiCalculatorBinding

class BMICalculatorActivity : AppCompatActivity() {
        private lateinit var binding: ActivityBmiCalculatorBinding
        private var result = 0.0
        private var resultHealthy = ""
        private var color = 0

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityBmiCalculatorBinding.inflate(layoutInflater)
            setContentView(binding.root)
            val actionBar = supportActionBar
            actionBar!!.title = "BMI Calculator"
            actionBar.setDisplayHomeAsUpEnabled(true)

            binding.calculate.setOnClickListener{
                bmiResult()
                displayHealthy()
            }


        }

        private fun bmiResult(){
            val resultForHeight = binding.heightinCM.text.toString()
            val resultForWeight = binding.WeightinKG.text.toString()
            if(validateInput(resultForHeight, resultForWeight )) {
                val resultForHeights = resultForHeight.toString().toDouble() / 100
                val resultForWeights = resultForWeight.toString().toInt()
                result = resultForWeights / (resultForHeights * resultForHeights)
                val results = String.format("%.2f", result)
                binding.AnsForBMI.text = ("$results")
            }
        }

        private fun validateInput(resultForHeight:String?, resultForWeight:String?):Boolean{
            return when{
                resultForHeight.isNullOrEmpty() -> {
                    Toast.makeText(this,"Height Empty!!!",Toast.LENGTH_LONG).show()
                    return false
                }
                resultForWeight.isNullOrEmpty() -> {
                    Toast.makeText(this, "Weight Empty!!!", Toast.LENGTH_LONG).show()
                    return false
                }
                else ->{
                    return true
                }

            }

        }

        private fun displayHealthy(){
            when{
                result<18.5 ->{
                    resultHealthy = "Underweight"
                    color = R.color.underweight
                }
                result in 18.50..24.99 ->{
                    resultHealthy = "Normal weight"
                    color = R.color.normalWeight
                }
                result in 25.00..29.99 ->{
                    resultHealthy = "Overweight"
                    color = R.color.overweight
                }
                result in 30.00..34.90 ->{
                    resultHealthy = "Obesity Class I"
                    color = R.color.obesityClass_i
                }
                result in 35.00..39.99 ->{
                    resultHealthy = "Obesity Class II"
                    color = R.color.obesityClass_ii
                }
                result > 40.00 ->{
                    resultHealthy = "Obesity Class III"
                    color = R.color.obesityClass_iii
                }
            }
            binding.AnsForWeight.setTextColor(ContextCompat.getColor(this,color))
            binding.AnsForWeight.text = resultHealthy
        }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}