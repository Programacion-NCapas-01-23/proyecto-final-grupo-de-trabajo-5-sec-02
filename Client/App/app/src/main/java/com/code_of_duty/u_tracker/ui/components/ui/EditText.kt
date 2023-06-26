package com.code_of_duty.u_tracker.ui.components.ui

import android.util.Patterns
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType as KeyboardType3

@Composable
fun EditTextField(
    label: String,
    value: MutableState<String>,
    onValueChange: (String) -> Unit,
    icon: @Composable (() -> Unit)? = null,
    type: KeyboardType = KeyboardType.Text,
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
            .fillMaxWidth()
            .padding(8.dp),
        textStyle = TextStyle(color = MaterialTheme3.colorScheme.onSurface),
        leadingIcon = icon,
        singleLine = true,
        maxLines = 1,
        isError = when(type) {
            KeyboardType.Email -> !Patterns.EMAIL_ADDRESS.matcher(value.value).matches()
            else -> false
        },
        colors = textFieldColors(
            backgroundColor = MaterialTheme3.colorScheme.surfaceVariant,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(16.dp),
        keyboardOptions = when(type) {
            KeyboardType.Text -> KeyboardType.Text.keyboardType
            KeyboardType.Number -> KeyboardType.Number.keyboardType
            KeyboardType.Email -> KeyboardType.Email.keyboardType
            KeyboardType.Password -> KeyboardType.Password.keyboardType
        },
        visualTransformation = when(type){
            KeyboardType.Password -> PasswordVisualTransformation()
            else -> VisualTransformation.None
        }
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
        onValueChange = {},
    )
}