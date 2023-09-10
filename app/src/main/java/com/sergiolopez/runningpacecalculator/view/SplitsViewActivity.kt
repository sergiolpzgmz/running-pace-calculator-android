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

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SplitsAdapter(createSplitTimesList())
        recyclerView.adapter = adapter
    }

    private fun createSplitTimesList(): MutableList<String> {
        calculateSplitTimes()
        val splitTimesList = mutableListOf<String>()

        dataViewModel.splitTimesList.observe(this) { result ->
            var distance = 1
            splitTimesList.add(distance.toString() + " KM | " + formatHoursToTimeString(resultPace) + " min")
            distance++

            result.forEach { r ->
                splitTimesList.add(distance.toString() + " KM | " + formatHoursToTimeString(r) + " min")
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

    private fun backToTop() {
        val intent = Intent(this, PaceCalculatorActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}