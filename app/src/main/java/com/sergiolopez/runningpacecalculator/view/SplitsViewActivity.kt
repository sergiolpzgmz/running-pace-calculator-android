package com.sergiolopez.runningpacecalculator.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sergiolopez.runningpacecalculator.R
import com.sergiolopez.runningpacecalculator.view.adapter.SplitsAdapter
import com.sergiolopez.runningpacecalculator.viewModel.DataViewModel

class SplitsViewActivity : AppCompatActivity() {

    private lateinit var dataViewModel: DataViewModel

    private var resultPace = 0f
    private var distanceRun = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splits_view)

        dataViewModel = ViewModelProvider(this)[DataViewModel::class.java]

        extractIntent()


        dataViewModel.calculateSplitTimes(distanceRun, resultPace)
        dataViewModel.splitTimesList.observe(this, Observer { result ->
            result.forEach { r ->
                Log.d("sergioA", r.toString())
            }

        })

    }

    private fun extractIntent() {
        resultPace = intent.extras?.getFloat(ResultPaceCalculatorActivity.KEY_RESULT_PACE)!!
        distanceRun = intent.extras?.getInt(ResultPaceCalculatorActivity.KEY_RUN_DISTANCE)!!
    }
    private fun initRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.rvSplits)
        recyclerView.layoutManager=LinearLayoutManager(this)
        val list = mutableListOf<Float>(1f,2f)
        recyclerView.adapter=SplitsAdapter(list)
    }
}