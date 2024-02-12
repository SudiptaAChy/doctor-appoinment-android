package com.example.doctorsappointment.dashBoard.profile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doctorsappointment.models.BookedAppointmentItemModel
import com.example.doctorsappointment.repositories.AuthRepositories
import com.example.doctorsappointment.repositories.DoctorRepositories
import com.example.doctorsappointment.utils.DateTimeUtils
import com.example.doctorsappointment.utils.ResponseState
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _appointmentsResponse = MutableLiveData<ResponseState<Any?>>()
    val appointmentsResponse: LiveData<ResponseState<Any?>> = _appointmentsResponse

    private val authRepository = AuthRepositories
    private val doctorRepository = DoctorRepositories

    fun logoutUser() {
        authRepository.logOutUser()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAllBookedAppointments() {
        viewModelScope.launch {
            _appointmentsResponse.value = ResponseState.Loading

            val (appointments, exception) = doctorRepository
                .getAppointmentsOfUser(AuthRepositories.getCurrentUserId())

            if (exception != null) {
                _appointmentsResponse.value = ResponseState.Error(exception.message.toString())
                return@launch
            }

            val (doctors, exception1) = doctorRepository.getAllDoctors()

            if (exception1 != null) {
                _appointmentsResponse.value = ResponseState.Error(exception1.message.toString())
                return@launch
            }

            val bookedAppointments = mutableListOf<BookedAppointmentItemModel>()
            appointments?.forEach { appointment ->
                val relatedDoctor = doctors?.first { doctor ->
                    doctor.uid == appointment.doctorId
                }

                val (schedule, error) = doctorRepository.getScheduleById(appointment.scheduleId ?: "")

                var createdDateTime = ""
                var timeSlot = ""

                if (error == null) timeSlot = schedule?.time ?: ""

                if (appointment.createdAt?.isNotEmpty() == true) createdDateTime = DateTimeUtils.convertTimestampToDateTime(appointment.createdAt)
                bookedAppointments.add(
                    BookedAppointmentItemModel(drName = relatedDoctor?.name ?: "", date = createdDateTime, timeSlot = timeSlot)
                )
            }

            _appointmentsResponse.value = ResponseState.Success(data = bookedAppointments)
        }
    }
}