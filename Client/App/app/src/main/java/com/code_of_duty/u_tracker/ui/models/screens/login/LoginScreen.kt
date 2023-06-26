package com.code_of_duty.u_tracker.ui.models.screens.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.MaterialTheme as MaterialTheme3
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.code_of_duty.u_tracker.ui.components.login.LoginForm
import com.code_of_duty.u_tracker.ui.components.login.LoginHeader
import com.code_of_duty.u_tracker.ui.graphs.AuthScreen
import com.code_of_duty.u_tracker.ui.graphs.Graph
import com.code_of_duty.u_tracker.ui.theme.Typography
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LoginScreen(loginViewModel: LoginViewModel = hiltViewModel(), navController: NavController){
    LoginContent(
        loginViewModel = loginViewModel,
        onClick = {
            navController.navigate(Graph.HOME){
                popUpTo(Graph.AUTHENTICATION){
                    inclusive = true
                }
            }
        },
        onSignUpClick = {
            navController.navigate(AuthScreen.SignUp.route)
        },
        onForgotClick = {
            navController.navigate(AuthScreen.Forgot.route)
        }
    )
}

@Composable
fun LoginContent(
    loginViewModel: LoginViewModel,
    onClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginHeader()
        LoginForm(loginViewModel = loginViewModel, onClick = onClick)
        Text(
            modifier = Modifier.clickable { onSignUpClick() },
            text = "Crear Cuenta",
            style = Typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme3.colorScheme.onSurfaceVariant
        )
        Row {
            Text(
                text = "Olvidaste tu contraseña?",
                fontSize = MaterialTheme.typography.body1.fontSize,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme3.colorScheme.onSurface
            )
            Text(
                modifier = Modifier.clickable { onForgotClick() },
                text = "Recuperar",
                fontSize = MaterialTheme.typography.body1.fontSize,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme3.colorScheme.onSurfaceVariant
            )
        }
    }
}