package com.code_of_duty.u_tracker.ui.models.screens.forgotPassword.getVerificationToken

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.code_of_duty.u_tracker.ui.components.forgotPassword.changePassword.ChangePasswordCard
import com.code_of_duty.u_tracker.ui.components.forgotPassword.getVerificationToken.RequestTokenCard
import com.code_of_duty.u_tracker.ui.theme.Typography

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
        RequestTokenCard {

        }
        ChangePasswordCard {
            onContinue()
        }
    }
}