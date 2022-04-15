package com.example.health_diary

import android.R
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.health_diary.databinding.SleepSummaryBinding
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import java.util.*
import kotlin.collections.ArrayList


class SleepSummaryActivity: AppCompatActivity() {

    private lateinit var binding: SleepSummaryBinding
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SleepSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = "Sleep Summary"

        actionBar.setDisplayHomeAsUpEnabled(true)

        loadData(7)
        binding.chooseWeekSummaryButton.setBackgroundColor(Color.parseColor("#FFDBE4E0"))
        //LOAD SLEEP DATA
        binding.chooseWeekSummaryButton.setOnClickListener {
            changeToWeek()
            binding.chooseWeekSummaryButton.setBackgroundColor(Color.parseColor("#FFDBE4E0"))
            binding.chooseMonthSummaryButton.setBackgroundColor(Color.parseColor("#FFFFFF"))
        }

        binding.chooseMonthSummaryButton.setOnClickListener {
            changeToMonth()
            binding.chooseMonthSummaryButton.setBackgroundColor(Color.parseColor("#FFDBE4E0"))
            binding.chooseWeekSummaryButton.setBackgroundColor(Color.parseColor("#FFFFFF"))

        }

        binding.sleepScore.text = "-"
        binding.sleepDuration.text = "-"
        binding.sleepAtTime.text = "-"
        binding.wakeAtTime.text = "-"
    }


    private fun changeToWeek(){
        loadData(7)
    }

    private fun changeToMonth(){
        loadData(30)
    }

    private fun loadData(required : Int){

        var sleepList = ArrayList<SleepDB>()
        var totalSleepHour = 0
        var totalSleepMinutes = 0
        var totalWakeHour = 0
        var totalWakeMinutes = 0
        var sleepScore = 0
        var totalSleepDuration = 0
        var totalDurationHour = 0
        var totalDurationMinutes=0
        var totalDurationSeconds =0


        db= FirebaseFirestore.getInstance()
        db.collection("Sleep").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) { Log.e("Firestore error", error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        val sleepData = dc.document.toObject(SleepDB::class.java)
                        sleepList.add(sleepData)
                    }
                }

                val value = sleepList.size
                var requiredValue = required
                if(required > value){
                    requiredValue = value
                }
                val listItems = arrayOfNulls<String>(requiredValue)

                for (i in 0 until requiredValue) {
                    val sleep = sleepList[ i ]
                    listItems[i] = String.format("%s    %s",sleep.date,sleep.duration)
                    val durationString = sleep.duration
                    val durations  = durationString!!.split(":")
                    totalDurationHour+=durations[0].toInt()
                    totalDurationMinutes+=durations[1].toInt()
                    totalDurationSeconds+=durations[2].toInt()
                    val wakeUp = sleep.wakeTime
                    val wakeUpSplit = wakeUp!!.split(" ")
                    val wakeUpTime = wakeUpSplit[0]!!.split(":")
                    totalWakeHour += wakeUpTime[0].toInt()
                    totalWakeMinutes += wakeUpTime[1].toInt()
                    val sleeps = sleep.bedTime
                    val sleepSplit = sleeps!!.split(" ")
                    val sleepTime = sleepSplit[0]!!.split(":")
                    totalSleepHour += sleepTime[0].toInt()
                    totalSleepMinutes += sleepTime[1].toInt()
                }
                listItems.sort()
                listItems.reverse()
                val adapter = ArrayAdapter(applicationContext, R.layout.simple_list_item_1, listItems)
                binding.listView.adapter = adapter

                totalDurationHour *= 60*60*1000
                totalDurationMinutes *= 60*1000
                totalDurationSeconds *= 1000

                totalSleepDuration = totalDurationHour+totalDurationMinutes+totalDurationSeconds

                    totalSleepDuration/=requiredValue
                     totalSleepHour /= requiredValue
                     totalSleepMinutes /= requiredValue
                     totalWakeHour /= requiredValue
                     totalWakeMinutes /= requiredValue

                var finalDurationHours = totalSleepDuration/(60*60*1000)
                var finalDurationMinutes = totalSleepDuration/(60*1000)
                var finalDurationSeconds = totalSleepDuration/(1000)

                if(finalDurationHours == 8){
                    binding.sleepScore.text = "Excellent"
                }

                else if (finalDurationHours in 6..7 || finalDurationHours in 9..10 ){
                    binding.sleepScore.text = "Good"
                }

                else if (finalDurationHours in 4..5 || finalDurationHours in 11..12 ){
                    binding.sleepScore.text = "Average"
                }

                else{
                    binding.sleepScore.text = "Bad"
                }

                if(finalDurationMinutes > 60){
                    val minutes = finalDurationMinutes%60
                    finalDurationMinutes = minutes
                }

                if(finalDurationSeconds > 60){
                    val seconds = finalDurationSeconds%60
                    finalDurationSeconds = seconds
                }

                if(totalSleepMinutes > 60){
                    totalSleepHour++
                }
                if(totalWakeMinutes > 60){
                    totalWakeHour++
                }

                var wakeAmPm = ""
                var sleepAmPm = ""
                sleepAmPm = if(totalSleepHour > 12){
                    "P.M."
                } else{
                    "A.M."
                }

                wakeAmPm = if(totalWakeHour > 12){
                    "P.M."
                } else{
                    "A.M."
                }

                val averageDuration = String.format("%d Hr %d Min %d Sec",finalDurationHours,finalDurationMinutes,finalDurationSeconds)
                val averageSleep = String.format("%02d:%02d %s",totalSleepHour,totalSleepMinutes,sleepAmPm)
                val averageWake = String.format("%02d:%02d %s.",totalWakeHour,totalWakeMinutes,wakeAmPm)
                binding.sleepDuration.text= averageDuration
                binding.sleepAtTime.text=averageSleep
                binding.wakeAtTime.text=averageWake


            }})
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}