package com.sergiolopez.runningpacecalculator.util

class TimeUtils {
    companion object {
        fun formatHoursToTimeString(hours: Float): String {
            val totalMinutes = (hours * 60).toInt()
            val formattedHours = totalMinutes / 60
            val formattedMinutes = totalMinutes % 60
            val formattedSeconds = ((hours * 3600) % 60).toInt()

            return String.format("%02d:%02d:%02d", formattedHours, formattedMinutes, formattedSeconds)
        }
    }
}
