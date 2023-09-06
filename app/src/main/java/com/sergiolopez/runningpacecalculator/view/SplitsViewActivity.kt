package com.sergiolopez.runningpacecalculator.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sergiolopez.runningpacecalculator.R

class SplitsViewActivity : AppCompatActivity() {

    private var resultPace = 0f
    private var distanceRun = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splits_view)

        extractIntent()
    }

    private fun extractIntent() {
        resultPace = intent.extras?.getFloat(ResultPaceCalculatorActivity.KEY_RESULT_PACE)!!
        distanceRun = intent.extras?.getInt(ResultPaceCalculatorActivity.KEY_RUN_DISTANCE)!!
    }


}