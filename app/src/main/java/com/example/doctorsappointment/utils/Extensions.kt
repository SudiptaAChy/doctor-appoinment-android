package com.example.doctorsappointment.utils

import android.util.Log
import com.example.doctorsappointment.models.AppointmentItemModel

fun List<AppointmentItemModel>.sortedByTime(): List<AppointmentItemModel> {
    return this.sortedBy {
        DateTimeUtils.getTimeInMinutes(it.time!!)
    }
}