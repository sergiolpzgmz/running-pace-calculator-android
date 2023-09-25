package com.sergiolopez.runningpacecalculator.view.result

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sergiolopez.runningpacecalculator.model.RunPaceModel

class ResulPaceViewModel : ViewModel() {
    val resultPace = MutableLiveData<Double>(0.0)

    fun calculatePace(distance:Int, hours:Double, minutes:Double, seconds:Double) {
       val runPaceObject: RunPaceModel = RunPaceModel(distance,hours,minutes,seconds)
        resultPace.value = runPaceObject.calculatePace()
    }
}