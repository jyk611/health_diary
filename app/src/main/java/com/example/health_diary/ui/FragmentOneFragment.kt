package com.example.health_diary.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.health_diary.HomeActivity
import com.example.health_diary.R
import com.example.health_diary.UserInfo
import com.example.health_diary.databinding.FragmentOneBinding
import com.google.firebase.firestore.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FragmentOneFragment : Fragment(R.layout.fragment_one) {

    private lateinit var binding: FragmentOneBinding
    private lateinit var db: FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentOneBinding.bind(view)

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

                val preferences: SharedPreferences = requireContext().getSharedPreferences("PREFERENCE",
                    AppCompatActivity.MODE_PRIVATE
                )
                val firstTime: String? = preferences.getString("FirstTimeInstall", "")

                if (firstTime == "Yes" && userData != 0) {
                    val intent = Intent(requireContext(), HomeActivity::class.java);
                    startActivity(intent)
                }

                val editor: SharedPreferences.Editor = preferences.edit()
                editor.putString("FirstTimeInstall", "Yes")
                editor.apply()

//                binding.startButton.setOnClickListener { loginPg2() }
            } })
    }

}