package com.code_of_duty.u_tracker.ui.screens.cum

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.code_of_duty.u_tracker.ui.components.ui.PartialCircle
import com.code_of_duty.u_tracker.ui.theme.Typography

@Composable
fun CUMScreen (
    viewModel: CumViewModel = hiltViewModel()
) {
    val total = remember { mutableStateOf(0f) }
    val completed = remember { mutableStateOf(1f) }
    val cum = remember { mutableStateOf(0f) }
    val percent = remember { mutableStateOf(0f) }

    LaunchedEffect(key1 = viewModel.total.value, key2 = viewModel.completed.value, key3 = viewModel.cum.value) {
        total.value = viewModel.getTotal()
        completed.value = viewModel.getCompleted()
        percent.value = (completed.value / total.value) * 100f
        cum.value = viewModel.getCum()
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PartialCircle(
            completed = completed.value,
            total = total.value,
            cum = cum.value,
            scale = 1.5f,
        )
        Text(
            text = "AVANCE DE CARRERA: %.2f".format(percent.value) + "%",
            style = Typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(42.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CUMScreenPreview() {
    CUMScreen()
}