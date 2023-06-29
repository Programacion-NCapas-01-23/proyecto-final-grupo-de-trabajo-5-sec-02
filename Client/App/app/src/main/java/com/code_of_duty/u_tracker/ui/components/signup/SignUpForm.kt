@file:OptIn(ExperimentalMaterial3Api::class)

package com.code_of_duty.u_tracker.ui.components.signup

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import com.code_of_duty.u_tracker.enums.SignUpStatus
import com.code_of_duty.u_tracker.ui.components.ui.BottomSheet
import com.code_of_duty.u_tracker.ui.components.ui.CenteredExposedDropdown
import com.code_of_duty.u_tracker.ui.components.ui.CustomButton
import com.code_of_duty.u_tracker.ui.components.ui.DialogAlert
import com.code_of_duty.u_tracker.ui.components.ui.EditTextField
import com.code_of_duty.u_tracker.ui.components.ui.FormsCard
import com.code_of_duty.u_tracker.ui.components.ui.KeyboardType
import com.code_of_duty.u_tracker.ui.models.ExposedDropdownModel
import com.code_of_duty.u_tracker.ui.models.Provisional
import com.code_of_duty.u_tracker.ui.models.TextFieldModel
import com.code_of_duty.u_tracker.ui.models.careers
import com.code_of_duty.u_tracker.ui.models.faculties
import com.code_of_duty.u_tracker.ui.screens.signup.SignUpViewModel
import com.code_of_duty.u_tracker.ui.theme.UTrackerTheme

@Composable
fun SignUpForm (
    signUpViewModel: SignUpViewModel,
    onClick: () -> Unit,
) {

    signUpViewModel.loadSelectsData()

    val error = remember { mutableStateOf(false) }
    val loading = remember { mutableStateOf(false) }

    val newPassword = remember { mutableStateOf(TextFieldModel()) }
    val confirmPassword = remember { mutableStateOf(TextFieldModel()) }

    val careerList = remember {mutableStateOf(signUpViewModel.getCareersList())}

    val enableStateCareer = remember { mutableStateOf(false) }

    LaunchedEffect(signUpViewModel.getSignup().value) {
        when(signUpViewModel.getSignup().value){
            SignUpStatus.SIGNUP_SUCCESS -> {
                onClick()
            }
            SignUpStatus.SIGNUP_FAILED -> {
                error.value = true
                loading.value = false
                signUpViewModel.setSignup(SignUpStatus.NONE)
            }
            else -> {}
        }
    }

    //check if the password is valid
    LaunchedEffect(newPassword.value.text.value) {
        newPassword.value.apply {
            isError = !text.value.matches(Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"))
            supportText = if (isError) "La contraseña debe tener al menos 8 caracteres, 1 mayuscula, 1 minuscula, 1 numero y un caracter especial" else ""
        }
    }

    LaunchedEffect(confirmPassword.value.text.value) {
        confirmPassword.value.apply {
            isError = newPassword.value.text.value != text.value
            supportText = if (isError) "Las contraseñas no coinciden" else ""
        }
    }

    //Enable carreer dropdown
    LaunchedEffect(signUpViewModel.getFacultyId().value) {
        signUpViewModel.setDegreeId("")
        signUpViewModel.setDegreeText("")
        if (signUpViewModel.getFacultyId().value != "") {
            enableStateCareer.value = true;
            careerList.value = signUpViewModel.filterCareers();
        }
    }


    UTrackerTheme() {
        FormsCard(
            title = "Crea tu cuenta de U-Tracker",
            editFields = listOf(
                {
                    EditTextField(
                        label = "Ingresa tu carnet",
                        value = signUpViewModel.getCode(),
                        type = KeyboardType.Number
                    )
                },
                {
                    EditTextField(
                        label = "Ingresa un nombre de usuario",
                        value = signUpViewModel.getUsername(),
                        type = KeyboardType.Text
                    )
                },
                {
                    EditTextField(
                        label = "Ingresa tu correo electrónico",
                        value = signUpViewModel.getEmail(),
                        type = KeyboardType.Email
                    )
                },
                {
                    EditTextField(
                        label = "Ingresa una contraseña",
                        value = signUpViewModel.getPassword(),
                        type = KeyboardType.Password,
                        isError = newPassword.value.isError,
                        supportText = newPassword.value.supportText
                    )
                },
                {
                    EditTextField(
                        label = "Confirma tu contraseña",
                        value = signUpViewModel.getConfirm(),
                        type = KeyboardType.Password,
                        isError = confirmPassword.value.isError,
                        supportText = confirmPassword.value.supportText
                    )
                },
                {
                    //For BottomSheet
                    val openBottomSheet = rememberSaveable { mutableStateOf(false) }
                    val scope = rememberCoroutineScope()
                    val bottomSheetState = rememberModalBottomSheetState()
                    val openButtonLabel = "Elige facultad y carrera"
                    val closeButtonLabel = "Volver al registro"

                    //For ExposeDropdown Carreer
                    val labelCareer = "Elige tu carrera"
                    val selectedIdCareer = signUpViewModel.getDegreeId()
                    val selectedNameCareer = signUpViewModel.getDegreeText()

                    //For ExposeDropdown Faculty
                    val labelFaculty = "Elige tu facultad"
                    val selectedIdFaculty = signUpViewModel.getFacultyId()
                    val selectedNameFaculty = signUpViewModel.getFacultyText()

                    BottomSheet(
                        openButtonLabel = openButtonLabel,
                        closeButtonLabel = closeButtonLabel,
                        bottomSheetState = bottomSheetState,
                        scope = scope,
                        editFields = listOf(
                            {
                                CenteredExposedDropdown(
                                    label = labelFaculty,
                                    options = signUpViewModel.getFacultiesList(),
                                    selectedIdValue = selectedIdFaculty,
                                    selectedNameValue = selectedNameFaculty,
                                    optionNameProvider = {option -> option.name},
                                    optionIdProvider = {option -> option.id},
                                )
                            },

                            {
                                CenteredExposedDropdown(
                                    label = labelCareer,
                                    options = careerList.value,
                                    selectedIdValue = selectedIdCareer,
                                    selectedNameValue = selectedNameCareer,
                                    optionNameProvider = {option -> option.name},
                                    optionIdProvider = {option -> option.id},
                                    enableState = enableStateCareer,
                                )
                            }
                        ),
                        openBottomSheet = openBottomSheet
                    )
                },
                {
                    CustomButton(text = "Registrarse", loading = loading.value) {
                        loading.value = true
                        signUpViewModel.signUp()
                    }
                }
            ),
            onClick = {}
        )

        if (error.value) {
            DialogAlert(
                title = "Error",
                message = "Campos incorrectos",
                onConfirm = {
                    error.value = false
                },
                needCancel = false
            )
        }
    }
}