package com.code_of_duty.u_tracker.ui.components.forgotPassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.code_of_duty.u_tracker.enums.ChangePasswordStatus
import com.code_of_duty.u_tracker.enums.TokenStatus
import com.code_of_duty.u_tracker.ui.models.TextFieldModel
import com.code_of_duty.u_tracker.ui.components.ui.CustomButton
import com.code_of_duty.u_tracker.ui.components.ui.EditTextField
import com.code_of_duty.u_tracker.ui.components.ui.FormsCard
import com.code_of_duty.u_tracker.ui.components.ui.KeyboardType
import com.code_of_duty.u_tracker.ui.screens.forgotPassword.ForgotPasswordViewModel

@Composable
fun ChangePasswordCard(
    viewModel: ForgotPasswordViewModel,
    onContinue: () -> Unit
) {
    val token = remember { mutableStateOf("") }
    val newPassword = remember { mutableStateOf(TextFieldModel()) }
    val confirmPassword = remember { mutableStateOf(TextFieldModel()) }
    val email = remember { mutableStateOf("") }
    val isEnabled = remember { mutableStateOf(true) }
    val loadingToken =  remember { mutableStateOf(false) }
    val loadingChangePass = remember { mutableStateOf(false) }

    LaunchedEffect(viewModel.tokenStatus().value){
        when(viewModel.tokenStatus().value){
            TokenStatus.SEND -> {
                loadingToken.value = false
                isEnabled.value = true
            }
            TokenStatus.FAILED -> {
                loadingToken.value = false
                viewModel.tokenStatus().value = TokenStatus.NONE
            }
            else -> {}
        }
    }

    LaunchedEffect(viewModel.changePassStatus().value){
        when(viewModel.changePassStatus().value){
            ChangePasswordStatus.SUCCESS -> {
                onContinue()
            }
            ChangePasswordStatus.FAILURE -> {
                loadingChangePass.value = false
                viewModel.changePassStatus().value = ChangePasswordStatus.NONE
            }
            else -> {}
        }
    }

    LaunchedEffect(newPassword.value.text.value) {
        newPassword.value.apply {
            isError.value = !text.value.matches(Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"))
            supportText.value = if (isError.value) "La contraseña debe tener al menos 8 caracteres, 1 mayuscula, 1 minuscula, 1 numero y un caracter especial" else ""
        }
    }

    LaunchedEffect(confirmPassword.value.text.value) {
        confirmPassword.value.apply {
            isError.value = newPassword.value.text.value != text.value
            supportText.value = if (isError.value) "Las contraseñas no coinciden" else ""
        }
    }

    FormsCard(
        title = "Cambiar contraseña",
        verticalScroll = true,
        editFields = listOf(
            {
                EditTextField(
                    label = "Correo electrónico",
                    value = email,
                    type = KeyboardType.Email
                )
            },
            {
                CustomButton(text = "Solicitar código", loading = loadingToken.value) {
                    loadingToken.value = true
                    viewModel.generateToken()
                }
            },
            {
                EditTextField(
                    label = "Token",
                    value = token,
                    isEnabled = isEnabled,
                )
            },
            {
                EditTextField(
                    label = "Nuevo contraseña",
                    value = newPassword.value.text,
                    type = KeyboardType.Password,
                    isError = newPassword.value.isError,
                    supportText = newPassword.value.supportText,
                    isEnabled = isEnabled
                )
            },
            {
                EditTextField(
                    label = "Confirmar contraseña",
                    value = confirmPassword.value.text,
                    type = KeyboardType.Password,
                    isError = confirmPassword.value.isError,
                    supportText = confirmPassword.value.supportText,
                    isEnabled = isEnabled
                )
            },
            {
                CustomButton(text ="Cambiar contraseña", loading = loadingChangePass.value, isEnabled = isEnabled.value) {
                    if (!newPassword.value.isError.value && !confirmPassword.value.isError.value){
                        loadingChangePass.value = true
                        viewModel.changePassword()
                    }
                }
            }
        ),
        onClick = {}
    )
}