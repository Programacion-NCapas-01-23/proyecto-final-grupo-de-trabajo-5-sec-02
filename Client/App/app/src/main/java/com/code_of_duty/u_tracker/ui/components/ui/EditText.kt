package com.code_of_duty.u_tracker.ui.components.ui

import android.util.Patterns
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme as MaterialTheme3

@Composable
fun EditTextField(
    label: String,
    value: MutableState<String>,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    isEmail: Boolean = false,
    icon: @Composable (() -> Unit)? = null
) {
    TextField(
        value = value.value,
        onValueChange = {
            onValueChange(it)
            value.value = it
        },
        label = {
            Text(
                text = label,
                color = MaterialTheme3.colorScheme.onSurface,
            )
        },
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .padding(8.dp),
        textStyle = TextStyle(color = MaterialTheme3.colorScheme.onSurface),
        leadingIcon = icon,
        singleLine = true,
        maxLines = 1,
        isError = isEmail && !Patterns.EMAIL_ADDRESS.matcher(value.value).matches(),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        colors = textFieldColors(
            backgroundColor = MaterialTheme3.colorScheme.surfaceVariant,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Preview
@Composable
fun EditTextPreview() {
    val value = remember {
        mutableStateOf("00271419")
    }
    EditTextField(
        label = "carnet",
        value = value,
        onValueChange = {},
    )
}