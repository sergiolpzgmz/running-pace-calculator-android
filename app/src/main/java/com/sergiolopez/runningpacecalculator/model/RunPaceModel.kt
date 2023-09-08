package com.sergiolopez.runningpacecalculator.model

import android.util.Log

class RunPaceModel(
    var distanceRunSelected: Int,
    var hours: Float,
    var minutes: Float,
    var seconds: Float

) {
    fun calculatePace(): Float {
        // Convert all units to minutes
        val totalMinutes = (this.hours * 60) + this.minutes + (this.seconds / 60)

        return totalMinutes / this.distanceRunSelected
    }

    fun calculateSplitTimes(distance: Int, resultPace: Float): MutableList<Float> {
        val splitTimesList = mutableListOf<Float>()

        for (i in 2..distance) {
            splitTimesList.add(resultPace * i)
        }

        return splitTimesList
    }

}
