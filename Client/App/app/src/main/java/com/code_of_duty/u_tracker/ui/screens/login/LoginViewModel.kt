package com.code_of_duty.u_tracker.ui.screens.login

import androidx.lifecycle.ViewModel
import com.code_of_duty.u_tracker.data.network.request.LoginRequest

class LoginViewModel: ViewModel() {
    fun login(loginRequest: LoginRequest, onSuccess: () -> Unit, onError: () -> Unit) {
        onSuccess()
    }
}