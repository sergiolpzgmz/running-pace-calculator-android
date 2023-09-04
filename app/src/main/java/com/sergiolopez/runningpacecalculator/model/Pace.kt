package com.sergiolopez.runningpacecalculator.model

class Pace(
    private val distanceSelected: Int,
    private var hours: Float,
    private val minutes: Float,
    private var seconds: Float
) {

    private fun calculatePeace(): Float {
        // Convert all units to minutes
        this.hours *= 60
        this.seconds /= 60

        return (this.hours + this.minutes + this.seconds) / this.distanceSelected
    }

}