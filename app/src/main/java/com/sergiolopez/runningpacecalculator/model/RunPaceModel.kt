package com.sergiolopez.runningpacecalculator.model

import android.util.Log

class RunPaceModel(
    var distanceRunSelected: Int,
    var hours: Float,
    var minutes: Float,
    var seconds: Float

) {
    fun calculatePace(): Double {
            // Convert all units to minutes
            val totalMinutes = (this.hours * 60) + this.minutes + (this.seconds / 60)

            // Calculate pace in hours per unit of distance
            val result = totalMinutes / 60.0 / this.distanceRunSelected

            return result
    }

    fun calculateSplitTimes(distance: Int, resultPace: Float): MutableList<Float> {
        val splitTimesList = mutableListOf<Float>()

        for (i in 2..distance) {
            splitTimesList.add(resultPace * i)
        }

        return splitTimesList
    }

}
