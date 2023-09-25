package com.sergiolopez.runningpacecalculator.view.splits

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sergiolopez.runningpacecalculator.model.RunPaceModel

class SplitsViewModel : ViewModel() {
    var splitTimesList = MutableLiveData<MutableList<Double>>()

    fun calculateSplitTimes(distance: Int, resultPace: Double) {
        splitTimesList.value = RunPaceModel.calculateSplitTimes(distance, resultPace)
    }

}