package com.code_of_duty.u_tracker.ui.components.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme as MaterialTheme3
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.code_of_duty.u_tracker.ui.theme.UTrackerTheme
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun CustomButton(
    text: String,
    loading: Boolean = false,
    onClick: () -> Unit
){
    val buttonColors = MaterialTheme3.colorScheme.onPrimary
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        colors = buttonColors(
            contentColor = buttonColors,
        )
    ) {

        if (loading){
            CircularProgressIndicator(
                modifier = Modifier
                    .size(24.dp),
                color = buttonColors
            )
        }else{
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}
@Preview
@Composable
fun ButtonLoadingPreview(){
    val loading = remember { mutableStateOf(false) }
    UTrackerTheme {
        CustomButton(text = "Button", loading = loading.value){
            loading.value = !loading.value
        }
    }
}