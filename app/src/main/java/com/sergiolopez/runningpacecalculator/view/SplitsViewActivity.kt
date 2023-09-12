package com.sergiolopez.runningpacecalculator.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sergiolopez.runningpacecalculator.databinding.ActivitySplitsViewBinding
import com.sergiolopez.runningpacecalculator.util.TimeUtils.Companion.formatHoursToTimeString
import com.sergiolopez.runningpacecalculator.view.adapter.SplitsAdapter
import com.sergiolopez.runningpacecalculator.viewModel.DataViewModel
import java.text.DecimalFormat

class SplitsViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplitsViewBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SplitsAdapter
    private lateinit var dataViewModel: DataViewModel
    private lateinit var btnRecalculate: Button

    private var resultPace = 0f
    private var distanceRun = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplitsViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataViewModel = ViewModelProvider(this)[DataViewModel::class.java]

        initComponents()
        extractIntent()
        initRecyclerView()
        initListeners()
    }

    private fun initComponents() {
        btnRecalculate = binding.btnRecalculate
        recyclerView = binding.rvSplits
    }

    private fun extractIntent() {
        resultPace = intent.extras?.getFloat(ResultPaceCalculatorActivity.KEY_RESULT_PACE)!!
        distanceRun = intent.extras?.getInt(ResultPaceCalculatorActivity.KEY_RUN_DISTANCE)!!
    }

    /**
     * Initializes the RecyclerView to display split times.
     *
     * This function configures the RecyclerView to display split times using a LinearLayoutManager.
     * It creates an instance of the SplitsAdapter with split times data and sets it as the adapter
     * for the RecyclerView.
     */
    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SplitsAdapter(createSplitTimesList())
        recyclerView.adapter = adapter
    }

    /**
     * Creates a list of split times in a user-readable format.
     *
     * This function calculates and creates a list of split times based on the calculated result pace
     * and distance. It observes changes in the split times data from the ViewModel (`dataViewModel`)
     * and formats them into a user-readable format (distance in kilometers | time in minutes).
     *
     * @return A mutable list of split times in the specified format.
     */
    private fun createSplitTimesList(): MutableList<String> {
        calculateSplitTimes()
        val splitTimesList = mutableListOf<String>()

        dataViewModel.splitTimesList.observe(this) { result ->
            var distance = 1
            var measuringUnit: String

            result.forEach { r ->
                val time = formatHoursToTimeString(r)

                // Evaluates the result to display in the list hours minutes or seconds
                measuringUnit =
                    if (time >= "01:00:00") "hours" else if (time <= "00:00:60") "seconds"
                    else "minutes"

                splitTimesList.add("$distance KM | $time $measuringUnit")
                distance++
            }
        }
        return splitTimesList
    }

    private fun calculateSplitTimes() {
        dataViewModel.calculateSplitTimes(distanceRun, resultPace)
    }

    private fun initListeners() {
        btnRecalculate.setOnClickListener { backToTop() }
    }

    /**
     * Navigates back to the top-level Pace Calculator activity, clearing the activity stack.
     *
     * This function creates an intent to navigate back to the top-level Pace Calculator activity
     * (`PaceCalculatorActivity`). It adds the `FLAG_ACTIVITY_CLEAR_TOP` flag to the intent,
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