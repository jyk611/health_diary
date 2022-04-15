package com.example.health_diary

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.health_diary.databinding.FragmentSettingBinding
import com.google.firebase.firestore.*


class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "Setting"
        val binding = FragmentSettingBinding.inflate(layoutInflater)
        var selectedList = ArrayList<UserInfo>()

        var db: FirebaseFirestore = FirebaseFirestore.getInstance()

        //LOAD DATA
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
                binding.usernameSetting.text = getString(R.string.username_text, selectedList[0].username)
                binding.userAge.text = getString(R.string.user_age, selectedList[0].age)
                binding.userGender.text = getString(R.string.user_gender, selectedList[0].gender)
                binding.userWeight.text = getString(R.string.user_weight, selectedList[0].weight)
                binding.userHeight.text = getString(R.string.user_height, selectedList[0].height)
                binding.userBMI.text = getString(R.string.user_bmi, selectedList[0].bmi)

                val gender = selectedList[0].gender

                if (gender == "Female") {
                    binding.userIconSetting.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable._72622_user_female_icon
                        )
                    )
                } else {
                    binding.userIconSetting.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.male_icon
                        )
                    )
                }

                val bmi = selectedList[0].bmi

                if (bmi != null) {
                    when {
                        bmi >= 30 -> {
                            binding.userHealthSentence.text = getString(
                                R.string.bmi_sentence,
                                "You are Obese!You are recommend to have a good eating habits and engage in moderate physical activity in order to lose weight!"
                            )
                        }
                        bmi < 16.5 -> {
                            binding.userHealthSentence.text = getString(
                                R.string.bmi_sentence,
                                "Your BMI is under an underweight BMI range ! You are recommend to gain more weight to increase your metabolism.  "
                            )
                        }
                        bmi in 25.0..29.9 -> {
                            binding.userHealthSentence.text = getString(
                                R.string.bmi_sentence,
                                "You are slightly overweight. You are recommend to engage in moderate physical activity in order to lose weight!"
                            )
                        }
                        bmi in 16.5..18.5 -> {
                            binding.userHealthSentence.text = getString(
                                R.string.bmi_sentence,
                                "You are slightly underweight! You are recommend to gain more weight to increase your metabolism.  "
                            )
                        }
                        bmi in 18.5..24.9 -> {
                            binding.userHealthSentence.text = getString(
                                R.string.bmi_sentence,
                                "Congrats! Your BMI is under a healthy BMI range ! Keep it up\n" +
                                        "to maintain a healthy weight and getting low risk of developing serious health problems!"
                            )
                        }
                    }
                }
            }
        })

        //EDIT PROFILE
        binding.editProfileButton.setOnClickListener {
            val myDialogView = LayoutInflater.from(context).inflate(R.layout.edit_dialog, null)
            val builder = AlertDialog.Builder(context)
                .setView(myDialogView)
                .setTitle("Edit User Information : ")

                builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                    //old data
                    var oldName = selectedList[0].username.toString()
                    var oldGender = selectedList[0].gender.toString()
                    var oldAge = selectedList[0].age.toString().toInt()
                    var oldWeight = selectedList[0].weight.toString().toDouble()
                    var oldHeight = selectedList[0].height.toString().toDouble()
                    //old data

                    var newGender = ""
                    var newAge = 0
                    var newHeight = 0.0
                    var newWeight = 0.0
                    var newBmi = 0.0

                    val maleButton = myDialogView.findViewById<RadioButton>(R.id.male_button)
                    val femaleButton = myDialogView.findViewById<RadioButton>(R.id.female_button)

                    if(maleButton.isChecked){
                        newGender="Male"
                    }
                    else if(femaleButton.isChecked){
                        newGender="Female"
                    }

                    val age = myDialogView.findViewById<EditText>(R.id.edit_age_field).text.toString()
                    val height = myDialogView.findViewById<EditText>(R.id.edit_height_field).text.toString()
                    val weight = myDialogView.findViewById<EditText>(R.id.edit_weight_field).text.toString()

                    if (age == "") {
                        newAge = oldAge.toString().toInt()
                    }
                    if (height == "") {
                        newHeight = oldHeight.toString().toDouble()
                    }
                    if (weight == "") {
                        newHeight = oldWeight.toString().toDouble()
                    }

                    if (age != "" && height != "" && weight != "") {
                        newAge = age.toInt()
                        newHeight = height.toDouble()
                        newWeight = weight.toDouble()
                    }

                    newBmi = newWeight / (newHeight * newHeight)

                    db.collection("User").document(oldName).delete()
                        .addOnSuccessListener {
                            Toast.makeText(context, "Previous Data Deleted Successfully!", Toast.LENGTH_LONG).show()
                        }
                        .addOnFailureListener { Toast.makeText(context, "Failed to Delete previous Data!", Toast.LENGTH_LONG).show()
                        }

                    val newUserInfo: MutableMap<String, Any> = HashMap()
                    newUserInfo["username"] = oldName
                    newUserInfo["age"] = newAge
                    newUserInfo["bmi"] = newBmi
                    newUserInfo["gender"] = newGender
                    newUserInfo["height"] = newHeight
                    newUserInfo["weight"] = newWeight

                    db.collection("User").document(oldName).set(newUserInfo)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Your user information saved Successfully!", Toast.LENGTH_LONG).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(context, "Your user information failed to save!", Toast.LENGTH_LONG).show()
                        }
                    dialog.cancel()
                })

                builder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel() })
            builder.create().show()
        }

        //EDIT USERNAME
        binding.editUsernameButton.setOnClickListener {

            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("Edit Username:")

            val input = EditText(context)
            input.hint = "New user name"
            builder.setView(input)

            builder.setPositiveButton("Confirm", DialogInterface.OnClickListener
            { dialog, which ->
                val newUserName = input.text.toString()

                if (newUserName == "" || newUserName == "null") {
                    dialog.cancel()
                }

                else{
                    var oldName = selectedList[0].username.toString()
                    var oldGender = selectedList[0].gender.toString()
                    var oldAge = selectedList[0].age.toString().toInt()
                    var oldWeight = selectedList[0].weight.toString().toDouble()
                    var oldHeight = selectedList[0].height.toString().toDouble()
                    var oldBmi = selectedList[0].bmi.toString().toDouble()

                    if(newUserName != oldName){
                        db.collection("User").document(oldName).delete()
                            .addOnFailureListener { Toast.makeText(context, "Failed to Delete previous Data!", Toast.LENGTH_LONG).show() }

                        val userInfo: MutableMap<String, Any> = HashMap()
                        userInfo["username"] = newUserName
                        userInfo["age"] = oldAge
                        userInfo["bmi"] = oldBmi
                        userInfo["gender"] = oldGender
                        userInfo["height"] = oldHeight
                        userInfo["weight"] = oldWeight

                        db.collection("User").document(newUserName).set(userInfo)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Your user information updated Successfully!", Toast.LENGTH_LONG).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "Your user information failed to update", Toast.LENGTH_LONG).show()
                            }
                    }

                    else{
                        Toast.makeText(context, "Your new username is same as the old username.", Toast.LENGTH_LONG).show()
                    }

                }
            })
            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

            builder.show()
        }

        return binding.root
    }
}