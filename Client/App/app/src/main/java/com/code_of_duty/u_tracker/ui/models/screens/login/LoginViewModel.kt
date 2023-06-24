package com.code_of_duty.u_tracker.ui.models.screens.login

import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {
    fun login(code: String, password: String, onSuccess: () -> Unit, onError: () -> Unit) {
        if (code == "00271419" && password == "123456"){
            onSuccess()
        } else{
            onError()
        }
    }
}