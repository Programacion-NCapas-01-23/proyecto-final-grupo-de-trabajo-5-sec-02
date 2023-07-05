package com.code_of_duty.u_tracker.ui.screens.cum

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code_of_duty.u_tracker.data.repositories.CumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CumViewModel @Inject constructor(
    private val cumRepository: CumRepository
) : ViewModel() {
    val total: MutableState<Float> = mutableStateOf(0.0f)
    val completed: MutableState<Float> = mutableStateOf(0.0f)
    val cum: MutableState<Float> = mutableStateOf(0.0f)


    fun getTotal(): Float {
        viewModelScope.launch {
            val subjects = cumRepository.getTotal()
            total.value = subjects.size.toFloat()
        }
        return total.value
    }
    fun getCompleted(): Float {
        viewModelScope.launch {
            val grades = cumRepository.getCompleted()
            completed.value = grades.size.toFloat()
        }
        return completed.value
    }

    fun getCum(): Float {
        viewModelScope.launch {
            val subjects = cumRepository.getTotal()
            val grades = cumRepository.getCompleted()
            var sum = 0.0f
            var sumUv = 0.0f
            for(grade in grades){
                val subject = subjects.find { it.code == grade.code }
                sum += grade.grade * subject!!.uv
                sumUv += subject.uv
            }
            cum.value = sum/sumUv
        }
        return cum.value
    }
}