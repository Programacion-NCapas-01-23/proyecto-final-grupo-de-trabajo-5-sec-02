package com.code_of_duty.u_tracker.ui.components.signup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.code_of_duty.u_tracker.ui.components.ui.CustomButton
import com.code_of_duty.u_tracker.ui.components.ui.EditTextField
import com.code_of_duty.u_tracker.ui.components.ui.FormsCard
import com.code_of_duty.u_tracker.ui.components.ui.KeyboardType
import com.code_of_duty.u_tracker.ui.screens.signup.SignUpViewModel

@Composable
fun SignUpForm (
    //TODO: change for viewModel and use state
    signUpViewModel: SignUpViewModel,
    onClick: () -> Unit,
) {
    val code = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPass = remember { mutableStateOf("") }
    val names = remember { mutableStateOf("") }
    val lastnames = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val passwordNotMatch = remember { mutableStateOf(false)}
FormsCard(
        title = "Crea tu cuenta de U-Tracker",
        editFields = listOf(
            {
                EditTextField(
                    label = "Ingresa tu carnet",
                    value = code,
                    onValueChange = {code.value = it},
                    type = KeyboardType.Number
                )
            },
            {
                EditTextField(
                    label = "Ingresa tus nombres",
                    value = names,
                    onValueChange = {names.value = it},
                    type = KeyboardType.Text
                )
            },
            {
                EditTextField(
                    label = "Ingresa tus apellidos",
                    value = lastnames,
                    onValueChange = {lastnames.value = it},
                    type = KeyboardType.Text
                )
            },
            {
                EditTextField(
                    label = "Ingresa tu correo electrónico",
                    value = email,
                    onValueChange = {email.value = it},
                    type = KeyboardType.Email
                )
            },
            {
                EditTextField(
                    label = "Ingresa una contraseña",
                    value = password,
                    onValueChange = {password.value = it},
                    type = KeyboardType.Password,
                )
            },
            {
                EditTextField(
                    label = "Confirma tu contraseña",
                    value = confirmPass,
                    onValueChange = {confirmPass.value = it},
                    type = KeyboardType.Password
                )
            },
            {
                CustomButton(text = "Registrarse"){
                    if (password.value != confirmPass.value) {
                        passwordNotMatch.value = true
                    }
                    signUpViewModel.signUp(code.value, names.value, lastnames.value, email.value, password.value, onSuccess = onClick, onError = {})
                }
            }
        ),
        onClick = {}
    )
}