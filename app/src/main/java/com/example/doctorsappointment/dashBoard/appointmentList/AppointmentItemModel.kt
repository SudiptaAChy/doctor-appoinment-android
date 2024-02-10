package com.example.doctorsappointment.dashBoard.appointmentList

data class AppointmentItemModel(
    val time: String? = null,
    val totalSlot: Int? = 0,
    val availableSlot: Int? = 0,
    val slotStatus: String? = null,
)
