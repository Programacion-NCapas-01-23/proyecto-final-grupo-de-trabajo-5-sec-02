package com.code_of_duty.u_tracker.ui.components.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme as MaterialTheme3
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.code_of_duty.u_tracker.R
import com.code_of_duty.u_tracker.ui.theme.Typography
import androidx.compose.ui.text.font.FontWeight

@Composable
fun LoginHeader () {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Text(
            text = "U-Tracker",
            style = Typography.titleLarge,
            color = MaterialTheme3.colorScheme.onSurfaceVariant,
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 8.dp)
        )
        val logo = if (isSystemInDarkTheme()) {
            painterResource(id = R.drawable.logo_white)
        }else{
            painterResource(id = R.drawable.logo_black)
        }
        Image(
            painter = logo,
            contentDescription = "U-Tracker Logo",
            modifier = Modifier
                .padding(start = 8.dp)
                .size(150.dp)
        )
    }
}

 @Preview
@Composable
fun LoginHeaderPreview () {
    LoginHeader()
}