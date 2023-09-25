package com.sergiolopez.runningpacecalculator.model

class RunPaceModel(
    private var distanceRunSelected: Int,
    private var hours: Double,
    private var minutes: Double,
    private var seconds: Double

) {
    /**
     * Calculates the running pace in hours per unit of distance.
     *
     * This function calculates the running pace based on the provided hours, minutes, seconds, and distance.
     * The result is the pace in hours per unit of distance.
     *
     * @return The calculated pace in hours per unit of distance.
     */
    fun calculatePace(): Double {
        // Convert all units to minutes
        val totalMinutes = (this.hours * 60) + this.minutes + (this.seconds / 60)

        // Calculate pace in hours per unit of distance
        return totalMinutes / 60.0 / distanceRunSelected
    }

    /**
     * Calculates the race split times kilometer by kilometer.
     *
     * This function calculates the split times for each kilometer of a race based on the total distance
     * and the result pace. The result is a list of split times in hours.
     *
     * @param distance The total distance chosen for the race.
     * @param resultPace The pace in hours per unit of distance.
     * @return A list of race split times in hours.
     */
    companion object {
        fun calculateSplitTimes(distance: Int, resultPace: Double): MutableList<Double> {
            val splitTimesList = mutableListOf<Double>()

            for (i in 1..distance) {
                splitTimesList.add(resultPace * i)
            }
            return splitTimesList
        }
    }

}
