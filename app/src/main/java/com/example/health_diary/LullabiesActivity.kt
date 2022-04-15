package com.example.health_diary

import android.app.AlertDialog
import android.content.*
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.TypedValue
import android.view.View
import android.widget.*
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.example.health_diary.databinding.LullabiesBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.roundToInt


class Lullabies : AppCompatActivity() {

    private lateinit var binding: LullabiesBinding
    private lateinit var db: FirebaseFirestore
    private var timerStarted = false
    private lateinit var serviceIntent: Intent
    private var time = 0.0
    private var totalTime = 0

    var mediaPlayer : MediaPlayer? = null
    private val bedTimeFormat = SimpleDateFormat("h:mm a")
    private val bedTime: String = bedTimeFormat.format(Date())
    private val dateFormat = SimpleDateFormat("dd-MMMM-yyyy")
    private val date: String = dateFormat.format(Date())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LullabiesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = "Lullabies"
        actionBar.setDisplayHomeAsUpEnabled(true)

        //start timer count
        serviceIntent = Intent(applicationContext, TimerFunc::class.java)
        registerReceiver(updateTime, IntentFilter(TimerFunc.TIMER_UPDATED))
        serviceIntent.putExtra(TimerFunc.TIME_EXTRA, time)
        startService(serviceIntent)
        timerStarted = true

        val durationSpinner = findViewById<View>(R.id.playDuration_Spinner) as Spinner
        val durationAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.duration_array,
            android.R.layout.simple_spinner_item
        )
        durationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        durationSpinner.adapter = durationAdapter

//        binding.backSleepTrackerButton.setOnClickListener { confirmation() }

        binding.wakeUpButton.setOnClickListener { sleepTracker() }

        binding.rainButton.setOnClickListener { rainButton() }
        binding.windButton.setOnClickListener { windButton() }
        binding.natureButton.setOnClickListener { natureButton() }
        binding.pianoButton.setOnClickListener { pianoButton() }
        binding.allIsFoundButton.setOnClickListener { song1Button() }
        binding.lovelyButton.setOnClickListener { song2Button() }
        binding.withoutMeButton.setOnClickListener { song3Button() }
        binding.someoneLikeYouButton.setOnClickListener { song4Button() }

        binding.playButton.setOnClickListener{ playSong() }

        binding.pauseButton.setOnClickListener{ pauseSong() }

        binding.wakeUpButton.setOnClickListener { wakeUp() }
    }

    private fun sleepTracker(){
        val intent = Intent(this, SleepTracker::class.java)
        startActivity(intent)
    }

    private fun confirmation(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation")

        val input = TextView(this)
        input.text = " If you go back now, your sleep record will be discarded.Are you sure?"
        input.setTextColor(Color.parseColor("#000000"))
        input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
        builder.setView(input)

            builder.setPositiveButton("Confirm", DialogInterface.OnClickListener { dialog, which ->
                val intent = Intent(this, SleepTracker::class.java)
                startActivity(intent)
            })

        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
    }

    private fun rainButton(){
        resetAllButton()
        binding.rainButton.setBackgroundColor(Color.parseColor("#3443eb"))
        binding.rainButton.setTextColor(Color.parseColor("#FFFFFF"))
        binding.lullabiesSelectedName.text = getString(R.string.selectedSong_name, "Rain")
        pauseSong()
    }

    private fun windButton(){
        resetAllButton()
        binding.windButton.setBackgroundColor(Color.parseColor("#3443eb"))
        binding.windButton.setTextColor(Color.parseColor("#FFFFFF"))
        binding.lullabiesSelectedName.text=getString(R.string.selectedSong_name, "Wind")
        pauseSong()
    }

    private fun natureButton(){
        resetAllButton()
        binding.natureButton.setBackgroundColor(Color.parseColor("#3443eb"))
        binding.natureButton.setTextColor(Color.parseColor("#FFFFFF"))
        binding.lullabiesSelectedName.text=getString(R.string.selectedSong_name, "Nature")
        pauseSong()
    }

    private fun pianoButton(){
        resetAllButton()
        binding.pianoButton.setBackgroundColor(Color.parseColor("#3443eb"))
        binding.pianoButton.setTextColor(Color.parseColor("#FFFFFF"))
        binding.lullabiesSelectedName.text=getString(R.string.selectedSong_name, "Piano")
        pauseSong()
    }

    private fun song1Button(){
        resetAllButton()
        binding.allIsFoundButton.setBackgroundColor(Color.parseColor("#3443eb"))
        binding.allIsFoundButton.setTextColor(Color.parseColor("#FFFFFF"))
        binding.lullabiesSelectedName.text=getString(R.string.selectedSong_name, "All Is Found")
        pauseSong()
    }

    private fun song2Button(){
        resetAllButton()
        binding.lovelyButton.setBackgroundColor(Color.parseColor("#3443eb"))
        binding.lovelyButton.setTextColor(Color.parseColor("#FFFFFF"))
        binding.lullabiesSelectedName.text=getString(R.string.selectedSong_name, "Lovely")
        pauseSong()
    }

    private fun song3Button(){
        resetAllButton()
        binding.withoutMeButton.setBackgroundColor(Color.parseColor("#3443eb"))
        binding.withoutMeButton.setTextColor(Color.parseColor("#FFFFFF"))
        binding.lullabiesSelectedName.text=getString(R.string.selectedSong_name, "Without Me")
        pauseSong()
    }

    private fun song4Button(){
        resetAllButton()
        binding.someoneLikeYouButton.setBackgroundColor(Color.parseColor("#3443eb"))
        binding.someoneLikeYouButton.setTextColor(Color.parseColor("#FFFFFF"))
        binding.lullabiesSelectedName.text=getString(R.string.selectedSong_name, "Someone Like You")
        pauseSong()
    }

    override fun onStop() {
        super.onStop()
        stopService(serviceIntent)
        binding.playButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        binding.pauseButton.setImageResource(R.drawable.ic_baseline_pause_24)
        pauseSong()
    }

    private fun playSong(){

        val selectedLullabies = binding.lullabiesSelectedName.text.toString()
        if(mediaPlayer == null){
            when (selectedLullabies) {
                "Rain" -> {
                    mediaPlayer = MediaPlayer.create(this,R.raw.rain)
                }
                "Wind" -> {
                    mediaPlayer = MediaPlayer.create(this,R.raw.wind)
                }
                "Nature" -> {
                    mediaPlayer = MediaPlayer.create(this,R.raw.nature)
                }
                "Piano" -> {
                    mediaPlayer = MediaPlayer.create(this,R.raw.piano)
                }
                "All Is Found" -> {
                    mediaPlayer = MediaPlayer.create(this,R.raw.allisfound)
                }
                "Lovely" -> {
                    mediaPlayer = MediaPlayer.create(this,R.raw.lovely)
                }
                "Without Me" -> {
                    mediaPlayer = MediaPlayer.create(this,R.raw.without)
                }
                "Someone Like You" -> {
                    mediaPlayer = MediaPlayer.create(this,R.raw.someone)
                }
                else -> {
                    Toast.makeText(this,"No Lullabies Selected.",LENGTH_SHORT).show()
                    return
                }
            }
        }
        binding.playButton.setImageResource(R.drawable.clicked_play)
        binding.pauseButton.setImageResource(R.drawable.ic_baseline_pause_24)

        mediaPlayer!!.start()

        var duration = 0
        when {
            binding.playDurationSpinner.selectedItem.toString() == "30 Mins" -> {
                duration = 1000 * 60 * 30
            }
            binding.playDurationSpinner.selectedItem.toString() == "45 Mins" -> {
                duration = 1000 * 60 * 45
            }
            binding.playDurationSpinner.selectedItem.toString() == "1 Hour" -> {
                duration = 1000 * 60 * 60
            }
        }

        val durationTime = duration.toString().toLong()

        val timer: CountDownTimer = object : CountDownTimer(durationTime, durationTime) {
            override fun onTick(millisUntilFinished: Long) {
                // Nothing to do
            }

            override fun onFinish() {
                if (mediaPlayer!!.isPlaying) {
                    stopSong()
                }
            }
        }
        timer.start()
    }

    private fun pauseSong(){
        if(mediaPlayer != null){
            mediaPlayer!!.pause()
            binding.playButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            binding.pauseButton.setImageResource(R.drawable.clicked_pause)
        }
    }

    private fun stopSong(){
        if(mediaPlayer != null) {
            mediaPlayer!!.reset()
            mediaPlayer!!.release()
            mediaPlayer = null
            binding.lullabiesSelectedName.text =
                getString(R.string.selectedSong_name, "No Song Selected")
            binding.playButton.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            binding.pauseButton.setImageResource(R.drawable.ic_baseline_pause_24)
        }
    }

    private val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            time = intent.getDoubleExtra(TimerFunc.TIME_EXTRA, 0.0)
            binding.sleepDurationTime.text =
                getString(R.string.sleepTime_edit, getTimeStringFromDouble(time))
            totalTime = time.roundToInt()
        }
    }

    private fun getTimeStringFromDouble(time: Double): String {

        val resultInt = time.roundToInt()
        val hours = resultInt % 86400 / 3600
        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60
        totalTime = seconds
        return makeTimeString(hours, minutes, seconds)
    }

    private fun makeTimeString(hour: Int, min: Int, sec: Int): String = String.format("%02d:%02d:%02d", hour, min, sec)

    private fun wakeUp(){
        //get currentTime
        val wakeTimeFormat = SimpleDateFormat("h:mm a")
        val wakeTime: String = wakeTimeFormat.format(Date())


        //stop timer
        stopService(serviceIntent)
        timerStarted = false

        val wakeUpString = binding.sleepDurationTime.text.toString()
        Toast.makeText(applicationContext,"Your total sleep duration is $wakeUpString", LENGTH_SHORT).show()

        binding.sleepDurationTime.setText(R.string.sleepDuration_Text)
        Toast.makeText(applicationContext,"$wakeTime\n$bedTime\n$date\n$wakeUpString", LENGTH_SHORT).show()
        //add and save Sleep Records
        db = FirebaseFirestore.getInstance()
        val sleepRecord: MutableMap<String, Any> = HashMap()
        sleepRecord["date"] = date
        sleepRecord["bedTime"] = bedTime
        sleepRecord["wakeTime"] = wakeTime
        sleepRecord["duration"] = wakeUpString

        db.collection("Sleep").document("$date-$bedTime").set(sleepRecord)
            .addOnSuccessListener {
                Toast.makeText(applicationContext, "Your user information saved Successfully!", Toast.LENGTH_LONG)
                    .show()
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext, "Your user information failed to save!", Toast.LENGTH_LONG)
                    .show()
            }

        sleepTracker()
    }

    private fun resetAllButton(){
        binding.rainButton.setTextColor(Color.parseColor("#919191"))
        binding.windButton.setTextColor(Color.parseColor("#919191"))
        binding.natureButton.setTextColor(Color.parseColor("#919191"))
        binding.pianoButton.setTextColor(Color.parseColor("#919191"))
        binding.allIsFoundButton.setTextColor(Color.parseColor("#919191"))
        binding.lovelyButton.setTextColor(Color.parseColor("#919191"))
        binding.withoutMeButton.setTextColor(Color.parseColor("#919191"))
        binding.someoneLikeYouButton.setTextColor(Color.parseColor("#919191"))

        binding.rainButton.setBackgroundColor(Color.parseColor("#FFFFFF"))
        binding.windButton.setBackgroundColor(Color.parseColor("#FFFFFF"))
        binding.natureButton.setBackgroundColor(Color.parseColor("#FFFFFF"))
        binding.pianoButton.setBackgroundColor(Color.parseColor("#FFFFFF"))
        binding.allIsFoundButton.setBackgroundColor(Color.parseColor("#FFFFFF"))
        binding.lovelyButton.setBackgroundColor(Color.parseColor("#FFFFFF"))
        binding.withoutMeButton.setBackgroundColor(Color.parseColor("#FFFFFF"))
        binding.someoneLikeYouButton.setBackgroundColor(Color.parseColor("#FFFFFF"))

        stopSong()
    }

    override fun onSupportNavigateUp(): Boolean {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmation")

        val input = TextView(this)
        input.text = " If you go back now, your sleep record will be discarded.Are you sure?"
        input.setTextColor(Color.parseColor("#000000"))
        input.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20F)
        builder.setView(input)

        builder.setPositiveButton("Confirm", DialogInterface.OnClickListener { dialog, which ->
            val intent = Intent(this, SleepTracker::class.java)
            startActivity(intent)
        })

        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        builder.show()
        return true
    }
}
