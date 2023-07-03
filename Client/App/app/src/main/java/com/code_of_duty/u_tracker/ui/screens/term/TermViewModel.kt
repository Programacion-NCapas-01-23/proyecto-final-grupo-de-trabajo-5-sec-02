package com.code_of_duty.u_tracker.ui.screens.term

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code_of_duty.u_tracker.data.network.response.DegreesResponse
import com.code_of_duty.u_tracker.data.network.response.PersonalTermResponse
import com.code_of_duty.u_tracker.data.repositories.TermRepository
import com.code_of_duty.u_tracker.enums.AddTermStatus
import com.code_of_duty.u_tracker.enums.CommonState
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
    private var year: MutableState<ExposedDropdownModel> = mutableStateOf(ExposedDropdownModel())
    private var term: MutableState<ExposedDropdownModel> = mutableStateOf(ExposedDropdownModel())
    private var yearsList = mutableListOf<CommonInt>()
    private var termTypesList = mutableListOf<TermType>()
    private var _term: MutableList<PersonalTermResponse> = mutableListOf()
    private var termStatus: MutableState<CommonState> = mutableStateOf(CommonState.NONE)
    private var addTermStatus: MutableState<AddTermStatus> = mutableStateOf(AddTermStatus.NONE)

    fun term() = _term
    fun termStatus() = termStatus

    fun loadTermSelectsData() {
        viewModelScope.launch {
            yearsList = setYearsList(currentYears)
            termTypesList = setTermTypesList(termTypes)
        }
    }

    fun filterYears(): MutableList<CommonInt> {
        try {
            val yearsWithCount = yearsList.map { year ->
                val count = _term.count { it.year == year.value }
                year to count
            }

            yearsList = yearsWithCount.filter { it.second < 3 }.map { it.first }.toMutableList()
            return yearsList
        } catch (e: Exception) {
            /*CUANDO VEAS ESTO, YA SE QUE NO VA UN TRY CATCH*/
            return emptyList<CommonInt>().toMutableList()
        }
    }


    fun filterTermTypes(year: Int) : MutableList<TermType> {
        try {
            val filteredTermsForYear = _term.filter { it.year == year }

            val cycleTypesWithCount = termTypesList.map { termType ->
                val count = filteredTermsForYear.count { it.cycleType == termType.value }
                termType to count
            }

            termTypesList = cycleTypesWithCount.filter { it.second < 3 }.map { it.first }.toMutableList()
            return termTypesList
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList<TermType>().toMutableList()
        }
    }

    fun getYearsList(): MutableList<CommonInt> {
        return yearsList
    }

    fun setYearsList(yearsList: MutableList<CommonInt>): MutableList<CommonInt> {
        this.yearsList = yearsList
        return this.yearsList
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


    fun getTerm() {
        viewModelScope.launch {
            try {
                val token = repository.getToken()
                if (token.isNotEmpty()) {
                    _term = repository.getPersonalTerms(token).toMutableList()
                    termStatus.value = CommonState.DONE
                }
            } catch (e: Exception) {
                termStatus.value = CommonState.ERROR
            }
        }
    }
}