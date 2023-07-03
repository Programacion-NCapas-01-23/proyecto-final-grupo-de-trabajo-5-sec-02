package com.code_of_duty.u_tracker.ui.screens.cum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CumViewModel @Inject constructor() : ViewModel() {
    var completed = 0f
    fun loadCompleted() {
        viewModelScope.launch {
            for (i in 0..100) {
                completed = i.toFloat()
                delay(100)
            }
        }
    }
}