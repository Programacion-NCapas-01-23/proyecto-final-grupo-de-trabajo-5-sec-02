package com.code_of_duty.u_tracker.ui.screens.pensum

import com.code_of_duty.u_tracker.data.database.entities.Cycle as CycleEntity
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code_of_duty.u_tracker.data.database.entities.Prerequisite
import com.code_of_duty.u_tracker.data.database.entities.Subject
import com.code_of_duty.u_tracker.data.repositories.PensumRepository
import com.code_of_duty.u_tracker.enums.PensumState
import com.code_of_duty.u_tracker.data.network.response.IdealTermResponse
import com.code_of_duty.u_tracker.data.network.response.SubjectsFromTermResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PensumViewModel @Inject constructor(
    private val repository: PensumRepository
) : ViewModel(){
    //TODO: Implement PensumViewModel
    private var _pensum: MutableList<IdealTermResponse> = mutableListOf()
    private var pensumStatus: MutableState<PensumState> = mutableStateOf(PensumState.NONE)

    fun pensum() = _pensum
    fun pensumStatus() = pensumStatus

    fun getPensum(){
        viewModelScope.launch {
            val cycles = repository.getCycles()
            if (cycles.isEmpty()) {
                getPensumFromServer()
                return@launch
            }
            cycles.forEach{ cycle ->
                val subjects = repository.getSubjects(cycle.orderValue)
                val subjectsResponse = mutableListOf<SubjectsFromTermResponse>()
                subjects.forEach { subject ->
                    val prerequisites = repository.getPrerequisites(subject.code)
                    subjectsResponse.add(
                        SubjectsFromTermResponse(
                            subject.code,
                            subject.name,
                            subject.uv,
                            subject.order,
                            subject.estimateGrade,
                            prerequisites.map { it.prerequisiteCode }
                        )
                    )
                }
                _pensum.add(
                    IdealTermResponse(
                        cycle.name,
                        cycle.cycleType,
                        cycle.orderValue,
                        subjectsResponse
                    )
                )

            }
            pensumStatus.value = PensumState.DONE
        }
    }

    private suspend fun getPensumFromServer(){
        try {
            val token =  repository.getToken()
            if (token.isNotEmpty()){
                _pensum = repository.getPensum(token).toMutableList()
                pensumStatus.value = PensumState.DONE
                val cycles = mutableListOf<CycleEntity>()
                _pensum.forEach {
                    cycles.add(CycleEntity(it.orderValue, it.name, it.orderValue, it.cycleType))
                    val subjects = mutableListOf<Subject>()
                    it.subjects.forEach { subject ->
                        subjects.add(Subject(subject.code, subject.name, subject.correlative, subject.uv, subject.estimateGrade, it.orderValue))
                        val prerequisite = mutableListOf<Prerequisite>()
                        subject.prerequisiteID?.forEach { pre ->
                            prerequisite.add(Prerequisite(subjectCode =  subject.code, prerequisiteCode =  pre))
                        }
                        repository.savePrerequisites(prerequisite)
                    }
                    repository.saveSubjects(subjects)
                }
                repository.saveCycles(cycles)
            }
        } catch (e: Exception){
            pensumStatus.value = PensumState.ERROR
        }
    }

    fun updateSubject(currSubject: Subject, grade: Float) {
        viewModelScope.launch {
            repository.updateSubject(currSubject.code, grade)
        }
    }
}