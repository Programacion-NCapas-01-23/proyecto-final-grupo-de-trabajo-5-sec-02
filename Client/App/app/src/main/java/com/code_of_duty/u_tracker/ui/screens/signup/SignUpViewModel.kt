package com.code_of_duty.u_tracker.ui.screens.signup

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.code_of_duty.u_tracker.data.repositories.SignUpRepository
import com.code_of_duty.u_tracker.enums.SignUpStatus
import com.code_of_duty.u_tracker.ui.models.ExposedDropdownModel
import com.code_of_duty.u_tracker.ui.models.Provisional
import com.code_of_duty.u_tracker.ui.models.careers
import com.code_of_duty.u_tracker.ui.models.faculties
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpRepository: SignUpRepository
) : ViewModel() {

    private var code: MutableState<String> = mutableStateOf("")
    private var username: MutableState<String> = mutableStateOf("")
    private var email: MutableState<String> = mutableStateOf("")
    private var password: MutableState<String> = mutableStateOf("")
    private var confirm: MutableState<String> = mutableStateOf("")
    private var faculty: MutableState<ExposedDropdownModel> = mutableStateOf(ExposedDropdownModel())
    private var degree: MutableState<ExposedDropdownModel> = mutableStateOf(ExposedDropdownModel())
    private var signup: MutableState<SignUpStatus> = mutableStateOf(SignUpStatus.NONE)
    private var careersListAll = mutableListOf<Provisional>()
    private var careersList = mutableListOf<Provisional>()
    private var facultiesList = mutableListOf<Provisional>()
    fun loadSelectsData() {
        viewModelScope.launch { /*TODO: Use API*/
            facultiesList = faculties
            careersListAll = careers
        }
    }
    fun filterCareers() = careersListAll.filter { it.faculty == getFacultyId().value  }.toMutableList()

    fun getFacultiesList(): MutableList<Provisional> {
        return facultiesList
    }
    fun getCareersList(): MutableList<Provisional> {
        return careersList
    }

    fun setCareersList(careersList: MutableList<Provisional>) {
        this.careersList = careersList
    }

    fun getCode(): MutableState<String> {
        return code
    }

    fun setCode(code: String) {
        this.code.value = code
    }

    fun getUsername(): MutableState<String> {
        return username
    }

    fun setUsername(username: String) {
        this.username.value = username
    }

    fun getEmail(): MutableState<String> {
        return email
    }

    fun setEmail(email: String) {
        this.email.value = email
    }

    fun getPassword(): MutableState<String> {
        return password
    }

    fun setPassword(password: String) {
        this.password.value = password
    }

    fun getConfirm(): MutableState<String> {
        return confirm
    }

    fun setConfirm(confirm: String) {
        this.confirm.value = confirm
    }

    fun getFacultyId(): MutableState<String> {
        return faculty.value.selectedIdValue
    }

    fun setFacultyId(facultyId: String) {
        this.faculty.value.selectedIdValue.value = facultyId
    }

    fun getFacultyText(): MutableState<String> {
        return faculty.value.selectedTextValue
    }


    fun getDegreeId(): MutableState<String> {
        return degree.value.selectedIdValue
    }

    fun setDegreeId(degreeId: String) {
        this.degree.value.selectedIdValue.value = degreeId
    }

    fun getDegreeText(): MutableState<String> {
        return degree.value.selectedTextValue
    }

    fun setDegreeText(degreeText: String) {
        this.degree.value.selectedTextValue.value = degreeText
    }

    fun getSignup(): MutableState<SignUpStatus> {
        return signup
    }

    fun setSignup(status: SignUpStatus) {
        signup.value = status
    }

    fun signUp() {
        viewModelScope.launch {
            try {
                val signUpResponse = signUpRepository.signup(code.value, username.value, email.value, password.value, degree.value.selectedIdValue.value)
                signUpResponse.message.let {
                    signup.value = SignUpStatus.SIGNUP_SUCCESS
                }
            } catch (e: Exception) {
                signup.value = SignUpStatus.SIGNUP_FAILED
            }
        }
    }
}