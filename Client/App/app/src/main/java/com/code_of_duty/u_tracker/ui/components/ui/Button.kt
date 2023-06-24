package com.code_of_duty.u_tracker.ui.components.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.MaterialTheme as MaterialTheme3
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.code_of_duty.u_tracker.ui.theme.UTrackerTheme
import androidx.compose.material3.Button as Button3

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit
){
    UTrackerTheme {
        Button3(
            onClick = onClick,
            content = { Text(text = text) },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            colors = buttonColors(
                contentColor = MaterialTheme3.colorScheme.onPrimary
            )
        )
        
    }
}

@Preview
@Composable
fun ButtonPreview(){
    CustomButton(text = "Button", onClick = {})
}