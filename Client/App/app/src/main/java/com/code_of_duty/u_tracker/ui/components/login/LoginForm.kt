package com.code_of_duty.u_tracker.ui.components.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.code_of_duty.u_tracker.ui.components.ui.CustomButton
import com.code_of_duty.u_tracker.ui.components.ui.EditTextField
import com.code_of_duty.u_tracker.ui.components.ui.FormsCard

@Composable
fun LoginForm(onClick: () -> Unit) {
    //TODO: change for viewModel and use state
    val code = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    FormsCard(
        title = "login",
        editFields = listOf(
            {
                EditTextField(
                    label = "carnet",
                    value = code,
                    onValueChange = {code.value = it},
                )
            },
            {
                EditTextField(
                    label = "contraseña",
                    value = password,
                    onValueChange = {password.value = it},
                    isPassword = true
                )
            },
            {
                CustomButton(text = "Ingresar", onClick = onClick)
            }
        ),
        onClick = {}
    )
}