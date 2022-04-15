package com.example.health_diary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.health_diary.databinding.FragmentHealthCategoryBinding
import com.example.health_diary.ui.FoodPlanFragment
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import java.util.*

class HealthCategoryFragment : Fragment(R.layout.fragment_health_category) {
    //private lateinit var binding: FragmentHealthCategoryBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.title = "Health Categories"
        val binding = FragmentHealthCategoryBinding.inflate(layoutInflater)

        var timeList = ArrayList<TimeList>()

        var db: FirebaseFirestore = FirebaseFirestore.getInstance()

        //LOAD DATA
        db.collection("Time").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.e("Firestore error", error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        val time = dc.document.toObject(TimeList::class.java)
                        timeList.add(time)
                    }
                }
                val bedTime = timeList[0].sleepTime
                val wakeTime = timeList[0].wakeTime

                if(bedTime == "-" || wakeTime =="-"){
                    binding.bedTimeEdit.text = getString(R.string.bedTime_edit_healthCat,"Please choose schedule first.")
                    binding.wakeTimeEdit.text = getString(R.string.wakeTime_edit_healthCat,"Please choose schedule first.")
                    binding.alarmStatus.text = getString(R.string.alarmStatus_off)
                }
                else{
                    binding.bedTimeEdit.text = getString(R.string.bedTime_edit_healthCat,bedTime)
                    binding.wakeTimeEdit.text = getString(R.string.wakeTime_edit_healthCat,wakeTime)
                    binding.alarmStatus.text = getString(R.string.alarmStatus_on)
                }
            }
        })

        binding.forwardSleepTrackerButton.setOnClickListener{
            val intent = Intent(context, SleepTracker::class.java)
            startActivity(intent)
        }

        binding.forwardFoodPlanButton.setOnClickListener{
            val intent = Intent(context, FoodPlanFragment::class.java)
            startActivity(intent)
        }

        return binding.root



    }


}