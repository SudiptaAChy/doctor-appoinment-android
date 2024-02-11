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
        val startOfToday = now.withHour(0).withMinute(0).withSecond(0).withNano(0)
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
}