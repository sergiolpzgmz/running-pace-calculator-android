package com.sergiolopez.runningpacecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.sergiolopez.runningpacecalculator.PaceCalculatorActivity.Companion.KEY_DISTANCE
import com.sergiolopez.runningpacecalculator.PaceCalculatorActivity.Companion.KEY_HOURS
import com.sergiolopez.runningpacecalculator.PaceCalculatorActivity.Companion.KEY_MINUTES
import com.sergiolopez.runningpacecalculator.PaceCalculatorActivity.Companion.KEY_RESULT
import com.sergiolopez.runningpacecalculator.PaceCalculatorActivity.Companion.KEY_SECONDS
import com.sergiolopez.runningpacecalculator.databinding.ActivityResultPaceCalculatorBinding
import java.text.DecimalFormat

class ResultPaceCalculatorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultPaceCalculatorBinding


    private lateinit var tvResultPace: TextView
    private lateinit var tvResultText: TextView
    private lateinit var tvResultPaceUnits: TextView
    private lateinit var btnRecalculate: Button

    private var result: Float = 0.0f
    private var distance: Int = 0
    private lateinit var hours: String
    private lateinit var minutes: String
    private lateinit var seconds: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultPaceCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        extractIntent()
        initComponents()
        initListeners()
        initUI(result, distance)
    }

        private fun extractIntent() {
            val bundle = intent.extras
            result = intent.extras?.getFloat(KEY_RESULT)!!
            distance = intent.extras?.getInt(KEY_DISTANCE)!!
            hours = intent.extras?.getString(KEY_HOURS).toString()
            minutes = intent.extras?.getString(KEY_MINUTES).toString()
            seconds = intent.extras?.getString(KEY_SECONDS).toString()

            Log.d("sergio", minutes)

        }

        private fun initComponents() {
            tvResultPace = binding.tvResultPeace
            tvResultText = binding.tvResultText
            btnRecalculate = binding.btnRecalculate
            tvResultPaceUnits = binding.tvResultPeaceUnits
        }

        private fun initListeners() {
            btnRecalculate.setOnClickListener { onBackPressed() }
        }

        private fun initUI(
            result: Float,
            distance: Int,
        ) {
            Log.d("sergio",result.toString())
            val decimalFormat = DecimalFormat("#.##")
            tvResultPace.text = decimalFormat.format(result)
            tvResultPaceUnits.text = " min/km"

            if (hours == "") hours = "00"
            if (minutes == "") minutes = "00"
            if (seconds == "") seconds = "00"

            val resultText = "Target pace to complete $distance km in\n$hours h $minutes' $seconds''"
            tvResultText.text = resultText

        }
}