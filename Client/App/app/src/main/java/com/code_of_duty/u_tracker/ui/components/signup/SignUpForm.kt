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
import com.code_of_duty.u_tracker.ui.models.ProvisionalFaculty
import com.code_of_duty.u_tracker.ui.models.ProvisionalResponse
import com.code_of_duty.u_tracker.ui.screens.signup.SignUpViewModel
import com.code_of_duty.u_tracker.ui.theme.UTrackerTheme

@Composable
fun SignUpForm (
    signUpViewModel: SignUpViewModel,
    onClick: () -> Unit,
) {
    val error = remember { mutableStateOf(false) }
    val loading = remember { mutableStateOf(false) }

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
                    )
                },
                {
                    EditTextField(
                        label = "Confirma tu contraseña",
                        value = signUpViewModel.getConfirm(),
                        type = KeyboardType.Password
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
                    val careers = listOf(
                        ProvisionalResponse.Arquitecure,
                        ProvisionalResponse.CivilEngineering,
                        ProvisionalResponse.InformaticEngineering,
                        ProvisionalResponse.MechanicalEngineering,
                        ProvisionalResponse.ElectricalEngineering,
                        ProvisionalResponse.IndustrialEngineering,
                        ProvisionalResponse.EnergeticEngineering,
                    )

                    val enableStateCarrer = remember { mutableStateOf(true) }

                    //For ExposeDropdown Faculty
                    val labelFaculty = "Elige tu facultad"
                    val faculties = listOf(
                        ProvisionalFaculty.Communications,
                        ProvisionalFaculty.Economics,
                        ProvisionalFaculty.Engineering,
                        ProvisionalFaculty.Education,
                        ProvisionalFaculty.Humanities,
                        ProvisionalFaculty.Law
                    )
                    val enableStateFaculty = remember { mutableStateOf(false) }

                    BottomSheet(
                        openButtonLabel = openButtonLabel,
                        closeButtonLabel = closeButtonLabel,
                        bottomSheetState = bottomSheetState,
                        scope = scope,
                        editFields = listOf(
                            {
                                CenteredExposedDropdown(
                                label = labelFaculty,
                                options = faculties,
                                optionNameProvider = {option -> option.name},
                                optionIdProvider = {option -> option.id},
                                enableState = enableStateFaculty,
                                )
                            },

                            {
                                CenteredExposedDropdown(
                                    label = labelCareer,
                                    options = careers,
                                    optionNameProvider = {option -> option.name},
                                    optionIdProvider = {option -> option.id},
                                    enableState = enableStateCarrer,
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