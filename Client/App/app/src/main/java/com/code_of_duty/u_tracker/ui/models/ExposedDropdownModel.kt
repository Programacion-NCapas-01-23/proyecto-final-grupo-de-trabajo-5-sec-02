package com.code_of_duty.u_tracker.ui.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ExposedDropdownModel(
    var selectedIdValue : MutableState<String> = mutableStateOf(""),
    var selectedTextValue : MutableState<String> = mutableStateOf(""),
)
