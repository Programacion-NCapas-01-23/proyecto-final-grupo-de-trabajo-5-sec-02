package com.code_of_duty.u_tracker.ui.components.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.code_of_duty.u_tracker.enums.LoginStatus
import com.code_of_duty.u_tracker.ui.components.ui.CustomButton
import com.code_of_duty.u_tracker.ui.components.ui.DialogAlert
import com.code_of_duty.u_tracker.ui.components.ui.EditTextField
import com.code_of_duty.u_tracker.ui.components.ui.FormsCard
import com.code_of_duty.u_tracker.ui.components.ui.KeyboardType
import com.code_of_duty.u_tracker.ui.screens.login.LoginViewModel

@Composable
fun LoginForm(
    loginViewModel: LoginViewModel,
    onClick: () -> Unit
) {
    val error = remember { mutableStateOf(false) }
    val loading = remember { mutableStateOf(false) }

    LaunchedEffect(loginViewModel.getLogin().value){
        when(loginViewModel.getLogin().value){
            LoginStatus.LOGIN_SUCCESS -> {
                onClick()
            }
            LoginStatus.LOGIN_FAILED -> {
                error.value = true
                loading.value = false
                loginViewModel.setLogin(LoginStatus.NONE)
            }
            else -> {}
        }
    }

    FormsCard(
        title = "Login",
        editFields = listOf(
            {
                EditTextField(
                    label = "carnet",
                    value = loginViewModel.getCode(),
                    type = KeyboardType.Number
                )
            },
            {
                EditTextField(
                    label = "contraseña",
                    value = loginViewModel.getPassword(),
                    type = KeyboardType.Password
                )
            },
            {
                CustomButton(text = "Ingresar", loading = loading.value){
                    loading.value = true
                    loginViewModel.login()
                }
            }
        ),
        onClick = {}
    )

    if (error.value) {
        DialogAlert(
            title = "Error",
            message = "Carnet o contraseña incorrectos",
            onConfirm = {
                error.value = false
                loginViewModel.setCode("")
                loginViewModel.setPassword("")
            },
            needCancel = false
        )
    }
}