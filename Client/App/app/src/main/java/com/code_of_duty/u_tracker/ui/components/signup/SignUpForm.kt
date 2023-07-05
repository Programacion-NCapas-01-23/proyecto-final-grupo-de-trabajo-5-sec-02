@file:OptIn(ExperimentalMaterial3Api::class)

package com.code_of_duty.u_tracker.ui.components.signup

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.code_of_duty.u_tracker.R
import com.code_of_duty.u_tracker.enums.DegreeStatus
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

    // LaunchedEffect para validar la nueva contraseña
    LaunchedEffect(newPassword.value.text.value) {
        newPassword.value.apply {
            if (text.value != "") {
                isError.value = !text.value.matches(Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"))
                supportText.value = if (isError.value) "La contraseña debe tener al menos 8 caracteres, 1 mayúscula, 1 minúscula, 1 número y un carácter especial" else ""

                if (!isError.value) {
                    signUpViewModel.getPassword().value = text.value
                    isError.value = false
                    supportText.value = ""
                }
            }
        }
    }

    // LaunchedEffect para validar la confirmación de contraseña
    LaunchedEffect(confirmPassword.value.text.value, newPassword.value.text.value) {

        val newPasswordText = newPassword.value.text.value
        val confirmPasswordText = confirmPassword.value.text.value

        confirmPassword.value.apply {
            if (text.value != "") {
                val passwordsMatch = newPasswordText == confirmPasswordText
                isError.value = !passwordsMatch

                Log.d("ConfirmPassword", "isError: $isError")
                supportText.value = if (isError.value) "Las contraseñas no coinciden" else ""

                if (passwordsMatch) {
                    signUpViewModel.getConfirm().value = confirmPasswordText
                    signUpViewModel.getPassword().value = newPasswordText
                }
            } else {
                isError.value = false
                supportText.value = ""
            }
        }
    }

    //Enable carreer dropdown
    LaunchedEffect(signUpViewModel.getFacultyId().value) {
        signUpViewModel.setDegreeId("")
        signUpViewModel.setDegreeText("")
        signUpViewModel.setCareerStatus(DegreeStatus.NONE)
        enableStateCareer.value = false

        if (signUpViewModel.getFacultyId().value != "" ) {
            careerList.value = signUpViewModel.filterCareers()
            if(signUpViewModel.getCareerStatus().value == DegreeStatus.DEGREE_LOADED) {
                enableStateCareer.value = true
            }
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
                        type = KeyboardType.Number,
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.numeral),
                                contentDescription = "Carnet icon"
                            )
                        }
                    )
                },
                {
                    EditTextField(
                        label = "Ingresa un nombre de usuario",
                        value = signUpViewModel.getUsername(),
                        type = KeyboardType.Text,
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.account_circle),
                                contentDescription = "Username icon"
                            )
                        }
                    )
                },
                {
                    EditTextField(
                        label = "Ingresa tu correo electrónico",
                        value = signUpViewModel.getEmail(),
                        type = KeyboardType.Email,
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.contact_mail),
                                contentDescription = "Username icon"
                            )
                        }
                    )
                },
                {
                    EditTextField(
                        label = "Ingresa una contraseña",
                        value = newPassword.value.text,
                        type = KeyboardType.Password,
                        isError = newPassword.value.isError,
                        supportText = newPassword.value.supportText,
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.lock_person),
                                contentDescription = "Username icon"
                            )
                        }
                    )
                },
                {
                    EditTextField(
                        label = "Confirma tu contraseña",
                        value = confirmPassword.value.text,
                        type = KeyboardType.Password,
                        isError = confirmPassword.value.isError,
                        supportText = confirmPassword.value.supportText,
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.check_circle),
                                contentDescription = "Username icon"
                            )
                        }
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
                                    width = 280.dp,
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
                                    width = 280.dp,
                                    )
                            }
                        ),
                        openBottomSheet = openBottomSheet
                    )
                },
                {
                    CustomButton(text = "Registrarse", loading = loading.value) {
                        //TODO: validate fields
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