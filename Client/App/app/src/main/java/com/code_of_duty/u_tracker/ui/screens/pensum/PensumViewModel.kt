package com.code_of_duty.u_tracker.ui.screens.pensum

import com.code_of_duty.u_tracker.data.database.entities.Cycle as CycleEntity
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code_of_duty.u_tracker.data.repositories.PensumRepository
import com.code_of_duty.u_tracker.enums.PensumState
import com.code_of_duty.u_tracker.ui.models.Cycle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PensumViewModel @Inject constructor(
    private val repository: PensumRepository
) : ViewModel(){
    //TODO: Implement PensumViewModel
    private var _pensum: MutableList<Cycle> = mutableListOf()
    private var pensumStatus: MutableState<PensumState> = mutableStateOf(PensumState.NONE)

    fun pensum() = _pensum
    fun pensumStatus() = pensumStatus

    fun getPensum(){
        viewModelScope.launch {
            try {
                val token =  repository.getToken()
                if (token.isNotEmpty()){
                    _pensum = repository.getPensum(token).toMutableList()
                    pensumStatus.value = PensumState.DONE
                    val cycles = mutableListOf<CycleEntity>()
                    _pensum.forEach {
                        cycles.add(CycleEntity(it.orderValue, it.name, it.orderValue, it.cycleType))
                    }
                    repository.saveCycles(cycles)
                }
            } catch (e: Exception){
                pensumStatus.value = PensumState.ERROR
            }
        }
    }
}