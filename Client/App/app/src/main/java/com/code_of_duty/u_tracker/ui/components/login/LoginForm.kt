package com.code_of_duty.u_tracker.ui.components.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.code_of_duty.u_tracker.ui.components.ui.CustomButton
import com.code_of_duty.u_tracker.ui.components.ui.EditTextField
import com.code_of_duty.u_tracker.ui.components.ui.FormsCard
import com.code_of_duty.u_tracker.ui.components.ui.KeyboardType
import com.code_of_duty.u_tracker.ui.screens.login.LoginViewModel

@Composable
fun LoginForm(
    loginViewModel: LoginViewModel,
    onClick: () -> Unit
) {
    //TODO: change for viewModel and use state
    val code = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    FormsCard(
        title = "Login",
        editFields = listOf(
            {
                EditTextField(
                    label = "carnet",
                    value = code,
                    onValueChange = {code.value = it},
                    type = KeyboardType.Number
                )
            },
            {
                EditTextField(
                    label = "contraseña",
                    value = password,
                    onValueChange = {password.value = it},
                    type = KeyboardType.Password
                )
            },
            {
                CustomButton(text = "Ingresar"){
                    loginViewModel.login(code.value, password.value, onSuccess = onClick, onError = {})
                }
            }
        ),
        onClick = {}
    )
}