package com.code_of_duty.u_tracker.ui.components.term

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.code_of_duty.u_tracker.ui.theme.Typography
import com.code_of_duty.u_tracker.ui.theme.UTrackerTheme

@Composable
fun ListItemTerm(
    subjectTerm: String,
    grade: String,
) {
    Column() {
        ListItem(
            headlineContent = { Text("One line list item with 24x24 icon") },
            leadingContent = {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Localized description",
                )
            }
        )
        Divider(color = MaterialTheme.colorScheme.onPrimaryContainer, thickness = 1.dp)
    }
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 24.dp)
//    ) {
//        Text(
//            text = subjectTerm,
//            style = TextStyle(
//                    fontSize = Typography.titleMedium.fontSize,
//                    color = MaterialTheme.colorScheme.onPrimaryContainer,
//            ),
//            maxLines = 1,
//            modifier = Modifier.weight(1f)
//        )
//        Text(
//            text = grade,
//            style = TextStyle(
//                    fontSize = Typography.titleMedium.fontSize,
//                    color = MaterialTheme.colorScheme.onPrimaryContainer,
//            ),
//        )
//    }
}

@Preview
@Composable
fun ListItemTermPreview() {

    UTrackerTheme() {
        ListItemTerm(
            subjectTerm = "Term 1",
            grade = "8"
        )
    }
}