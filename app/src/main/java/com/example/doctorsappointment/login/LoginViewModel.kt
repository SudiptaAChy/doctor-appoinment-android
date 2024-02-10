package com.example.doctorsappointment.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doctorsappointment.repositories.AuthRepositories
import com.example.doctorsappointment.utils.ResponseState
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    private val _userResponse = MutableLiveData<ResponseState<Any?>>()
    val userResponse: LiveData<ResponseState<Any?>> get() = _userResponse

    private val repository = AuthRepositories

    fun loginUser(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            _userResponse.value = ResponseState.Loading

            val (user, error) = repository.loginUser(email, password)

            if (error != null) {
                _userResponse.value = ResponseState.Error(error.message.toString())
            } else {
                _userResponse.value = ResponseState.Success(user)
            }
        }
    }
}