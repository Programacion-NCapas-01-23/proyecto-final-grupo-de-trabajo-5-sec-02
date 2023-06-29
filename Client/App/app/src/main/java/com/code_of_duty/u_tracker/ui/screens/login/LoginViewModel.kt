package com.code_of_duty.u_tracker.ui.screens.login


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code_of_duty.u_tracker.data.repositories.LoginRepository
import com.code_of_duty.u_tracker.enums.LoginStatus
import com.code_of_duty.u_tracker.ui.graphs.Graph
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

//create and view model for login and inject repository
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {
    private var code: MutableState<String> = mutableStateOf("")
    private var password: MutableState<String> = mutableStateOf("")
    private var login: MutableState<LoginStatus> = mutableStateOf(LoginStatus.NONE)

    //region getters and setters
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
    //endregion

    fun login(_code: String = code.value, _password: String = password.value) {
        viewModelScope.launch {
            try {
                val loginResponse = loginRepository.login(code.value, password.value)
                loginResponse.token.let {
                    login.value = LoginStatus.LOGIN_SUCCESS
                    val token = it
                    loginRepository.saveToken(token)
                    loginRepository.saveUser(_code, _password)
                }
            } catch (e: Exception) {
                login.value = LoginStatus.LOGIN_FAILED
            }
        }
    }

    private suspend fun validateToken(): Boolean {
        val deferred = CompletableDeferred<Boolean>()
        viewModelScope.launch {
             try{
                val token =  loginRepository.getToken()
                val user = loginRepository.getUser()
                when{
                    token.isEmpty() -> deferred.complete(false)
                    user.code.isEmpty() -> deferred.complete(false)
                    token.isEmpty() && user.code.isNotEmpty() -> {
                        login(user.code, user.password)
                        deferred.complete(true)
                    }
                    //TODO(): if token is expired, refresh token
                    else -> deferred.complete(true)
                }
            } catch (e: Exception) {
                deferred.complete(false)
            }
        }
        return deferred.await()
    }
    suspend fun verifyLogin(graph: Graph): String {
        return withContext(Dispatchers.IO) {
            if (validateToken()) {
                graph.HOME
            } else {
                graph.AUTHENTICATION
            }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            val token = loginRepository.getToken()
            val user = loginRepository.getUser()
            loginRepository.deleteToken(token)
            loginRepository.deleteUser(user)
        }
    }
}