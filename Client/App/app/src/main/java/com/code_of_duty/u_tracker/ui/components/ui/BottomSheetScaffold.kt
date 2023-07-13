package com.code_of_duty.u_tracker.ui.components.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UtrackerBottomSheetScaffold(
    scaffoldState: BottomSheetScaffoldState,
    content: List<@Composable () -> Unit>
) {
    val peekHeight = remember { mutableStateOf(0.dp) }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                content.forEach {
                    it()
                }
            }
        },
        sheetPeekHeight = peekHeight.value,
        modifier = Modifier
            .imePadding()
    ) {
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun BottomSheetScaffoldPreview(){
    /*UtrackerBottomSheetScaffold(scaffoldState = BottomSheetScaffoldState, content = listOf(
        {
            Text(text = "hello")
        },
        {
            Text(text = "hello")
        },
        {
            Text(text = "hello")
        },
        {
            Text(text = "hello")
        }
    ))*/
}