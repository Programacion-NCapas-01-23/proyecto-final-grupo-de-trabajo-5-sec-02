package com.code_of_duty.u_tracker.ui.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class TextFieldModel(
    var text: MutableState<String> = mutableStateOf(""),
    var isError: Boolean = false,
    var supportText: String = ""
)