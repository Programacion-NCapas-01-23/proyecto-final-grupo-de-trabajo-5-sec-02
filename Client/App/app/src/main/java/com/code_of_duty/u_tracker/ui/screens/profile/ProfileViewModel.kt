package com.code_of_duty.u_tracker.ui.screens.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code_of_duty.u_tracker.data.network.response.Degree
import com.code_of_duty.u_tracker.data.repositories.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository
) : ViewModel() {
    private val _code = MutableStateFlow("")
    private val _username = MutableStateFlow("")
    private val _image = MutableStateFlow("")
    private val _cum = MutableStateFlow(0.0f)
    private val _degree = MutableStateFlow<Degree?>(null)
    private val _approvedSubjects = MutableStateFlow(0)

    val code: StateFlow<String> = _code
    val username: StateFlow<String> = _username
    val image: StateFlow<String> = _image
    val cum: StateFlow<Float> = _cum
    val degree: StateFlow<Degree?> = _degree
    val approvedSubjects: StateFlow<Int> = _approvedSubjects

    fun getStudentInfo() {
        viewModelScope.launch {
            try {
                val token = profileRepository.getToken()
                if (token.isNotEmpty()) {
                    val profileResponse = profileRepository.getStudentInfo(token)
                    Log.e("Response", profileResponse.toString())
                    _code.value = profileResponse.code
                    _username.value = profileResponse.username
                    _image.value = profileResponse.image ?: ""
                    _cum.value = profileResponse.cum
                    _degree.value = profileResponse.degree
                    _approvedSubjects.value = profileResponse.approvedSubjects
                }
            } catch (e: Exception) {
                Log.e("Profile View Model", e.toString())
            }
        }
    }
}

