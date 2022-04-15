package com.example.health_diary

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.example.health_diary.databinding.Login2Binding

class LoginPg2 : AppCompatActivity() {

    private lateinit var binding: Login2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Login2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextButton.setOnClickListener{
            loginPg3()
        }
    }

    private fun loginPg3(){
        val nameValue = binding.usernameInput.text.toString()
        val ageValue = binding.ageInput.text.toString()
        val weightValue = binding.weightInput.text.toString()
        val heightValue = binding.heightInput.text.toString()
        val genderChoice = binding.radioGroup.checkedRadioButtonId

        var gender=
            if (genderChoice == R.id.selectMale_button){
                "Male"
            } else {
                "Female"
            }

        if(nameValue == "" || ageValue == "" ){
            Toast.makeText(this, "Please enter your username and age!", LENGTH_SHORT).show()
            return
        }

        if(weightValue == "" || heightValue == "" ) {
            Toast.makeText(this, "Please enter your height and weight!", LENGTH_SHORT).show()
            return
        }

        //transfer user data to next page
        val intent = Intent(this,LoginPg3::class.java)
        intent.putExtra("username",nameValue)
        intent.putExtra("userAge",ageValue)
        intent.putExtra("userGender",gender)
        intent.putExtra("userWeight",weightValue)
        intent.putExtra("userHeight",heightValue)
        startActivity(intent)
    }
}