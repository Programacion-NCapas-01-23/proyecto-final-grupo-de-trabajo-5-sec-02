package com.code_of_duty.u_tracker.ui.components.forgotPassword.getVerificationToken

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.code_of_duty.u_tracker.ui.components.ui.CustomButton
import com.code_of_duty.u_tracker.ui.components.ui.EditTextField
import com.code_of_duty.u_tracker.ui.components.ui.FormsCard
import com.code_of_duty.u_tracker.ui.components.ui.KeyboardType

@Composable
fun RequestTokenCard (
    //viewModel: TODO(),
    onContinue: () -> Unit
) {
    val email = remember { mutableStateOf("") }
    FormsCard(
        title = "Generar código de verificación",
        editFields = listOf(
            {
                EditTextField(
                    label = "Correo electrónico",
                    value = email,
                    onValueChange = {email.value = it},
                    type = KeyboardType.Email
                )
            },
            {
                CustomButton(text = "Solicitar código") {
                    onContinue()
                }
            }
        ),
        onClick = {}
    )
}