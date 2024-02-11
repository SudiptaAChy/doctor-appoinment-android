package com.example.doctorsappointment.models

data class ScheduleModel(
    val id: String? = null,
    val creator: String? = null,
    val time: String? = null,
    val totalSlot: Int? = 0,
    val available: Boolean = false,
)
