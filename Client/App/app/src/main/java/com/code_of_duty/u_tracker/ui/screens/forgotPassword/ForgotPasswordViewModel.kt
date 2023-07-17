package com.code_of_duty.u_tracker.ui.screens.forgotPassword

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code_of_duty.u_tracker.data.repositories.ForgotPasswordRepository
import com.code_of_duty.u_tracker.enums.ChangePasswordStatus
import com.code_of_duty.u_tracker.enums.TokenStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val repository: ForgotPasswordRepository
): ViewModel() {
    //region Variables
    private val _email = mutableStateOf("")
    private val _token = mutableStateOf("")
    private val _newPassword = mutableStateOf("")
    private val _confirmPassword = mutableStateOf("")
    private val _tokenStatus =  mutableStateOf(TokenStatus.NONE)
    private val _changePassStatus = mutableStateOf(ChangePasswordStatus.NONE)
    //endregion

    //region Getters and setters
    fun email() = _email
    fun token() = _token
    fun newPassword() = _newPassword
    fun confirmPassword() = _confirmPassword
    fun tokenStatus() = _tokenStatus
    fun changePassStatus() = _changePassStatus
    //endregion

    //region Functions
    fun generateToken(){
        viewModelScope.launch {
            try{
                val response = repository.generateToken(_email.value)
                response.message.let{
                    _tokenStatus.value = TokenStatus.SEND
                }
            } catch (e: Exception){
                Log.e("ForgotPasswordViewModel", "generateToken: ${e.message}")
                _tokenStatus.value = TokenStatus.FAILED
            }
        }
    }

    fun changePassword(){
        viewModelScope.launch {
            try{
                val response = repository.changePassword(_email.value, _token.value, _newPassword.value, _confirmPassword.value)
                response.message.let{
                    _changePassStatus.value = ChangePasswordStatus.SUCCESS
                }
            } catch (e: Exception){
                Log.e("ForgotPasswordViewModel", "changePassword: ${e.message}")
                _changePassStatus.value = ChangePasswordStatus.FAILURE
            }
        }
    }
    //endregion
}
