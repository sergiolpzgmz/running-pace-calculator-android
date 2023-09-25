package com.sergiolopez.runningpacecalculator.view.splits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sergiolopez.runningpacecalculator.R
import com.sergiolopez.runningpacecalculator.databinding.ActivitySplitsViewBinding
import com.sergiolopez.runningpacecalculator.util.TimeUtils.Companion.formatHoursToTimeString
import com.sergiolopez.runningpacecalculator.view.splits.adapter.SplitsAdapter
import com.sergiolopez.runningpacecalculator.view.main.PaceCalculatorActivity
import com.sergiolopez.runningpacecalculator.view.main.PaceCalculatorViewModel
import com.sergiolopez.runningpacecalculator.view.splits.SplitsViewModel
import com.sergiolopez.runningpacecalculator.view.result.ResultPaceCalculatorActivity

class SplitsViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplitsViewBinding

    private lateinit var adapter: SplitsAdapter
    private lateinit var splitsViewModel: SplitsViewModel

    private var resultPace = 0.0
    private var distanceRun = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplitsViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splitsViewModel = ViewModelProvider(this)[SplitsViewModel::class.java]

        extractIntent()
        initRecyclerView()
        initListeners()
    }

    private fun extractIntent() {
        resultPace = intent.extras?.getDouble(ResultPaceCalculatorActivity.KEY_RESULT_PACE)!!
        distanceRun = intent.extras?.getInt(ResultPaceCalculatorActivity.KEY_RUN_DISTANCE)!!
    }

    private fun initRecyclerView() {
        val recyclerView = binding.rvSplits
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SplitsAdapter(createSplitTimesList())
        recyclerView.adapter = adapter
    }


    /**
     * Creates a list of split times in user-readable format
     * The split times are calculated and observed using the [splitsViewModel].
     *
     * @return A mutable list of split times as strings.
     */
    private fun createSplitTimesList(): MutableList<String> {
        calculateSplitTimes()
        val splitTimesList = mutableListOf<String>()

        splitsViewModel.splitTimesList.observe(this) { result ->
            var distance = 1
            var measuringUnit: String

            result.forEach { r ->
                val time = formatHoursToTimeString(r)
                val stringHoursTxt: String = getString(R.string.hours)
                val stringMinutesTxt: String = getString(R.string.minutes)
                val stringSecondsTxt: String = getString(R.string.seconds)

                // Evaluates the result to display in the list hours minutes or seconds
                measuringUnit =
                    if (time >= "01:00:00") "$stringHoursTxt" else if (time <= "00:00:60") "$stringSecondsTxt"
                    else "$stringMinutesTxt"

                splitTimesList.add("$distance KM | $time $measuringUnit")
                distance++
            }
        }
        return splitTimesList
    }

    private fun calculateSplitTimes() {
        splitsViewModel.calculateSplitTimes(distanceRun, resultPace)
    }

    private fun initListeners() {
        binding.btnRecalculate.setOnClickListener { backToTop() }
    }

    /**
     * Navigates back to the top-level Pace Calculator activity, clearing the activity stack.
     *
     * This function creates an intent to navigate back to the top-level Pace Calculator activity
     * It adds the `FLAG_ACTIVITY_CLEAR_TOP` flag to the intent,
     * which clears the activity stack and ensures that the top-level activity is brought to the front.
     * After starting the intent, it finishes the current activity to ensure it is removed from the stack.
     */
    private fun backToTop() {
        val intent = Intent(this, PaceCalculatorActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}