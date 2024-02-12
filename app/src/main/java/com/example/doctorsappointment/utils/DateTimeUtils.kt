package com.example.doctorsappointment.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

object DateTimeUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    fun getStartingTimestampOfToday(): String {
        val now = LocalDateTime.now()
        val startOfToday = now.withHour(0).withMinute(0).withSecond(0).withNano(0).minusHours(6)
        val timestamp = startOfToday.toEpochSecond(ZoneOffset.UTC)
        return timestamp.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentTimestamp(): String {
        val offset = ZoneOffset.ofHours(6) // GMT+06:00
        val timestamp = Instant.now().atZone(ZoneId.ofOffset("GMT", offset)).toEpochSecond()
        return timestamp.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertTimestampToTime(timestamp: String): String {
        val offset = ZoneOffset.ofHours(6) // GMT+06:00
        val instant = Instant.ofEpochSecond(timestamp.toLong())
        val time = instant.atZone(ZoneId.ofOffset("GMT", offset)).toLocalTime()
        return time.format(DateTimeFormatter.ofPattern("h:mm a"))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertTimestampToDateTime(timestamp: String): String {
        val offset = ZoneOffset.ofHours(6) // GMT+06:00
        val instant = Instant.ofEpochSecond(timestamp.toLong())
        val dateTime = LocalDateTime.ofInstant(instant, ZoneId.ofOffset("GMT", offset))
        val formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY hh:mm a")
        return dateTime.format(formatter)
    }

    fun getTimeInMinutes(time: String): Int {
        val parts = time.split(":")
        val hour = parts[0].toInt()
        val minute = parts[1].substringBefore(" ").toInt()
        val period = parts[1].substringAfter(" ")

        var totalMinutes = hour * 60 + minute
        if (period.equals("PM", ignoreCase = true) && hour != 12) {
            totalMinutes += 12 * 60
        }
        return totalMinutes
    }
}