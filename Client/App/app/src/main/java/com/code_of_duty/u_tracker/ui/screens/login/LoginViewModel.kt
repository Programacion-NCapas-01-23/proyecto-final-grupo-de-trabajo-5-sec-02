package com.code_of_duty.u_tracker.ui.screens.login


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code_of_duty.u_tracker.data.repositories.LoginRepository
import com.code_of_duty.u_tracker.enums.LoginStatus
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
    private var login: MutableState<LoginStatus> = mutableStateOf(LoginStatus.NONE)

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
    fun getLogin(): MutableState<LoginStatus> {
        return login
    }

    fun setLogin(status: LoginStatus) {
        login.value = status
    }

    fun login() {
        viewModelScope.launch {
            try {
                val loginResponse = loginRepository.login(code.value, password.value)
                loginResponse.token.let {
                    login.value = LoginStatus.LOGIN_SUCCESS
                    val token = it
                    //loginRepository.saveToken(token)
                }
            } catch (e: Exception) {
                login.value = LoginStatus.LOGIN_FAILED
            }
        }
    }
}