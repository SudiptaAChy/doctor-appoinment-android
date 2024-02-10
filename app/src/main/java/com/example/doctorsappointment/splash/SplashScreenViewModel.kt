package com.example.doctorsappointment.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doctorsappointment.repositories.AuthRepositories
import kotlinx.coroutines.launch

class SplashScreenViewModel: ViewModel() {
    private val _userLoggedIn = MutableLiveData<Int>(-1)
    val userLoggedIn: LiveData<Int> get() = _userLoggedIn

    private val repository = AuthRepositories

    fun isUserLoggedIn() {
        val loggedIn = repository.isLoggedIn()
        if (loggedIn) _userLoggedIn.value = 1
        else _userLoggedIn.value = 0
    }
}