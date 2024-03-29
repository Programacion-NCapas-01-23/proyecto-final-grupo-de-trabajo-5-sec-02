package com.code_of_duty.u_tracker.ui.screens.term

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code_of_duty.u_tracker.data.network.response.DegreesResponse
import com.code_of_duty.u_tracker.data.network.response.PersonalTermResponse
import com.code_of_duty.u_tracker.data.repositories.TermRepository
import com.code_of_duty.u_tracker.enums.AddTermStatus
import com.code_of_duty.u_tracker.enums.CommonState
import com.code_of_duty.u_tracker.enums.DeleteTermStatus
import com.code_of_duty.u_tracker.ui.models.CommonInt
import com.code_of_duty.u_tracker.ui.models.ExposedDropdownModel
import com.code_of_duty.u_tracker.ui.models.TermType
import com.code_of_duty.u_tracker.ui.models.currentYears
import com.code_of_duty.u_tracker.ui.models.termTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TermViewModel @Inject constructor(
    private val repository: TermRepository
) : ViewModel() {
    //PARA LOS EXPOSED DROPDOWNS DE ADD TERM
    private var year: MutableState<ExposedDropdownModel> = mutableStateOf(ExposedDropdownModel())
    private var term: MutableState<ExposedDropdownModel> = mutableStateOf(ExposedDropdownModel())

    //MODELOS DE LAS LISTAS
    private var yearsList = mutableListOf<CommonInt>()
    private var termTypesList = mutableListOf<TermType>()

    //LISTA QUE TRAE LOS CICLOS PERSONALES
    private var _term: MutableList<PersonalTermResponse> = mutableListOf()

    //STATUS PARA LAS ACCIONES DEL CRUD
    private var termStatus: MutableState<CommonState> = mutableStateOf(CommonState.NONE)
    private var addTermStatus: MutableState<AddTermStatus> = mutableStateOf(AddTermStatus.NONE)
    private var deleteTermStatus: MutableState<DeleteTermStatus> = mutableStateOf(DeleteTermStatus.NONE)
    private var termCycleStatus: MutableState<CommonState> = mutableStateOf(CommonState.NONE)

    fun term() = _term
    fun termStatus() = termStatus

    fun loadTermSelectsData() {
        viewModelScope.launch {
            yearsList = setYearsList(currentYears)
            termTypesList = setTermTypesList(termTypes)
        }
    }

    private fun getForDB(){
        viewModelScope.launch {
            //val personalTerms = repository.getPersonalTerms()

        }
    }

    fun filterYears(): MutableList<CommonInt> {
        val yearsWithCount = yearsList.map { year ->
            val count = _term.count { it.year == year.value }
            year to count
        }

        setTermTypesStatus(CommonState.DONE)
        yearsList = yearsWithCount.filter { it.second < 3 }.map { it.first }.toMutableList()
        return yearsList
    }

    fun filterTermTypesForYear(year: Int): MutableList<TermType> {
        val filteredTermsForYear = _term.filter { it.year == year }
        Log.d("TermViewModel", "TermTypesList1: $filteredTermsForYear")

        val filteredTermTypes = termTypesList.filter { termType ->
            val count = filteredTermsForYear.count { it.cycleType == termType.value }
            count < 3 && count == 0
        }
        Log.d("TermViewModel", "TermTypesList2: $filteredTermTypes")

        termTypesList.clear()
        termTypesList.addAll(filteredTermTypes)

        Log.d("TermViewModel", "TermTypesList3: $termTypesList")

        setTermTypesStatus(CommonState.DONE)
        return termTypesList
    }
    fun getYearsList(): MutableList<CommonInt> {
        return yearsList
    }

    fun setYearsList(yearsList: MutableList<CommonInt>): MutableList<CommonInt> {
        this.yearsList = yearsList
        return this.yearsList
    }

    fun getTermTypesStatus(): MutableState<CommonState> {
        return termCycleStatus
    }

    fun setTermTypesStatus(status: CommonState){
        this.termCycleStatus.value = status
    }


    fun getTermTypesList(): MutableList<TermType> {
        return termTypesList
    }

    fun setTermTypesList(termTypesList: MutableList<TermType>): MutableList<TermType> {
        this.termTypesList = termTypesList
        return this.termTypesList
    }

    //YEAR
    fun getYearId(): MutableState<String> {
        return year.value.selectedIdValue
    }

    fun setYearId(yearId: String) {
        this.year.value.selectedIdValue.value = yearId
    }

    fun getYearText(): MutableState<String> {
        return year.value.selectedTextValue
    }

    fun setYearText(yearText: String){
        this.year.value.selectedTextValue.value = yearText
    }

    //TERMTYPE
    fun getTermTypeId(): MutableState<String> {
        return term.value.selectedIdValue
    }

    fun setTermTypeId(termTypeId: String) {
        this.term.value.selectedIdValue.value = termTypeId
    }

    fun getTermTypeText(): MutableState<String> {
        return term.value.selectedTextValue
    }

    fun setTermTypeText(termTypeText: String){
        this.term.value.selectedTextValue.value = termTypeText
    }

    fun getAddTermStatus(): MutableState<AddTermStatus> {
        return addTermStatus
    }

    fun setAddTermStatus(status: AddTermStatus) {
        this.addTermStatus.value = status
    }


    fun getTerm() {
        viewModelScope.launch {
            try {
                val token = repository.getToken()
                if (token.isNotEmpty()) {
                    Log.d("TermViewModel", "Entro al if")
                    _term = repository.getPersonalTerms(token).toMutableList()

                    Log.d("TermViewModel", "getTerm: ${_term}")
                    termStatus.value = CommonState.DONE
                }
            } catch (e: Exception) {
                Log.e("TermViewModel", "getTerm: ${e.message}", e)
                termStatus.value = CommonState.ERROR
            }
        }
    }

    fun addTerm() {
        viewModelScope.launch {
            try {
                val token = repository.getToken()
                if (token.isNotEmpty()) {
                    Log.d("TermViewModel", "addTerm: $token ${getTermTypeId().value} ${getYearText().value}")
                    val addTermResponse = repository.addPersonalTerm(
                        token = token,
                        cycleType =  getTermTypeId().value.toInt(),
                        year =  getYearId().value.toInt()
                    )
                    addTermStatus.value = AddTermStatus.CREATED
                }
            } catch (e: Exception) {
                addTermStatus.value = AddTermStatus.FAILED
            }
        }
    }

    fun setCurrentTerm(id: String) {
        viewModelScope.launch {
            repository.deleteCurrentTerm()
            repository.setCurrentTerm(id)
        }
    }
}