package com.code_of_duty.u_tracker.ui.screens.assesment

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.code_of_duty.u_tracker.R
import com.code_of_duty.u_tracker.data.database.entities.Assesment
import com.code_of_duty.u_tracker.ui.components.ui.CustomTab
import com.code_of_duty.u_tracker.ui.components.ui.EditTextField
import com.code_of_duty.u_tracker.ui.components.ui.ExpandableCard
import com.code_of_duty.u_tracker.ui.components.ui.FAB
import com.code_of_duty.u_tracker.ui.components.ui.KeyboardType
import com.code_of_duty.u_tracker.ui.components.ui.UtrackerBottomSheetScaffold
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState", "MutableCollectionMutableState")
@Composable
fun AssesmentScreen (viewModel: AssesmentViewModel = hiltViewModel()) {
    val indexTab = remember { mutableStateOf(0) }
    val tabs = listOf("Notas","Porcentajes")
    val subjects = remember { mutableStateOf(viewModel.subject) }
    val bsState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = indexTab.value) {
            tabs.forEachIndexed { index, tab ->
                CustomTab(
                    selected = remember { mutableStateOf(indexTab.value == index) },
                    onClick = {
                        indexTab.value = index
                    },
                    title = tab,
                    icon = when (index) {
                        0 -> R.drawable.grade
                        1 -> R.drawable.percent
                        else -> R.drawable.check_circle
                    }
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ){
            items(items = subjects.value, key = {subject -> subject.code}) { subject ->
                val assesments = remember { mutableStateListOf(*subject.assesments.toTypedArray()) }
                val itemsInfo = mutableListOf<@Composable () -> Unit>()
                assesments.forEachIndexed { index, assesment ->
                    itemsInfo.add {
                        Row(modifier = Modifier.fillMaxSize() ,horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                            EditTextField(
                                value = mutableStateOf(assesment.name),
                                float = 0.6f,
                                padding = 8.dp,
                                onChangeValue = { newValue -> assesments[index] = assesment.copy(name = newValue) },
                            )
                            EditTextField(
                                value = mutableStateOf(if(indexTab.value == 0) assesment.grade else assesment.percentage),
                                onChangeValue = { newValue ->
                                    if(indexTab.value == 0) assesments[index] = assesment.copy(grade = newValue)
                                    else assesments[index] = assesment.copy(percentage = newValue)},
                                padding = 2.dp,
                                textStyle = TextStyle(fontSize = 15.sp, textAlign = TextAlign.Center),
                                float = 0.4f,
                                type = KeyboardType.Number
                            )
                            FAB(onClick = {
                                assesments.removeAt(index)
                            }, shape = RoundedCornerShape(50)) {
                                Icon(
                                    painter = painterResource(id = R.drawable.delete),
                                    contentDescription = "delete",
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }

                ExpandableCard(
                    title = subject.name,
                    info = itemsInfo,
                    onAction = {
                        val newAssesment = Assesment(name = "Nueva evaluacion", grade = "0.0", percentage = "0", subject = subject.code)
                        assesments.add(newAssesment)
                        Log.d("AssesmentScreen", "onAction: ${subject.assesments}")
                    }
                )
            }
        }
    }
    FAB(
        onClick = {
           //desplegar un bottom sheet para agregar una nueva materia
            scope.launch {
                bsState.bottomSheetState.expand()
            }
        },
        background = MaterialTheme.colorScheme.primaryContainer
    ) {
        Icon(
            painter = painterResource(id = R.drawable.add),
            contentDescription = "Add",
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }

    UtrackerBottomSheetScaffold(
        scaffoldState = bsState,
        content = listOf(
            {
                Text(text ="Agregar materia")
            },
            {
                Text(text ="retirar materia")
            }
        )
    )
}

@Preview
@Composable
fun AssesmentScreenPreview() {
    AssesmentScreen()
}