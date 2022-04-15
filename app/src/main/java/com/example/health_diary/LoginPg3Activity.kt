package com.example.health_diary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.health_diary.databinding.Login3Binding
import com.google.firebase.firestore.*

class LoginPg3 : AppCompatActivity() {
    private lateinit var binding: Login3Binding
    private lateinit var db: FirebaseFirestore
    private var selectedList = ArrayList<UserInfo>()
    private var size = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Login3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("username").toString()
        val age = intent.getStringExtra("userAge").toString()
        val gender = intent.getStringExtra("userGender").toString()
        val weight = intent.getStringExtra("userWeight").toString()
        val height = intent.getStringExtra("userHeight").toString()

        db = FirebaseFirestore.getInstance()
        db.collection("User").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.e("Firestore error", error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        val user = dc.document.toObject(UserInfo::class.java)
                        selectedList.add(user)
                    }
                }
                size = selectedList.size
            }
        })

        if (gender == "Female") {
            binding.userIcon.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable._72622_user_female_icon))
        } else {
            binding.userIcon.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.male_icon))
        }

        val ageValue = age.toInt()
        val weightValue = weight.toDouble()
        val heightValue = height.toDouble()
        val bmi = weightValue / (heightValue * heightValue)
        val bmiValue = bmi.toString().toDouble()

        binding.username.text = getString(R.string.username_text, "$name")
        binding.userAge.text = getString(R.string.user_age, ageValue)
        binding.userGender.text = getString(R.string.user_gender, "$gender")
        binding.userWeight.text = getString(R.string.user_weight, weightValue)
        binding.userHeight.text = getString(R.string.user_height, heightValue)
        binding.userBmi.text = getString(R.string.user_bmi, bmiValue)

        binding.backButton.setOnClickListener { loginPage2() }

        binding.finishButton.setOnClickListener {
            loadData(name, ageValue, gender, weightValue, heightValue, bmiValue)
            homePage(name)
        }
    }

    private fun loginPage2() {
        val intent = Intent(this, LoginPg2::class.java)
        startActivity(intent)
    }

    private fun homePage(name: String) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("username", name)
        startActivity(intent)
    }

    private fun loadData(name: String, age: Int, gender: String, weight: Double, height: Double, bmi: Double) {
        db = FirebaseFirestore.getInstance()
        var i =0
        if (size != 0) {
            if(size ==1 ){
                val savedUsername = selectedList[0].username.toString()
                db.collection("User").document(savedUsername).delete()
                    .addOnSuccessListener {
                        Toast.makeText(applicationContext, "Previous Data Deleted Successfully!", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(applicationContext, "Failed to Delete previous Data!", Toast.LENGTH_LONG).show()
                    }
            }
            else{
                do{
                    val savedUsername = selectedList[i].username.toString()
                    db.collection("User").document(savedUsername).delete()
                        .addOnSuccessListener {
                            Toast.makeText(applicationContext, "Previous Data Deleted Successfully!", Toast.LENGTH_LONG).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(applicationContext, "Failed to Delete previous Data!", Toast.LENGTH_LONG).show()
                        }
                    i++
                }while(size != 0)
            }
        }

            val userInfo: MutableMap<String, Any> = HashMap()
            userInfo["username"] = name
            userInfo["age"] = age
            userInfo["bmi"] = bmi
            userInfo["gender"] = gender
            userInfo["height"] = height
            userInfo["weight"] = weight

            db.collection("User").document("$name").set(userInfo)
                .addOnSuccessListener {
                    Toast.makeText(
                        applicationContext,
                        "Your user information saved Successfully!",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        applicationContext,
                        "Your user information failed to save!",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

        }
    }