package com.code_of_duty.u_tracker.ui.models

sealed class AuthNavItems(
    val route: String,
    val name: String
    ) {
    object Login: AuthNavItems(
        name="Inicio de sesión", route = "login"
    )
    object SignUp: AuthNavItems(
        name="Registro", route = "signup"
    )
    object Forgot: AuthNavItems(
        name ="Recuperar contraseña", route = "forgot"
    )
}