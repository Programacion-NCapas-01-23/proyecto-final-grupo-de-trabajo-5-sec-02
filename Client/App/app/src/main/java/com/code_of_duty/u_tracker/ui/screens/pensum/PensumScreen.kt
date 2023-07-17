package com.code_of_duty.u_tracker.ui.screens.pensum

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.code_of_duty.u_tracker.R
import com.code_of_duty.u_tracker.data.database.entities.Subject
import com.code_of_duty.u_tracker.enums.PensumState
import com.code_of_duty.u_tracker.ui.components.ui.BottomSheet
import com.code_of_duty.u_tracker.ui.components.ui.EditTextField
import com.code_of_duty.u_tracker.ui.components.ui.KeyboardType
import com.code_of_duty.u_tracker.ui.components.ui.SubjectCard
import com.code_of_duty.u_tracker.ui.components.ui.UtrackerBottomSheetScaffold
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class
)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun PensumScreen (
    viewModel: PensumViewModel = hiltViewModel()
) {
    val bsState = rememberBottomSheetScaffoldState()
    val currGrade = remember { mutableStateOf("") }
    val currSubject = remember { mutableStateOf(Subject("","",0,0,0)) }
    val scope = rememberCoroutineScope()
    val currPensum = remember { mutableStateOf(viewModel.pensum()) }
    val loading = remember { mutableStateOf(true) }
    var correlativePressed by remember { mutableStateOf(0) }

    viewModel.getPensum()

    LaunchedEffect(viewModel.pensumStatus().value){
        when(viewModel.pensumStatus().value) {
            PensumState.DONE -> {
                currPensum.value = viewModel.pensum()
                Log.d("PensumScreen", currPensum.value.toString())
                loading.value = false
            }
            PensumState.ERROR -> {
                loading.value = false
                Log.e("PensumScreen", "Error getting pensum")
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
                        .padding(8.dp),
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
                    Column(modifier = Modifier.verticalScroll(rememberScrollState(), enabled = true)){
                        cycle.subjects.forEach { subject ->
                            Spacer(modifier = Modifier.padding(8.dp))
                            SubjectCard(
                                passed = mutableStateOf(viewModel.subjectPassedState[subject.code] ?: false),
                                markCard = mutableStateOf(subject.prerequisiteID?.filter { it == correlativePressed }
                                    ?.isNotEmpty() ?: false),
                                sort = subject.correlative,
                                subjectName = subject.name,
                                prerequisites = subject.prerequisiteID,
                                uv = subject.uv,
                                myModifier = Modifier.combinedClickable(
                                    onClick = {
                                        scope.launch { bsState.bottomSheetState.expand() }
                                        currSubject.value = Subject(
                                            code = subject.code,
                                            name = subject.name,
                                            uv = subject.uv,
                                            order = subject.correlative,
                                            cycle = cycle.orderValue
                                        )
                                    },
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

        UtrackerBottomSheetScaffold(content = listOf(
            {
                Text(
                    text = currSubject.value.name,
                    modifier = Modifier.padding(24.dp)
                )
            },
            {
                EditTextField(
                    label = "Nota",
                    value = currGrade,
                    type = KeyboardType.Number
                )
            },
            {
                Button(onClick = {
                    viewModel.updateSubject(currSubject.value, currGrade.value.toFloat())
                    viewModel.subjectPassedState[currSubject.value.code] = currGrade.value.toFloat() >= 6.0f
                    currGrade.value = ""
                    scope.launch { bsState.bottomSheetState.partialExpand() }
                }) {
                    Text(text = "Guardar Nota")
                }
            }
        ), scaffoldState = bsState
        )

        /*BottomSheet(
            closeButtonLabel = "Guardar Nota",
            bottomSheetState = bottomSheetState,
            scope = scope,
            editFields = listOf(
                {
                    Text(
                        text = currSubject.value.name,
                        modifier = Modifier.padding(24.dp)
                    )
                },
                {
                    EditTextField(
                        label = "Nota",
                        value = currGrade,
                        type = KeyboardType.Number
                    )
                }
            ),
            openBottomSheet = openBottomSheet,
            hideOpenButton = true,
            skipPartiallyExpanded = skipPartiallyExpanded,
            aditionalActionOnClose = {
                viewModel.updateSubject(currSubject.value, currGrade.value.toFloat())
                currGrade.value = ""
            },
            edgeToEdgeEnabled = edgeToEdge
        )*/
    }
}

@Preview
@Composable
fun PensumScreenPreview() {
    PensumScreen()
}