package com.code_of_duty.u_tracker.ui.components.forgotPassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.code_of_duty.u_tracker.ui.models.TextFieldModel
import com.code_of_duty.u_tracker.ui.components.ui.CustomButton
import com.code_of_duty.u_tracker.ui.components.ui.EditTextField
import com.code_of_duty.u_tracker.ui.components.ui.FormsCard
import com.code_of_duty.u_tracker.ui.components.ui.KeyboardType

@Composable
fun ChangePasswordCard(
    onContinue: () -> Unit
) {
    //set a variable for each field
    val token = remember { mutableStateOf("") }
    val newPassword = remember { mutableStateOf(TextFieldModel()) }
    val confirmPassword = remember { mutableStateOf(TextFieldModel()) }
    val email = remember { mutableStateOf("") }

    //check if the password is valid
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
                CustomButton(text = "Solicitar código") {
                }
            },
            {
                EditTextField(
                    label = "Token",
                    value = token
                )
            },
            {
                EditTextField(
                    label = "Nuevo contraseña",
                    value = newPassword.value.text,
                    type = KeyboardType.Password,
                    isError = newPassword.value.isError,
                    supportText = newPassword.value.supportText
                )
            },
            {
                EditTextField(
                    label = "Confirmar contraseña",
                    value = confirmPassword.value.text,
                    type = KeyboardType.Password,
                    isError = confirmPassword.value.isError,
                    supportText = confirmPassword.value.supportText
                )
            },
            {
                CustomButton(text ="Cambiar contraseña") {
                    if (!newPassword.value.isError.value && !confirmPassword.value.isError.value)
                        onContinue()
                }
            }
        ),
        onClick = {}
    )
}