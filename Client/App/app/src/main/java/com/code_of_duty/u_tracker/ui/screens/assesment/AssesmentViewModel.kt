package com.code_of_duty.u_tracker.ui.screens.assesment

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code_of_duty.u_tracker.data.database.entities.Assesment
import com.code_of_duty.u_tracker.data.database.entities.Subject
import com.code_of_duty.u_tracker.data.repositories.AssesmentRepository
import com.code_of_duty.u_tracker.ui.models.SubjectWithAssesment
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AssesmentViewModel @Inject constructor(
    private val repository: AssesmentRepository
): ViewModel(){
    val subject = mutableStateListOf<SubjectWithAssesment>(
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
    var subjects = listOf<Subject>()

    fun getSubjectWithoutGrade(){
        viewModelScope.launch {
            subjects = repository.getSubjectWithoutGrade()
        }
    }
}