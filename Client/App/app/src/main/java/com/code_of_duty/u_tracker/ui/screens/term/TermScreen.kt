@file:OptIn(ExperimentalMaterial3Api::class)

package com.code_of_duty.u_tracker.ui.screens.term

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.code_of_duty.u_tracker.R
import com.code_of_duty.u_tracker.data.network.response.PersonalTermResponse
import com.code_of_duty.u_tracker.enums.CommonState
import com.code_of_duty.u_tracker.ui.components.term.CreateTerm
import com.code_of_duty.u_tracker.ui.components.ui.BottomSheet
import com.code_of_duty.u_tracker.ui.components.ui.CenteredExposedDropdown
import com.code_of_duty.u_tracker.ui.components.ui.CustomELevatedCard
import com.code_of_duty.u_tracker.ui.components.ui.FAB
import com.code_of_duty.u_tracker.ui.models.currentYears
import com.code_of_duty.u_tracker.ui.models.termTypes
import com.code_of_duty.u_tracker.ui.theme.UTrackerTheme

@Composable
fun TermScreen (termViewModel: TermViewModel = hiltViewModel()) {
    UTrackerTheme {



        val currTerm = remember { mutableStateOf(termViewModel.term())}
        val loading = remember { mutableStateOf(true) }

        val yearsList = remember { mutableStateOf(termViewModel.getYearsList()) }
        val termTypesList = remember { mutableStateOf(termViewModel.getTermTypesList()) }

        val enableStateTermType = remember { mutableStateOf(false) }

        termViewModel.getTerm()
        termViewModel.loadTermSelectsData()

        LaunchedEffect(termViewModel.termStatus().value) {
            when (termViewModel.termStatus().value) {
                CommonState.DONE -> {
                    currTerm.value = termViewModel.term()
                    loading.value = false
                }
                CommonState.ERROR -> {
                    loading.value = false
                }
                else -> {}
            }
        }

        //Enable termType Dropdown

        LaunchedEffect(termViewModel.getYearId().value) {
            termViewModel.setTermTypeId("")
            termViewModel.setTermTypeText("")
            enableStateTermType.value = false

            if(termViewModel.getYearId().value != ""){
                termTypesList.value = termViewModel.filterTermTypes(termViewModel.getYearId().value.toInt())
                enableStateTermType.value = true
            }
        }

        if(loading.value){
            CircularProgressIndicator()
        } else {

            //For BottomSheet
            val openBottomSheetTerm = rememberSaveable { mutableStateOf(false) }
            val scopeTerm = rememberCoroutineScope()
            val bottomSheetState = rememberModalBottomSheetState()
            val closeButtomLabel = "Volver a ciclos"

            /*Están quemados, pero tiene sentido*/
            //For ExposeDropDown Years
            val labelYear = "Año"
            val selectedYear = termViewModel.getYearId()
            val selectedNameYear = termViewModel.getYearText()

            //For ExposeDropDown TermType
            val labelTermType = "Tipo"
            val selectedIdTermType = termViewModel.getTermTypeId()
            val selectedNameTermType = termViewModel.getTermTypeText()

            BottomSheet(
                closeButtonLabel = closeButtomLabel,
                bottomSheetState = bottomSheetState,
                scope = scopeTerm,
                editFields = listOf(
                    {
                        CreateTerm(
                            content = listOf(
                                {
                                    CenteredExposedDropdown(
                                        individualCentered = false,
                                        label = labelYear,
                                        options = yearsList.value,
                                        selectedIdValue = selectedYear,
                                        selectedNameValue = selectedNameYear,
                                        optionNameProvider ={option -> option.value.toString()}
                                        ,width = 125.dp
                                    )
                                },
                                {
                                    CenteredExposedDropdown(
                                        individualCentered = false,
                                        label = labelTermType,
                                        options = termTypesList.value,
                                        selectedIdValue = selectedIdTermType,
                                        selectedNameValue = selectedNameTermType,
                                        optionNameProvider = {option -> option.name},
                                        enableState = enableStateTermType,
                                        width = 175.dp
                                    )
                                },
                            )
                        )
                    },
                    {},
                ),
                openBottomSheet = openBottomSheetTerm,
                customButton = true,
                title = "Agregar ciclo"
            )

           LazyColumn(modifier = Modifier
               .fillMaxSize()
               .padding(16.dp)) {

               currTerm.value = currTerm.value.sortedWith(
                   compareByDescending<PersonalTermResponse> { it.year }
                       .thenByDescending { it.cycleType }
               ).toMutableList()

               currTerm.value.forEach { term ->
                    item {
                        CustomELevatedCard(
                            title = "${term.cycleType + 1} de ${term.year}",
                            editFields = listOf{
                                term.subjects?.forEach { subject ->
                                    Log.d("TermScreen", subject.toString())
                                    /*TODO: LIST ITEMS*/
                                }
                            }
                        )
                    }
               }
           }
            FAB(
                onClick = {
                    openBottomSheetTerm.value = true
                    yearsList.value = termViewModel.filterYears()
                },
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.add),
                        contentDescription = "FAB Icon",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                },
                background = MaterialTheme.colorScheme.primaryContainer,
            )
        }
    }
}

@Preview
@Composable
fun TermScreenPreview () {
    TermScreen();
}
