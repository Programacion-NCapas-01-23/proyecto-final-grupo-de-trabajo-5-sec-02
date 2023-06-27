package com.code_of_duty.u_tracker.ui.screens.forgotPassword.getVerificationToken

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.code_of_duty.u_tracker.ui.components.forgotPassword.ChangePasswordCard

@Composable
fun GetVerificationTokenScreen (
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ChangePasswordCard {
            onContinue()
        }
    }
}