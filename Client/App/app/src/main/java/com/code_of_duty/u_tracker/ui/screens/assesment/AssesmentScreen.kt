package com.code_of_duty.u_tracker.ui.screens.assesment

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.code_of_duty.u_tracker.R
import com.code_of_duty.u_tracker.data.database.entities.Assesment
import com.code_of_duty.u_tracker.ui.components.ui.CustomTab
import com.code_of_duty.u_tracker.ui.components.ui.EditTextField
import com.code_of_duty.u_tracker.ui.components.ui.ExpandableCard
import com.code_of_duty.u_tracker.ui.components.ui.FAB
import com.code_of_duty.u_tracker.ui.models.SubjectWithAssesment
import com.code_of_duty.u_tracker.ui.theme.Typography

@SuppressLint("UnrememberedMutableState")
@Composable
fun AssesmentScreen () {
    val indexTab = remember {
        mutableStateOf(0)
    }
    val tabs = listOf(
        "Notas","Porcentajes"
    )
    //temp
    val subjects = listOf(
        SubjectWithAssesment(code = "1", name = "Calculo I", assesments = listOf(
            Assesment(name = "Parcial 1", grade = "4.5", percentage = "30", subject = "1"),
            Assesment(name = "Parcial 2", grade = "4.5", percentage = "30", subject = "1"),
            Assesment(name = "Parcial 3", grade = "4.5", percentage = "30", subject = "1"),
        )),
        SubjectWithAssesment(code = "2", name = "Calculo II", assesments = listOf(
            Assesment(name = "Parcial 1", grade = "4.5", percentage = "30", subject = "2"),
            Assesment(name = "Parcial 2", grade = "4.5", percentage = "30", subject = "2"),
            Assesment(name = "Parcial 3", grade = "4.5", percentage = "30", subject = "2"),
        )),
        SubjectWithAssesment(code = "3", name = "Calculo III", assesments = listOf(
            Assesment(name = "Parcial 1", grade = "4.5", percentage = "30", subject = "3"),
            Assesment(name = "Parcial 2", grade = "4.5", percentage = "70", subject = "3")
        )),
    )

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
            items(items = subjects, key = {subject -> subject.code}) { subject ->
                val assesments = remember { mutableStateListOf(*subject.assesments.toTypedArray()) }
                val itemsInfo = mutableListOf<@Composable () -> Unit>()
                assesments.forEachIndexed { index, assesment ->
                    itemsInfo.add {
                        Row(modifier = Modifier.fillMaxSize() ,horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                            EditTextField(
                                label = "evaluacion",
                                value = mutableStateOf(assesment.name),
                                float = 0.6f,
                                onChangeValue = { newValue -> assesments[index] = assesment.copy(name = newValue) },
                            )
                            TextField(
                                value = if(indexTab.value == 0) assesment.grade else assesment.percentage,
                                onValueChange = { newValue ->
                                    if(indexTab.value == 0) assesments[index] = assesment.copy(grade = newValue)
                                    else assesments[index] = assesment.copy(percentage = newValue)},
                                modifier = Modifier.fillMaxWidth(0.40f),
                                textStyle = Typography.labelSmall
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
        onClick = { /*TODO*/ },
        background = MaterialTheme.colorScheme.primaryContainer
    ) {
        Icon(
            painter = painterResource(id = R.drawable.add),
            contentDescription = "Add",
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Preview
@Composable
fun AssesmentScreenPreview() {
    AssesmentScreen()
}