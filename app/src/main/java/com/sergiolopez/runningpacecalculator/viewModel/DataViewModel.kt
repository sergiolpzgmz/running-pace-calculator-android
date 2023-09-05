package com.sergiolopez.runningpacecalculator.viewModel

import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataViewModel : ViewModel() {
    val distanceSelected = MutableLiveData<Int>(0)
    var hours = MutableLiveData<Float>(0f)
    var minutes = MutableLiveData<Float>(0f)
    var seconds = MutableLiveData<Float>(0f)
    var resultPace = MutableLiveData<Float>(0f)
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

    fun calculatePace() {

        val hoursValue = hours.value ?: 0f
        val minutesValue = minutes.value ?: 0f
        val secondsValue = seconds.value ?: 0f
        val distanceValue = distanceSelected.value?.toFloat() ?: 0f

        // Convert all units to minutes
        val totalMinutes = (hoursValue * 60) + minutesValue + (secondsValue / 60)

        resultPace.value = totalMinutes / distanceValue

    }


}