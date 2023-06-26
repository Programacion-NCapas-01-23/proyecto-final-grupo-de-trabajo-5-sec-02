package com.code_of_duty.u_tracker.ui.screens.signup

import androidx.lifecycle.ViewModel

class SignUpViewModel: ViewModel() {
    fun signUp(code: String, names: String, lastnames: String, email: String, password: String, onSuccess : () -> Unit, onError: () -> Unit) {
        if(code == "00014820" && email == "test@utracker.com" && password == "123456" && names == "test" && lastnames == "test") {
            onSuccess()
        } else {
            onError()
        }
    }
}