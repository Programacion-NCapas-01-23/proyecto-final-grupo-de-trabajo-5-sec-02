package com.code_of_duty.u_tracker.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.code_of_duty.u_tracker.R
import com.code_of_duty.u_tracker.ui.graphs.AuthScreen.Forgot.route
import com.code_of_duty.u_tracker.ui.graphs.Graph
import com.code_of_duty.u_tracker.ui.models.NavItems
import com.code_of_duty.u_tracker.ui.theme.Typography

@Composable
fun DropDownMenu(expanded: Boolean, onDismiss: ()-> Unit,navController: NavController) {

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismiss()},
        modifier = Modifier.width(140.dp)) {
        DropdownMenuItem(onClick = { /*TODO*/ }) {
            androidx.compose.material.Icon(painter = painterResource(id = R.drawable.dark_mode), contentDescription = "Dark Mode" )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Modo Oscuro", style = TextStyle(fontSize = Typography.labelMedium.fontSize)
            )
        }
        DropdownMenuItem(onClick = {
            onDismiss()
            navController.navigate(NavItems.Logout.route)
        }) {
            androidx.compose.material.Icon(painter = painterResource(id = R.drawable.logout), contentDescription = "Log Out" )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Cerrar sesión", style = TextStyle(fontSize = Typography.labelMedium.fontSize))
        }
    }

}