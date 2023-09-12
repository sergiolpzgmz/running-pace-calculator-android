package com.sergiolopez.runningpacecalculator.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.sergiolopez.runningpacecalculator.view.PaceCalculatorActivity.Companion.KEY_HOURS
import com.sergiolopez.runningpacecalculator.view.PaceCalculatorActivity.Companion.KEY_MINUTES
import com.sergiolopez.runningpacecalculator.view.PaceCalculatorActivity.Companion.KEY_RESULT
import com.sergiolopez.runningpacecalculator.view.PaceCalculatorActivity.Companion.KEY_SECONDS
import com.sergiolopez.runningpacecalculator.databinding.ActivityResultPaceCalculatorBinding
import com.sergiolopez.runningpacecalculator.util.TimeUtils
import com.sergiolopez.runningpacecalculator.view.PaceCalculatorActivity.Companion.KEY_DISTANCE
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ResultPaceCalculatorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultPaceCalculatorBinding

    private lateinit var tvResultPace: TextView
    private lateinit var tvResultText: TextView
    private lateinit var tvResultPaceUnits: TextView
    private lateinit var btnRecalculate: Button
    private lateinit var btnSplits: Button

    private var resultPace: Float = 0.0f
    private var distanceRun: Int = 0
    private var hours: String = "00"
    private var minutes: String = "00"
    private var seconds: String = "00"

    companion object {
        const val KEY_RESULT_PACE = "PACE_RESULT"
        const val KEY_RUN_DISTANCE = "RUN_DISTANCE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultPaceCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        extractIntent()
        initComponents()
        initUI(resultPace, distanceRun)
        initListeners()

    }

    // Get the values provided by intent
    private fun extractIntent() {
        resultPace = intent.extras?.getFloat(KEY_RESULT)!!
        distanceRun = intent.extras?.getInt(KEY_DISTANCE)!!
        hours = intent.extras?.getString(KEY_HOURS).toString()
        minutes = intent.extras?.getString(KEY_MINUTES).toString()
        seconds = intent.extras?.getString(KEY_SECONDS).toString()
    }

    // Initialises the gui components
    private fun initComponents() {
        tvResultPace = binding.tvResultPace
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
     * Initializes the User Interface in the new activity with calculated result and distance values.
     *
     * This function populates the GUI elements with relevant data, including the calculated run pace,
     * distance selected, and the target time to complete the specified distance. It formats and displays
     * these values in the appropriate GUI components.
     *
     * @param result The calculated result of the run pace in time per kilometer (time/km).
     * @param distance The distance selected by the user.
     */
    private fun initUI(result: Float, distance: Int) {
        val timeResultString = TimeUtils.formatHoursToTimeString(result)
        tvResultPace.text = timeResultString

        val resultUnits: String =
            if (timeResultString <= "00:00:60") " seconds/Km" else if (timeResultString <= "01:00:00") " minutes/Km"
            else " hours/Km"
        tvResultPaceUnits.text = resultUnits

        val time = TimeUtils.parseStringToTime(hours, minutes, seconds)
        val resultText = "Target pace to complete $distance km in\n$time"
        tvResultText.text = resultText
    }

    // Navigates to the SplitsViewActivity with the calculated result pace.
    private fun navigateToSplitActivity() {
        val intent = Intent(this, SplitsViewActivity::class.java)
        startActivity(intent.putExtras(bundle()))
    }

    /**
     * Encapsulates data into a bundle for use in the SplitsViewActivity.
     *
     * This function creates a `Bundle` containing the calculated result pace and the selected
     * run distance to be used in the SplitsViewActivity.
     *
     * @return An encapsulated bundle containing the relevant data.
     */
    private fun bundle(): Bundle {
        val bundle = Bundle()
        bundle.putFloat(KEY_RESULT_PACE, resultPace)
        bundle.putInt(KEY_RUN_DISTANCE, distanceRun)

        return bundle
    }
}