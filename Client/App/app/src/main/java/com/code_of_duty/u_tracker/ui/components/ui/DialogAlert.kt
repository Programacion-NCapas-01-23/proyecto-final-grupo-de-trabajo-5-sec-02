package com.code_of_duty.u_tracker.ui.components.ui

import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DialogAlert(
    title: String,
    message: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit = {},
    needCancel: Boolean = true
) {
    AlertDialog(
        onDismissRequest = onConfirm,
        title = { Text(text = title) },
        text = { Text(text = message) },
        confirmButton = {
            Button(
                onClick = onConfirm
            ) {
                Text(text = "Confirmar")
            }
        },
        dismissButton = {
            if (needCancel) {
                Button(
                    onClick = onCancel
                ) {
                    Text(text = "Cancelar")
                }
            }
        }
    )
}

@Preview
@Composable
fun DialogAlertPreview() {
    DialogAlert(
        title = "Title",
        message = "Message",
        onConfirm = {},
        needCancel = false,
    )
}