@file:OptIn(ExperimentalMaterial3Api::class)

package com.code_of_duty.u_tracker.ui.components.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.code_of_duty.u_tracker.ui.models.Provisional
import com.code_of_duty.u_tracker.ui.models.careers
import com.code_of_duty.u_tracker.ui.models.faculties
import com.code_of_duty.u_tracker.ui.theme.UTrackerTheme

@Composable
fun <T: Any> CenteredExposedDropdown (
    label: String,
    options: MutableList<T>,
    enableState: MutableState<Boolean> = mutableStateOf(true),
    selectedIdValue: MutableState<String>,
    selectedNameValue: MutableState<String>,
    optionNameProvider: (T) -> String,
    optionIdProvider: (T) -> String
) {
    //VARIABLES
    val expandedState = remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExposedDropdownMenuBox(
            expanded = expandedState.value,
            onExpandedChange = {
                if(enableState.value) expandedState.value = !expandedState.value},
            modifier = Modifier.let {
                if (!enableState.value) {
                    it.clickable(enabled = enableState.value, onClick = {})
                } else {
                    it
                }
            }
                .fillMaxWidth()
                .padding(8.dp)
                .wrapContentSize(Alignment.Center),
        ) {
            TextField(
                // The `menuAnchor` modifier must be passed to the text field for correctness.
                modifier = Modifier
                    .menuAnchor(),
                readOnly = true,
                value = selectedNameValue.value,
                onValueChange = {},
                label = { Text(label) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedState.value) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent),
                singleLine = true,
                maxLines = 1,
                shape = RoundedCornerShape(16.dp),
            )
            ExposedDropdownMenu(
                expanded = expandedState.value,
                onDismissRequest = { expandedState.value = false },
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(optionNameProvider(selectionOption)) },
                        onClick = {
                            selectedNameValue.value = optionNameProvider(selectionOption)
                            selectedIdValue.value = optionIdProvider(selectionOption)
                            expandedState.value = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
        Row (horizontalArrangement = Arrangement.Center) {
            Text(text = "ID: ${selectedIdValue.value}")
        }
    }
}

@Preview
@Composable
fun ExposedDropdownPreview() {

    var careersList = mutableListOf<Provisional>()
    var facultiesList = mutableListOf<Provisional>()

    facultiesList = faculties
    careersList = careers.filter { it.faculty == "2" }.toMutableList()

    val enableState = remember {
        mutableStateOf(true)
    }

    val selectedIdValue = remember {
        mutableStateOf("")
    }

    val selectedNameValue = remember {
        mutableStateOf("")
    }

    val label = "Carrera"
    UTrackerTheme() {
        CenteredExposedDropdown(label, careersList, enableState, selectedIdValue, selectedNameValue,{option -> option.name}, {option -> option.id})
    }
}