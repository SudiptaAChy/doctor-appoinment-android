package com.example.doctorsappointment.dashBoard.appointmentList

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doctorsappointment.models.AppointmentItemModel
import com.example.doctorsappointment.models.ScheduleModel
import com.example.doctorsappointment.repositories.AuthRepositories
import com.example.doctorsappointment.repositories.DoctorRepositories
import com.example.doctorsappointment.utils.DateTimeUtils
import com.example.doctorsappointment.utils.ResponseState
import com.example.doctorsappointment.utils.SlotStatus
import com.example.doctorsappointment.utils.sortedByTime
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.max

class AppointmentListViewModel : ViewModel() {
    private val _scheduleResponse = MutableLiveData<ResponseState<Any?>>()
    val scheduleResponse: LiveData<ResponseState<Any?>> get() = _scheduleResponse


    private val _bookingResponse = MutableLiveData<ResponseState<Any?>>()
    val bookingResponse: LiveData<ResponseState<Any?>> get() = _bookingResponse

    private val repository = DoctorRepositories

    @RequiresApi(Build.VERSION_CODES.O)
    fun bookAppointment(
        scheduleId: String,
        doctorId: String?,
        notifyChange: () -> Unit,
    ) {
        viewModelScope.launch {
            _bookingResponse.value = ResponseState.Loading

            val exception = repository.bookAppointment(scheduleId, doctorId)
            if (exception != null) {
                _bookingResponse.value = ResponseState.Error(exception.message.toString())
            } else {
                _bookingResponse.value = ResponseState.Success("Appointment booked successfully!")
                notifyChange()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getScheduleOfDoctor(uid: String) {
        viewModelScope.launch {
            _scheduleResponse.value = ResponseState.Loading

            val (schedules, error) = repository.getScheduleOfDoctor(uid)
            if (error != null) {
                _scheduleResponse.value = ResponseState.Error(error.message.toString())
                return@launch
            }

            val (appointments, error2) = repository.getAllAppointments()
            if (error2 != null) {
                _scheduleResponse.value = ResponseState.Error(error2.message.toString())
                return@launch
            }

            val newAppointments = mutableListOf<AppointmentItemModel>()

            schedules?.forEach { schedule ->
                var totalBooked = 0
                var isBooked = false
                appointments?.forEach { appointment ->
                    if (schedule.id == appointment.scheduleId) {
                        totalBooked += 1
                        if (appointment.userId == AuthRepositories.getCurrentUserId()) {
                            isBooked = true
                        }
                    }
                }
                newAppointments.add(AppointmentItemModel(
                    id = schedule.id,
                    time = schedule.time,
                    totalSlot = schedule.totalSlot ?: 0,
                    availableSlot = max((schedule.totalSlot ?: 0) - totalBooked, 0),
                    slotStatus = getSlotStatus(schedule, totalBooked, isBooked),
                ))
            }

            val sortedAppointments = newAppointments.sortedByTime()

            _scheduleResponse.value = ResponseState.Success(data = sortedAppointments)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getSlotStatus(
        schedule: ScheduleModel,
        totalBooked: Int,
        isBooked: Boolean,
    ): String {
        if (!schedule.available) return SlotStatus.NOT_AVAILABLE.title

        if (schedule.time != null) {
            val splittedTimeSchedule = schedule.time.split(" ")
            var scheduleTime = splittedTimeSchedule[0].replace(":", ".").toDouble()
            if (splittedTimeSchedule[1].uppercase(Locale.ROOT) == "PM" && scheduleTime>=1.0) scheduleTime += 12

            val convertedTime = DateTimeUtils.convertTimestampToTime(DateTimeUtils.getCurrentTimestamp())
            val splittedTimeNow = convertedTime.split(" ")
            var currentTime = splittedTimeNow[0].replace(":", ".").toDouble()
            if (splittedTimeNow[1].uppercase(Locale.ROOT) == "PM" && currentTime>=1.0) currentTime += 12

            if (currentTime > scheduleTime) return SlotStatus.FINISHED.title
        }

        if (schedule.totalSlot==null || totalBooked>=schedule.totalSlot) return SlotStatus.FILLED_UP.title

        if (isBooked) return SlotStatus.BOOKED.title

        return SlotStatus.AVAILABLE.title
    }
}