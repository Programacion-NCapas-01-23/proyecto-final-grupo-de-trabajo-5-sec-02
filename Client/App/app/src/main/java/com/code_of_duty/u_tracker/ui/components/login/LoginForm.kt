package com.code_of_duty.u_tracker.ui.components.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.code_of_duty.u_tracker.ui.components.ui.CustomButton
import com.code_of_duty.u_tracker.ui.components.ui.DialogAlert
import com.code_of_duty.u_tracker.ui.components.ui.EditTextField
import com.code_of_duty.u_tracker.ui.components.ui.FormsCard
import com.code_of_duty.u_tracker.ui.components.ui.KeyboardType
import com.code_of_duty.u_tracker.ui.models.screens.login.LoginViewModel

@Composable
fun LoginForm(
    loginViewModel: LoginViewModel,
    onClick: () -> Unit
) {
    val error = remember { mutableStateOf(false) }
    val loading = remember { mutableStateOf(false) }

    FormsCard(
        title = "Login",
        editFields = listOf(
            {
                EditTextField(
                    label = "carnet",
                    value = loginViewModel.getCode(),
                    onValueChange = {loginViewModel.setCode(it)},
                    type = KeyboardType.Number
                )
            },
            {
                EditTextField(
                    label = "contraseña",
                    value = loginViewModel.getPassword(),
                    onValueChange = {loginViewModel.setPassword(it)},
                    type = KeyboardType.Password
                )
            },
            {
                CustomButton(text = "Ingresar", loading = loading.value){
                    loading.value = true
                    if (loginViewModel.getLogin().value) {
                        onClick()
                    } else {
                        error.value = true
                    }
                    loading.value = false
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