package com.example.health_diary

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.example.health_diary.databinding.Login1Binding
import com.google.firebase.firestore.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: Login1Binding
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Login1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()
        var selectedList = ArrayList<UserInfo>()

        db.collection("User").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(
                value: QuerySnapshot?,
                error: FirebaseFirestoreException?
            ) {
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

                val userData = selectedList.size

                val preferences: SharedPreferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                val firstTime: String? = preferences.getString("FirstTimeInstall", "")

                if (firstTime == "Yes" && userData != 0) {
                    val intent = Intent(applicationContext, HomeActivity::class.java);
                    startActivity(intent)
                }

                val editor: SharedPreferences.Editor = preferences.edit()
                editor.putString("FirstTimeInstall", "Yes")
                editor.apply()

                binding.startButton.setOnClickListener { loginPg2() }
            } })
    }
    private fun  loginPg2(){
        val intent = Intent(applicationContext, LoginPg2::class.java)
        startActivity(intent)
    }
}
