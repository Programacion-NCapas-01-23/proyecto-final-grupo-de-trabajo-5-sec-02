package com.code_of_duty.u_tracker.ui.models.screens.login


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code_of_duty.u_tracker.data.repositories.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//create and view model for login and inject repository
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {
    private var code: MutableState<String> = mutableStateOf("")
    private var password: MutableState<String> = mutableStateOf("")

    fun getCode(): MutableState<String> {
        return code
    }

    fun setCode(code: String) {
        this.code.value = code
    }

    fun getPassword(): MutableState<String> {
        return password
    }

    fun setPassword(password: String) {
        this.password.value = password
    }

    fun login(): Boolean {
        var login = false
        viewModelScope.launch {
            login = loginRepository.login(code.value, password.value)
        }
        return login
    }
}