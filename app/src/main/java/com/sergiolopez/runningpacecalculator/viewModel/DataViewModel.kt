package com.sergiolopez.runningpacecalculator.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sergiolopez.runningpacecalculator.model.RunPaceModel

class DataViewModel : ViewModel() {

    val distanceSelected = MutableLiveData<Int>(0)
    var hours = MutableLiveData<Float>(0f)
    var minutes = MutableLiveData<Float>(0f)
    var seconds = MutableLiveData<Float>(0f)
    var resultPace = MutableLiveData<Float>(0f)
    var splitTimesList = MutableLiveData<MutableList<Float>>()

    fun setDistanceSelected(distance: Int) {
        distanceSelected.value = distance
    }

    fun setHours(hours: Float) {
        this.hours.value = hours
    }

    fun setMinutes(minutes: Float) {
        this.minutes.value = minutes
    }

    fun setSeconds(seconds: Float) {
        this.seconds.value = seconds
    }

    private fun createRunPaceObject(): RunPaceModel {
        val distance = distanceSelected.value ?: 0
        val hoursValue = hours.value ?: 0f
        val minutesValue = minutes.value ?: 0f
        val secondsValue = seconds.value ?: 0f

        return RunPaceModel(distance, hoursValue, minutesValue, secondsValue)
    }

    fun calculatePace() {
        resultPace.value = createRunPaceObject().calculatePace()
    }

    fun calculateSplitTimes(distance: Int, resultPace: Float) {
        splitTimesList.value = createRunPaceObject().calculateSplitTimes(distance, resultPace)
    }

}