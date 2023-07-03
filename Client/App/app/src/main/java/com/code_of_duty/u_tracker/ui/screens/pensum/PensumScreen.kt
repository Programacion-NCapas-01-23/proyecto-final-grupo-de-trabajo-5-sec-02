package com.code_of_duty.u_tracker.ui.screens.pensum

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.code_of_duty.u_tracker.R
import com.code_of_duty.u_tracker.enums.PensumState
import com.code_of_duty.u_tracker.ui.components.ui.SubjectCard

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun PensumScreen (
    viewModel: PensumViewModel = hiltViewModel()
) {
    val currPensum = remember { mutableStateOf(viewModel.pensum()) }
    val loading = remember { mutableStateOf(true) }
    var correlativePressed by remember { mutableStateOf(0) }

    viewModel.getPensum()

    LaunchedEffect(viewModel.pensumStatus().value){
        when(viewModel.pensumStatus().value) {
            PensumState.DONE -> {
                currPensum.value = viewModel.pensum()
                loading.value = false
            }
            PensumState.ERROR -> {
                loading.value = false
            }
            else -> {}
        }
    }

    if(loading.value){
        CircularProgressIndicator()
    }else{
        Row(
            modifier = Modifier
                .padding(8.dp)
                .horizontalScroll(rememberScrollState(), enabled = true)
                .fillMaxSize()
        ) {
            currPensum.value = currPensum.value.sortedBy { it.orderValue }.toMutableList()
            currPensum.value.forEach { cycle ->
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .verticalScroll(rememberScrollState(), enabled = true),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {},
                        modifier = Modifier.clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null,
                            onClick = {}
                        ),
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.list_checked),
                            contentDescription = "Back",
                            modifier = Modifier.size(24.dp)
                        )
                        Text(text = cycle.name)
                    }
                    cycle.subjects.forEach { subject ->
                        Spacer(modifier = Modifier.padding(8.dp))
                        SubjectCard(
                            markCard = mutableStateOf(subject.prerequisiteID?.filter { it == correlativePressed }
                                ?.isNotEmpty() ?: false),
                            sort = subject.correlative,
                            subjectName = subject.name,
                            prerequisites = subject.prerequisiteID,
                            uv = subject.uv,
                            myModifier = Modifier.combinedClickable(
                                onClick = {},
                                onLongClick = {
                                    correlativePressed = subject.correlative
                                }
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PensumScreenPreview() {
    PensumScreen()
}