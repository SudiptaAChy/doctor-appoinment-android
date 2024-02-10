package com.example.doctorsappointment.dashBoard.doctorsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doctorsappointment.repositories.DoctorRepositories
import com.example.doctorsappointment.utils.ResponseState
import kotlinx.coroutines.launch

class DoctorListViewModel: ViewModel() {
    private val _doctorResponse = MutableLiveData<ResponseState<Any?>>()
    val doctorResponse: LiveData<ResponseState<Any?>> get() = _doctorResponse
    
    private val repository = DoctorRepositories
    
    fun getAllDoctorList() {
        viewModelScope.launch {
            _doctorResponse.value = ResponseState.Loading

            val (doctors, error) = repository.getAllDoctors()

            if (error != null) {
                _doctorResponse.value = ResponseState.Error(error.message.toString())
            } else {
                _doctorResponse.value = ResponseState.Success(data = doctors)
            }
        }
    }
}