package com.code_of_duty.u_tracker.ui.components.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FAB(
    onClick: () -> Unit,
    shape: RoundedCornerShape = RoundedCornerShape(15.dp),
    background: Color = MaterialTheme.colorScheme.surface,
    icon: @Composable () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 16.dp).fillMaxHeight()
    ) {
        FloatingActionButton(
            onClick = onClick,
            content = icon,
            shape = shape,
            backgroundColor = background,
            modifier = Modifier.wrapContentSize(align = Alignment.BottomEnd)
        )
    }
}

@Preview
@Composable
fun FABPreview() {
    FAB(
        onClick = {},
        icon = {Icon(
            imageVector = Icons.Filled.Edit,
            contentDescription = "Add",
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary,
        )}
    )
}