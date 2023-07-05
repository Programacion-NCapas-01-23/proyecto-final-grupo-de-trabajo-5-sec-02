@file:OptIn(ExperimentalMaterial3Api::class)

package com.code_of_duty.u_tracker.ui.components.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.code_of_duty.u_tracker.ui.theme.Typography
import com.code_of_duty.u_tracker.ui.theme.UTrackerTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BottomSheet(
    openButtonLabel: String = "",
    hideOpenButton: Boolean = false,
    aditionalActionOnClose : () -> Unit = {},
    closeButtonLabel: String,
    edgeToEdgeEnabled: MutableState<Boolean> = mutableStateOf(false),
    skipPartiallyExpanded: MutableState<Boolean> = mutableStateOf(false),
    bottomSheetState: SheetState,
    scope: CoroutineScope,
    editFields: List<@Composable () -> Unit>,
    openBottomSheet: MutableState<Boolean>,
    customButton: Boolean = false,
    customButtonAction: () -> Unit = {},
    title: String = "",
    ) {
// App content
    if (customButton) {
        customButtonAction()
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .wrapContentSize(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
//        Row(
//            Modifier.toggleable(
//                value = skipPartiallyExpanded?.value ?: false,
//                role = Role.Checkbox,
//                onValueChange = { checked -> skipPartiallyExpanded?.value = checked }
//            )
//        ) {
//            Checkbox(checked = skipPartiallyExpanded?.value ?: false, onCheckedChange = null)
//            Spacer(Modifier.width(16.dp))
//            Text("Skip partially expanded State")
//        }
//        Row(
//            Modifier.toggleable(
//                value = edgeToEdgeEnabled?.value ?: false,
//                role = Role.Checkbox,
//                onValueChange = { checked -> edgeToEdgeEnabled?.value = checked }
//            )
//        ) {
//            Checkbox(checked = edgeToEdgeEnabled?.value?.not() ?: false , onCheckedChange = null)
//            Spacer(Modifier.width(16.dp))
//            Text("Toggle edge to edge enabled.")
//        }
            if (!hideOpenButton){
                Button(onClick = { openBottomSheet.value = !openBottomSheet.value }) {
                    Text(text = openButtonLabel)
                }
            }
        }
    }


// Sheet content
    if (openBottomSheet.value) {
        val windowInsets = if (edgeToEdgeEnabled?.value == true)
            WindowInsets(0) else BottomSheetDefaults.windowInsets

        ModalBottomSheet(
            onDismissRequest = { openBottomSheet.value = false },
            sheetState = bottomSheetState,
            modifier = Modifier.imePadding(),
            windowInsets = windowInsets
        ) {
            if (title.isNullOrEmpty()) {} else {
                Row(horizontalArrangement = Arrangement.Center,modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Text(text = title, style = TextStyle(
                        fontSize = Typography.titleLarge.fontSize,
                        lineHeight = Typography.titleLarge.lineHeight,
                        fontWeight = Typography.titleLarge.fontWeight,
                        letterSpacing = Typography.titleLarge.letterSpacing,
                        color = MaterialTheme.colorScheme.primary,
                        )
                    )
                }
            }
            editFields.forEach { editField ->
                editField()
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp), horizontalArrangement = Arrangement.Center) {
                Button(
                    // Note: If you provide logic outside of onDismissRequest to remove the sheet,
                    // you must additionally handle intended state cleanup, if any.
                    onClick = {
                        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                            aditionalActionOnClose()
                            if (!bottomSheetState.isVisible) {
                                openBottomSheet.value = false
                            }
                        }
                    }
                ) {
                    Text(closeButtonLabel)
                }
            }

//            var text by remember { mutableStateOf("") }
//            OutlinedTextField(value = text, onValueChange = { text = it })
//            LazyColumn {
//                items(50) {
//                    ListItem(
//                        headlineContent = { Text("Item $it") },
//                        leadingContent = {
//                            Icon(
//                                Icons.Default.Favorite,
//                                contentDescription = "Localized description"
//                            )
//                        }
//                    )
//                }
//            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun BottomSheetPreview() {

    var openBottomSheet = rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
//    var edgeToEdgeEnabled by remember { mutableStateOf(false) }
    var skipPartiallyExpanded = remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = skipPartiallyExpanded.value
    )

    val options = listOf("Ingeniería y Arquitectura", "Ciencias Sociales y Humanidades", "Derecho", "Comunicaciones y Mercadeo", "Educación", "Gestión Empresarial y Negocios")
    var expandedState = remember {
        mutableStateOf(false)
    }
    var selectedOptionTextState = remember {
        mutableStateOf(options[0])
    }
    val label = "Elige una facultad"

    val label2 = "Elige una carrera"
    val options2 = listOf("Licenciatura en Ciencias Jurídicas")
    var expandedState2 = remember{
        mutableStateOf(false)
    }
    var selectedOptionTextState2 = remember {
        mutableStateOf(options2[0])
    }
    UTrackerTheme() {
        BottomSheet(
            bottomSheetState = bottomSheetState,
            scope = scope,
            editFields = listOf(
//            { CenteredExposedDropdown(
//                label = label ,
//                options = options,
//                expanded = expandedState,
//                selectedOptionText = selectedOptionTextState
//            ) },
//            { CenteredExposedDropdown(
//                label = label2 ,
//                options = options2,
//                expanded = expandedState2,
//                selectedOptionText = selectedOptionTextState2
//            ) }
            ),
            openBottomSheet = openBottomSheet,
            openButtonLabel = "Elegir carrera",
            closeButtonLabel = "Volver al formulario"
        )
    }

}