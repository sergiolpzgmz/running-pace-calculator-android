package com.sergiolopez.runningpacecalculator.model

class RunPaceModel(
    var distanceSelected: Int,
    var hours: Float,
    var minutes: Float,
    var seconds: Float

) {
    fun calculatePace():Float {
        // Convert all units to minutes
        val totalMinutes = (hours * 60) + minutes + (seconds / 60)

        return totalMinutes / distanceSelected

    }
}
