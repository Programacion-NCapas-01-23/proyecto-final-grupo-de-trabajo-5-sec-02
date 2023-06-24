package com.code_of_duty.u_tracker.ui.components.forgotPassword.changePassword

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.code_of_duty.u_tracker.ui.components.ui.CustomButton
import com.code_of_duty.u_tracker.ui.components.ui.EditTextField
import com.code_of_duty.u_tracker.ui.components.ui.FormsCard

@Composable
fun ChangePasswordCard(
    onContinue: () -> Unit
) {
    val token = remember { mutableStateOf("") }
    val newPassword = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }
    FormsCard(
        title = "Cambiar contraseña",
        editFields = listOf(
            {
                EditTextField(
                    label = "Token",
                    value = token,
                    onValueChange = {token.value = it}
                )
            },
            {
                EditTextField(
                    label = "Nuevo contraseña",
                    value = newPassword,
                    onValueChange = {newPassword.value = it}
                )
            },
            {
                EditTextField(
                    label = "Confirmar contraseña",
                    value = confirmPassword,
                    onValueChange = {confirmPassword.value = it}
                )
            },
            {
                CustomButton(text ="Cambiar contraseña") {
                    onContinue()
                }
            }
        ),
        onClick = {}
    )
}