package com.sergiolopez.runningpacecalculator.util

import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TimeUtils {
    companion object {
        /**
         * Converts a floating-point value representing hours into a formatted time string.
         *
         * This function takes a float value `hours` and converts it into a time string
         * in the format "HH:MM:SS"
         *
         * @param hours The input value representing hours.
         * @return A formatted time string in the format "HH:MM:SS".
         */
        fun formatHoursToTimeString(hours: Float): String {
            val totalMinutes = (hours * 60).toInt()

            val formattedHours = totalMinutes / 60
            val formattedMinutes = totalMinutes % 60
            val formattedSeconds = ((hours * 3600) % 60).toInt()

            return String.format(
                "%02d:%02d:%02d",
                formattedHours,
                formattedMinutes,
                formattedSeconds
            )
        }


        /**
         * Parses string values representing hours, minutes, and seconds into a formatted time string.
         *
         * This function takes three string parameters, `hoursStr`, `minutesStr`, and `secondsStr`,
         * which represent the hours, minutes, and seconds respectively. It converts these string values
         * into integer representations and constructs a `LocalTime` object. The resulting `LocalTime`
         * is then formatted into a time string in the "HH:mm:ss" format using the `DateTimeFormatter`.
         *
         * @param hoursStr A string representing hours.
         * @param minutesStr A string representing minutes.
         * @param secondsStr A string representing seconds.
         * @return A formatted time string in the "HH:mm:ss" format.
         */
        fun parseStringToTime(hoursStr: String, minutesStr: String, secondsStr: String): String {
            val hours = hoursStr.toFloatOrNull()?.toInt() ?: 0
            val minutes = minutesStr.toFloatOrNull()?.toInt() ?: 0
            val seconds = secondsStr.toFloatOrNull()?.toInt() ?: 0

            val currentTime = LocalTime.of(hours, minutes, seconds)

            return currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
        }

    }
}
