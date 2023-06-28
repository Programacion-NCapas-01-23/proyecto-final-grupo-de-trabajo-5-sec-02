package com.code_of_duty.u_tracker.ui.components.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme as MaterialTheme3
import androidx.compose.ui.text.input.KeyboardType as KeyboardType3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTextField(
    label: String,
    value: MutableState<String>,
    icon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    supportText: String? = null,
    type: KeyboardType = KeyboardType.Text,
) {
    TextField(
        value = value.value,
        onValueChange = { value.value = it },
        label = { Text(label) },
        supportingText = {
            Text(text = supportText ?: "")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        leadingIcon = icon,
        color = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = MaterialTheme3.colorScheme.surface,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent),
        isError = isError,
        keyboardOptions = when(type) {
            KeyboardType.Text -> KeyboardType.Text.keyboardType
            KeyboardType.Number -> KeyboardType.Number.keyboardType
            KeyboardType.Email -> KeyboardType.Email.keyboardType
            KeyboardType.Password -> KeyboardType.Password.keyboardType
        },
        shape = RoundedCornerShape(16.dp),
        visualTransformation = when(type) {
            KeyboardType.Password -> PasswordVisualTransformation()
            else -> VisualTransformation.None
        },
    )
}

sealed class KeyboardType() {
    object Text : KeyboardType() {
        val keyboardType = KeyboardOptions.Default.copy(keyboardType = KeyboardType3.Text)
    }

    object Number : KeyboardType() {
        val keyboardType = KeyboardOptions.Default.copy(keyboardType = KeyboardType3.Number)
    }

    object Email : KeyboardType() {
        val keyboardType = KeyboardOptions.Default.copy(keyboardType = KeyboardType3.Email)
    }
    object Password : KeyboardType() {
        val keyboardType = KeyboardOptions.Default.copy(keyboardType = KeyboardType3.Password)
    }
}

@Preview
@Composable
fun EditTextPreview() {
    val value = remember {
        mutableStateOf("")
    }
    EditTextField(
        label = "carnet",
        value = value,
        supportText = "Error",
        type = KeyboardType.Number
    )
}