package com.example.doctorsappointment.signUp

import android.provider.ContactsContract.CommonDataKinds.Phone
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.doctorsappointment.models.UserModel
import com.example.doctorsappointment.repositories.AuthRepositories
import com.example.doctorsappointment.utils.Constants
import com.example.doctorsappointment.utils.ResponseState
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch

class SignupViewModel : ViewModel() {
    private val _userResponse = MutableLiveData<ResponseState<Any>>()
    val userResponse: LiveData<ResponseState<Any>> get() = _userResponse

    private val repository = AuthRepositories

    fun signUpUser(
        email: String,
        password: String,
        name: String,
        phone: String,
    ) {
        viewModelScope.launch {
            _userResponse.value = ResponseState.Loading

            val (user, exception) = repository.signUpUser(email, password)

            if (exception != null) {
                _userResponse.value = ResponseState.Error(exception.toString())
            } else {
                val error = repository.saveUserData(UserModel(user?.uid, name, phone))
                if (error != null) {
                    _userResponse.value = ResponseState.Error(errorMessage = error)
                } else {
                    _userResponse.value = ResponseState.Success("registration completed!")
                }
            }
        }
    }
}