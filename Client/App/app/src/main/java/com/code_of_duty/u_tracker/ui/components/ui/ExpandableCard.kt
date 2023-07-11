package com.code_of_duty.u_tracker.ui.components.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.code_of_duty.u_tracker.ui.theme.Typography
import com.code_of_duty.u_tracker.ui.theme.UTrackerTheme

@Composable
fun ExpandableCard(
    title: String,
    onAction: () -> Unit,
    info: List<@Composable () -> Unit>,
) {
    val isExpanded = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 64.dp)
    ) {
        Column(
            modifier = Modifier
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .clickable { isExpanded.value = !isExpanded.value },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                ),
                content = {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = title,
                            modifier = Modifier,
                            style = Typography.titleSmall,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        FAB(
                            onClick = onAction,
                            shape = RoundedCornerShape(50.dp),
                            background = MaterialTheme.colorScheme.primary,
                        ){
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Add",
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }

                    }
                }
            )
            if(isExpanded.value) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    info.forEach { it() }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun ExpandableCardPreview() {
    UTrackerTheme {
        ExpandableCard(
            title = "Title",
            onAction = {},
            info = listOf(
                { Text(text = "Info 1") },
                { Text(text = "Info 2") },
                { Text(text = "Info 3") },
            )
        )
    }
}