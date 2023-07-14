package com.code_of_duty.u_tracker.ui.screens.pensum

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code_of_duty.u_tracker.data.database.entities.Grade
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

    private fun getForDB(){
        viewModelScope.launch {
            val cycles = repository.getCycles()
            if (cycles.isEmpty()){
                pensumStatus.value = PensumState.ERROR
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

    fun getPensum() {
        viewModelScope.launch {
            try {
                val token = repository.getToken()
                if (token.isNotEmpty()) {
                    _pensum = repository.getPensum(token).toMutableList()
                    val existingCycles = repository.getCycles() // Obtener todos los ciclos existentes de la base de datos

                    _pensum.forEach { newCycle ->
                        val existingCycle = existingCycles.find { it.orderValue == newCycle.orderValue }
                        if (existingCycle != null) {
                            // El ciclo ya existe, actualizar sus datos
                            existingCycle.name = newCycle.name
                            existingCycle.cycleType = newCycle.cycleType
                            repository.updateCycle(existingCycle)
                        } else {
                            // El ciclo no existe, insertarlo en la base de datos
                            repository.insertCycle(newCycle)
                        }

                        newCycle.subjects.forEach { newSubject ->
                            val existingSubject = repository.getSubjectByCode(newSubject.code)
                            if (existingSubject != null) {
                                // El tema ya existe, actualizar sus datos
                                existingSubject.name = newSubject.name
                                existingSubject.order = newSubject.correlative
                                existingSubject.uv = newSubject.uv
                                existingSubject.cycle = newCycle.orderValue
                                repository.updateSubject(existingSubject)
                            } else {
                                // El tema no existe, insertarlo en la base de datos
                                repository.insertSubject(newSubject, newCycle.orderValue)
                            }

                            repository.deletePrerequisite(newSubject.code)
                            val newPrequesites = mutableListOf<Prerequisite>()
                            newSubject.prerequisiteID?.forEach { newPrerequisiteCode ->
                                newPrequesites.add(Prerequisite(subjectCode = newSubject.code, prerequisiteCode =  newPrerequisiteCode))
                            }
                        }
                    }

                    pensumStatus.value = PensumState.DONE
                }
            } catch (e: Exception) {
                Log.e("PensumViewModel", e.toString())
                getForDB()
            }
        }
    }


    fun updateSubject(currSubject: Subject, grade: Float) {
        viewModelScope.launch {
            //TODO: Update subject grade in server
            repository.updateSubjectGrade(currSubject.code, grade)
        }
    }

    fun isPassed(code: String): Boolean {
        var grade = Grade("",0f,false)
        viewModelScope.launch {
            try {
                grade = repository.getGrade(code)
            } catch (e: Exception) {
                Log.e("PensumViewModel", e.toString())
            }
        }
        return grade.passed
    }
}