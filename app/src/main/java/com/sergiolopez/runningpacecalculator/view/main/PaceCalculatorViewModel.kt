package com.sergiolopez.runningpacecalculator.view.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sergiolopez.runningpacecalculator.model.RunPaceModel

class PaceCalculatorViewModel : ViewModel() {

    val distanceSelected = MutableLiveData<Int>(0)
    var hours = MutableLiveData<Double>(0.0)
    var minutes = MutableLiveData<Double>(0.0)
    var seconds = MutableLiveData<Double>(0.0)

    fun setDistanceSelected(distance: Int) {
        distanceSelected.value = distance
    }

    fun setHours(hours: Double) {
        this.hours.value = hours
    }

    fun setMinutes(minutes: Double) {
        this.minutes.value = minutes
    }

    fun setSeconds(seconds: Double) {
        this.seconds.value = seconds
    }

    private fun createRunPaceObject(): RunPaceModel {
        val distance = distanceSelected.value ?: 0
        val hoursValue = hours.value ?: 0.0
        val minutesValue = minutes.value ?: 0.0
        val secondsValue = seconds.value ?: 0.0

        return RunPaceModel(distance, hoursValue, minutesValue, secondsValue)
    }
}