package com.code_of_duty.u_tracker.ui.components.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.code_of_duty.u_tracker.R
import com.code_of_duty.u_tracker.ui.theme.Typography
import com.code_of_duty.u_tracker.ui.theme.UTrackerTheme

@Composable
fun CustomTab(
    selected: MutableState <Boolean> =  mutableStateOf(false),
    onClick: () -> Unit = {},
    icon: Int,
    title: String
) {
    LeadingIconTab(
        selected = selected.value,
        onClick = {
            onClick()
        },
        icon = {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Navigation Icon",
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        },
        text = {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = Typography.bodyLarge.fontSize,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        selectedContentColor = MaterialTheme.colorScheme.primaryContainer,
        unselectedContentColor = MaterialTheme.colorScheme.secondaryContainer,
    )
}

@Preview
@Composable
fun CustomTabPreview() {
    UTrackerTheme() {
        CustomTab(remember { mutableStateOf(true) } , {}, (R.drawable.check_circle), "Notas")
    }
}