package com.sergiolopez.runningpacecalculator.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.sergiolopez.runningpacecalculator.view.PaceCalculatorActivity.Companion.KEY_DISTANCE
import com.sergiolopez.runningpacecalculator.view.PaceCalculatorActivity.Companion.KEY_HOURS
import com.sergiolopez.runningpacecalculator.view.PaceCalculatorActivity.Companion.KEY_MINUTES
import com.sergiolopez.runningpacecalculator.view.PaceCalculatorActivity.Companion.KEY_RESULT
import com.sergiolopez.runningpacecalculator.view.PaceCalculatorActivity.Companion.KEY_SECONDS
import com.sergiolopez.runningpacecalculator.databinding.ActivityResultPaceCalculatorBinding
import java.text.DecimalFormat

class ResultPaceCalculatorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultPaceCalculatorBinding

    private lateinit var tvResultPace: TextView
    private lateinit var tvResultText: TextView
    private lateinit var tvResultPaceUnits: TextView
    private lateinit var btnRecalculate: Button
    private lateinit var btnSplits: Button

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

    // Get the values provided by intent
    private fun extractIntent() {
        result = intent.extras?.getFloat(KEY_RESULT)!!
        distance = intent.extras?.getInt(KEY_DISTANCE)!!
        hours = intent.extras?.getString(KEY_HOURS).toString()
        minutes = intent.extras?.getString(KEY_MINUTES).toString()
        seconds = intent.extras?.getString(KEY_SECONDS).toString()
    }

    // Initialises the gui components
    private fun initComponents() {
        tvResultPace = binding.tvResultPeace
        tvResultText = binding.tvResultText
        tvResultPaceUnits = binding.tvResultPeaceUnits
        btnRecalculate = binding.btnRecalculate
        btnSplits = binding.btnSplits
    }

    private fun initListeners() {
        btnRecalculate.setOnClickListener { onBackPressed() }
        btnSplits.setOnClickListener { navigateToSplitActivity() }
    }

    /**
     * Displays the result in the new activity based on intent values
     *
     * @param result the calculation result of run pace
     * @param distance distance selected
     */
    private fun initUI(
        result: Float,
        distance: Int,
    ) {
        val decimalFormat = DecimalFormat("#.##")
        tvResultPace.text = decimalFormat.format(result)
        tvResultPaceUnits.text = " min/km"

        if (hours == "") hours = "00"
        if (minutes == "") minutes = "00"
        if (seconds == "") seconds = "00"

        val resultText = "Target pace to complete $distance km in\n$hours h $minutes' $seconds''"
        tvResultText.text = resultText

    }

    private fun navigateToSplitActivity() {
        val intent = Intent(this, SplitsViewActivity::class.java)
        startActivity(intent)
    }
}