@file:OptIn(ExperimentalMaterial3Api::class)

package com.code_of_duty.u_tracker.ui.screens.term

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.code_of_duty.u_tracker.R
import com.code_of_duty.u_tracker.data.network.response.PersonalTermResponse
import com.code_of_duty.u_tracker.enums.AddTermStatus
import com.code_of_duty.u_tracker.enums.CommonState
import com.code_of_duty.u_tracker.ui.components.term.CreateTerm
import com.code_of_duty.u_tracker.ui.components.ui.BottomSheet
import com.code_of_duty.u_tracker.ui.components.ui.CenteredExposedDropdown
import com.code_of_duty.u_tracker.ui.components.ui.CustomButton
import com.code_of_duty.u_tracker.ui.components.ui.CustomELevatedCard
import com.code_of_duty.u_tracker.ui.components.ui.DialogAlert
import com.code_of_duty.u_tracker.ui.components.ui.FAB
import com.code_of_duty.u_tracker.ui.theme.Typography
import com.code_of_duty.u_tracker.ui.theme.UTrackerTheme

@Composable
fun TermScreen (
    termViewModel: TermViewModel = hiltViewModel()
) {
    UTrackerTheme {
        val error = remember { mutableStateOf(false) }
        val currTerm = remember { mutableStateOf(termViewModel.term())}
        val loading = remember { mutableStateOf(true) }

        val yearsList = remember { mutableStateOf(termViewModel.getYearsList()) }
        val termTypesList = remember { mutableStateOf(termViewModel.getTermTypesList()) }

        val enableStateTermType = remember {mutableStateOf(false)}

        termViewModel.getTerm()
        termViewModel.loadTermSelectsData()

        LaunchedEffect(termViewModel.getAddTermStatus().value) {
            when(termViewModel.getAddTermStatus().value) {
                AddTermStatus.CREATED -> {
                    Log.d("TermScreen", "Term Created")
                    termViewModel.getTerm()
                    Log.d("TermScreen", "Term Loaded ${termViewModel.getTerm()}")
                    termViewModel.loadTermSelectsData()
                    termViewModel.setAddTermStatus(AddTermStatus.NONE)
                }
                AddTermStatus.FAILED -> {
                    error.value = true
                    loading.value = false
                    termViewModel.setAddTermStatus(AddTermStatus.NONE)
                }
                else -> {}
            }
        }

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

        /*Están quemados, pero tiene sentido*/
        //For ExposeDropDown Years
        val labelYear = "Año"
        val selectedYear = termViewModel.getYearId()
        val selectedNameYear = termViewModel.getYearText()

        //For ExposeDropDown TermType
        val labelTermType = "Tipo"
        val selectedIdTermType = termViewModel.getTermTypeId()
        val selectedNameTermType = termViewModel.getTermTypeText()

        //Enable termType Dropdown

        LaunchedEffect(yearsList.value) {
            termViewModel.setTermTypeId("")
            termViewModel.setTermTypeText("")
            termViewModel.setTermTypesStatus(CommonState.NONE)

            if(termViewModel.getYearId().value != ""){
                termTypesList.value = termViewModel.filterTermTypesForYear(termViewModel.getYearId().value.toInt())

                if(termViewModel.getTermTypesStatus().value === CommonState.DONE){
                    enableStateTermType.value = true
                }
            }
        }

        //For BottomSheet
        val openBottomSheetTerm = rememberSaveable { mutableStateOf(false) }
        val scopeTerm = rememberCoroutineScope()
        val bottomSheetState = rememberModalBottomSheetState()
        val closeButtomLabel = "Volver a ciclos"


        val fABWithModal = @Composable {

            BottomSheet(
                closeButtonLabel = closeButtomLabel,
                bottomSheetState = bottomSheetState,
                scope = scopeTerm,
                editFields = listOf {
                    CreateTerm(
                        content = listOf(
                            {
                                CenteredExposedDropdown(
                                    individualCentered = false,
                                    label = labelYear,
                                    options = yearsList.value,
                                    selectedIdValue = selectedYear,
                                    selectedNameValue = selectedNameYear,
                                    optionNameProvider = { option -> option.value.toString()},
                                    optionIdProvider = { option -> option.value.toString()},
                                    width = 125.dp,
                                    onItemSelected = { selectedId ->
                                        // Aquí puedes acceder al valor de selectedId y hacer lo que necesites con él
                                        Log.d("TermScreen", "Selected Id: $selectedId")
                                        termViewModel.setYearText(selectedNameYear.value)
                                        termViewModel.setYearId(selectedId)
                                    }
                                )
                            },
                            {
                                CenteredExposedDropdown(
                                    individualCentered = false,
                                    label = labelTermType,
                                    options = termViewModel.getTermTypesList(),
                                    selectedIdValue = selectedIdTermType,
                                    selectedNameValue = selectedNameTermType,
                                    optionNameProvider = { option -> option.name },
                                    optionIdProvider = { option -> option.value.toString()},
                                    width = 175.dp,
                                    onItemSelected = { selectedId ->
                                        // Aquí puedes acceder al valor de selectedId y hacer lo que necesites con él
                                        Log.d("TermScreen", "Selected Id: ${selectedIdTermType.value}")
                                        termViewModel.setTermTypeText(selectedNameTermType.value)
                                        termViewModel.setTermTypeId(selectedIdTermType.value)
                                    }
                                )
                            },
                            {
                                CustomButton(text = "Crear ciclo", loading = loading.value) {
                                    loading.value = true
                                    termViewModel.addTerm()
                                }
                            }
                        )
                    )
                },
                openBottomSheet = openBottomSheetTerm,
                customButton = true,
                title = "Agregar ciclo"
            )

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

            if (error.value) {
                DialogAlert(
                    title = "Error",
                    message = "Campos incorrectos",
                    onConfirm = {
                        error.value = false
                    },
                    needCancel = false
                )
            }
        }

        if(loading.value){
            CircularProgressIndicator()
        } else {
            if(currTerm.value.isEmpty()){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "No hay ciclos creados",
                        style = TextStyle(
                            fontSize = Typography.displaySmall.fontSize,
                            lineHeight = Typography.displaySmall.lineHeight,
                            fontWeight = Typography.displaySmall.fontWeight,
                            letterSpacing = Typography.displaySmall.letterSpacing,
                            color = MaterialTheme.colorScheme.onSurface,
                        ),
                    )
                    fABWithModal()
                }
            } else {

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
                                        /*TODO: LIST ITEMS WITH COMPONENT LISTITEM*/
                                    }
                                }
                            )
                        }
                    }
                }

                /**/
                fABWithModal()
            }
        }
    }
}

@Preview
@Composable
fun TermScreenPreview () {
    TermScreen();
}