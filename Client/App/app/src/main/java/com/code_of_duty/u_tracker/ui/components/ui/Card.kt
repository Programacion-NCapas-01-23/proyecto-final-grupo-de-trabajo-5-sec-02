package com.code_of_duty.u_tracker.ui.components.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.MaterialTheme as MaterialTheme3
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.code_of_duty.u_tracker.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormsCard(
    title: String,
    subtitle: String? = null,
    editFields: List<@Composable () -> Unit>,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = onClick
            ),
        colors = cardColors(
            containerColor = MaterialTheme3.colorScheme.onSurfaceVariant
        ),
        content = {
            Text(
                text = title,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .padding(start = 16.dp)
                    .fillMaxWidth(),
                style = Typography.titleSmall,
                color = MaterialTheme3.colorScheme.onSurface
            )

            if (subtitle != null) {
                Text(
                    text = subtitle,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .fillMaxWidth(),
                    style = Typography.labelMedium,
                    color = MaterialTheme3.colorScheme.onSurface
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .wrapContentSize(Alignment.Center)
            ) {
                editFields.forEach { editField ->
                    editField()
                }
            }
        }
    )
}

@Preview
@Composable
fun FormsCardPreview() {
    val code = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    FormsCard(
        title = "login",
        editFields = listOf(
            {
                EditTextField(
                    label = "codigo",
                    value = code,
                    onValueChange = {code.value = it}
                )
            },
            {
                EditTextField(
                    label = "contraseña",
                    value = password,
                    onValueChange = {password.value = it},
                    isPassword = true
                )
            }
        ),
        onClick = {}
    )
}