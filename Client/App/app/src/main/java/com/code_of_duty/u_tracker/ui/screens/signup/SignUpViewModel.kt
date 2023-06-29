package com.code_of_duty.u_tracker.ui.screens.signup

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code_of_duty.u_tracker.data.repositories.SignUpRepository
import com.code_of_duty.u_tracker.enums.SignUpStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpRepository: SignUpRepository
) : ViewModel() {

    private var code: MutableState<String> = mutableStateOf("")
    private var username: MutableState<String> = mutableStateOf("")
    private var email: MutableState<String> = mutableStateOf("")
    private var password: MutableState<String> = mutableStateOf("")
    private var confirm: MutableState<String> = mutableStateOf("")
    private var degreeId: MutableState<String> = mutableStateOf("")
    private var signup: MutableState<SignUpStatus> = mutableStateOf(SignUpStatus.NONE)

    fun getCode(): MutableState<String> {
        return code
    }

    fun setCode(code: String) {
        this.code.value = code
    }

    fun getUsername(): MutableState<String> {
        return username
    }

    fun setUsername(username: String) {
        this.username.value = username
    }

    fun getEmail(): MutableState<String> {
        return email
    }

    fun setEmail(email: String) {
        this.email.value = email
    }

    fun getPassword(): MutableState<String> {
        return password
    }

    fun setPassword(password: String) {
        this.password.value = password
    }

    fun getConfirm(): MutableState<String> {
        return confirm
    }

    fun setConfirm(confirm: String) {
        this.confirm.value = confirm
    }

    fun getDegreeId(): MutableState<String> {
        return degreeId
    }

    fun setDegreeId(degreeId: String) {
        this.degreeId.value = degreeId
    }

    fun getSignup(): MutableState<SignUpStatus> {
        return signup
    }

    fun setSignup(status: SignUpStatus) {
        signup.value = status
    }

    fun signUp() {
        viewModelScope.launch {
            try {
                val signUpResponse = signUpRepository.signup(code.value, username.value, email.value, password.value, degreeId.value)
                signUpResponse.message.let {
                    signup.value = SignUpStatus.SIGNUP_SUCCESS
                }
            } catch (e: Exception) {
                signup.value = SignUpStatus.SIGNUP_FAILED
            }
        }
    }
}