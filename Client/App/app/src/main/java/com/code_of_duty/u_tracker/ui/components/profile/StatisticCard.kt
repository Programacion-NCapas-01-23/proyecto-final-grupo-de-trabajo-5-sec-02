package com.code_of_duty.u_tracker.ui.components.profile

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.code_of_duty.u_tracker.ui.components.ui.PartialCircle
import com.code_of_duty.u_tracker.ui.screens.cum.CumViewModel
import com.code_of_duty.u_tracker.ui.theme.Typography
import com.code_of_duty.u_tracker.ui.theme.UTrackerTheme

enum class cardType {
    CUM,
    CAREER,
    APPROVED,
    UVS,
}

@Composable
fun StatisticCard(
    markCard: MutableState<Boolean> = mutableStateOf(false),
    myModifier: Modifier = Modifier,
    label: String,
    viewModel: CumViewModel = hiltViewModel(),
    cardType: Int,
    approvedSubjects: Int?,
) {
    val transition = remember { Animatable(initialValue = 0f) }

    val total = remember { mutableStateOf(0f) }
    val completed = remember { mutableStateOf(1f) }
    val cum = remember { mutableStateOf(0f) }
    val percent = remember { mutableStateOf(0f) }

    LaunchedEffect(
        key1 = viewModel.total.value,
        key2 = viewModel.completed.value,
        key3 = viewModel.cum.value
    ) {
        total.value = viewModel.getTotal()
        completed.value = viewModel.getCompleted()
        percent.value = (completed.value / total.value) * 100f
        cum.value = viewModel.getCum()
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (!markCard.value) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        modifier = myModifier
            .width(180.dp)
            .height(200.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (cardType == 1) {
                PartialCircle(
                    completed = completed.value,
                    total = total.value,
                    cum = cum.value,
                    scale = 1.5f,
                )
                Text(
                    text = "CUM",
                    textAlign = TextAlign.End,
                )
            }
            if (cardType == 2) {
                PartialCircle(
                    completed = completed.value,
                    total = total.value,
                    cum = percent.value,
                    scale = 1.5f,
                )
                Text(
                    text = "Avance de Carrera",
                    textAlign = TextAlign.End,
                )
            }
            if (cardType == 3) {
                PartialCircle(
                    completed = completed.value,
                    total = total.value,
                    cum = approvedSubjects as Float,
                    scale = 1.5f,
                )
                Text(
                    text = "Materias Aprobadas",
                    textAlign = TextAlign.End,
                )
            }
            if (cardType == 4) {
                PartialCircle(
                    completed = completed.value,
                    total = total.value,
                    cum = percent.value,
                    scale = 1.5f,
                )
                Text(
                    text = "UVs Ganadas",
                    textAlign = TextAlign.End,
                )
            }
        }
    }
}

@Preview
@Composable
fun StatisticCardPreview() {
    UTrackerTheme {
        StatisticCard(
            label = "CUM",
            cardType = 1,
            approvedSubjects = null,
        )
    }
}