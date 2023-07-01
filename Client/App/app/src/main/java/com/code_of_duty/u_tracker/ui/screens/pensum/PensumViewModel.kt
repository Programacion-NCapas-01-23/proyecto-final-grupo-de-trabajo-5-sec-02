package com.code_of_duty.u_tracker.ui.screens.pensum

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code_of_duty.u_tracker.data.repositories.PensumRepository
import com.code_of_duty.u_tracker.enums.PensumState
import com.code_of_duty.u_tracker.data.network.response.IdealTermResponse
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
            try {
                val token =  repository.getToken()
                if (token.isNotEmpty()){
                    _pensum = repository.getPensum(token).toMutableList()
                    Log.d("PensumViewModel", "Pensum: $_pensum")
                    pensumStatus.value = PensumState.DONE
                }
            } catch (e: Exception){
                Log.e("PensumViewModel", "error: ${e.message}")
                pensumStatus.value = PensumState.ERROR
            }
        }
    }
}