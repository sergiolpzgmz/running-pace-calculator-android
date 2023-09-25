package com.sergiolopez.runningpacecalculator.view.result

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.sergiolopez.runningpacecalculator.R
import com.sergiolopez.runningpacecalculator.view.main.PaceCalculatorActivity.Companion.KEY_HOURS
import com.sergiolopez.runningpacecalculator.view.main.PaceCalculatorActivity.Companion.KEY_MINUTES
import com.sergiolopez.runningpacecalculator.view.main.PaceCalculatorActivity.Companion.KEY_SECONDS
import com.sergiolopez.runningpacecalculator.databinding.ActivityResultPaceCalculatorBinding
import com.sergiolopez.runningpacecalculator.util.TimeUtils
import com.sergiolopez.runningpacecalculator.view.splits.SplitsViewActivity
import com.sergiolopez.runningpacecalculator.view.main.PaceCalculatorActivity.Companion.KEY_DISTANCE
import com.sergiolopez.runningpacecalculator.view.main.PaceCalculatorViewModel
import com.sergiolopez.runningpacecalculator.view.splits.SplitsViewModel

class ResultPaceCalculatorActivity : AppCompatActivity() {
    private lateinit var resultViewModel: ResulPaceViewModel
    private lateinit var binding: ActivityResultPaceCalculatorBinding

    private var distanceRun: Int = 0
    private var hours: Double = 0.0
    private var minutes: Double = 0.0
    private var seconds: Double = 0.0

    companion object {
        const val KEY_RESULT_PACE = "PACE_RESULT"
        const val KEY_RUN_DISTANCE = "RUN_DISTANCE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultPaceCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resultViewModel = ViewModelProvider(this)[ResulPaceViewModel::class.java]

        extractIntent()
        initUI(distanceRun)
        initListeners()

    }


    // Get the values provided by intent
    private fun extractIntent() {
        distanceRun = intent.extras?.getInt(KEY_DISTANCE)!!
        hours = intent.extras?.getDouble(KEY_HOURS)!!
        minutes = intent.extras?.getDouble(KEY_MINUTES)!!
        seconds = intent.extras?.getDouble(KEY_SECONDS)!!
    }

    private fun initListeners() {
        binding.btnRecalculate.setOnClickListener { onBackPressed() }
        binding.btnSplits.setOnClickListener { navigateToSplitActivity() }
    }

    /**
     * Initializes the User Interface in the new activity with calculated result and distance values.
     *
     * This function populates the GUI elements with relevant data, including the calculated run pace,
     * distance selected, and the target time to complete the specified distance. It formats and displays
     * these values in the appropriate GUI components.
     *
     * @param distance The distance selected by the user.
     */
    private fun initUI(distance: Int) {
        calculatePaceResult()
        val timeResultString = TimeUtils.formatHoursToTimeString(resultViewModel.resultPace.value!!)
        binding.tvResultPace.text = timeResultString
        showResultStringOnScreen(timeResultString, distance)
    }


    /**
     * Displays the result string the screen with the calculated pace and time for a given distance.
     *
     * @param timeResultString A formatted time string in "HH:mm:ss" representing the time taken.
     * @param distance The distance to complete in kilometers.
     */
    private fun showResultStringOnScreen(timeResultString: String, distance: Int) {
        val stringHoursTxt: String = getString(R.string.hours)
        val stringMinutesTxt: String = getString(R.string.minutes)
        val stringSecondsTxt: String = getString(R.string.seconds)

        val resultUnits: String =
            if (timeResultString <= "00:00:60") " $stringSecondsTxt/Km" else if (timeResultString <= "01:00:00") " $stringMinutesTxt/Km"
            else " $stringHoursTxt/Km"
        binding.tvResultPeaceUnits.text = resultUnits

        val time =
            TimeUtils.parseStringToTime(hours.toString(), minutes.toString(), seconds.toString())
        val resultTxt: String = getString(R.string.result_txt)
        val resultTxtPreposition = getString(R.string.result_txt_preposition)

        binding.tvResultText.text = "$resultTxt $distance km $resultTxtPreposition\n$time"
    }

    // Navigates to the SplitsViewActivity with the calculated result pace.
    private fun navigateToSplitActivity() {
        val intent = Intent(this, SplitsViewActivity::class.java)
        startActivity(intent.putExtras(bundle()))
    }

    private fun calculatePaceResult() {
        val resultPace = resultViewModel.calculatePace(distanceRun, hours, minutes, seconds)
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
        bundle.putDouble(KEY_RESULT_PACE, resultViewModel.resultPace.value!!)
        bundle.putInt(KEY_RUN_DISTANCE, distanceRun)

        return bundle
    }
}