package com.code_of_duty.u_tracker.ui.screens.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.code_of_duty.u_tracker.ui.components.signup.SignUpForm
import com.code_of_duty.u_tracker.ui.graphs.Graph
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SignUpScreen(signUpViewModel: SignUpViewModel = hiltViewModel(), navController: NavController) {
    SignUpContent(
        signUpViewModel = signUpViewModel,
        onClick = {
            navController.navigate(Graph.AUTHENTICATION){
                popUpTo(Graph.AUTHENTICATION){
                    inclusive = true
                }
            }
        }
    )
}

@Composable
fun SignUpContent(
    signUpViewModel: SignUpViewModel,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SignUpForm(signUpViewModel = signUpViewModel, onClick = onClick)
    }
}