package com.code_of_duty.u_tracker.ui.components.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.code_of_duty.u_tracker.ui.models.TermSubjectsData
import com.code_of_duty.u_tracker.ui.models.subjectTermDummy
import com.code_of_duty.u_tracker.ui.theme.Typography
import com.code_of_duty.u_tracker.ui.theme.UTrackerTheme

@Composable
fun<T: Any> ListItemTerm(
    subjectTerm: List<T>,
    subjectNameProvider: (T) -> String,
//    subjectGradeProvider: (T) -> String
) {
    Column(
    ) {
        subjectTerm.forEach {subject ->
            ListItem(
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                ),
                headlineContent = {
                    Row( verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 12.dp)
                    ) {
                        Text(
                            text = subjectNameProvider(subject),
                            style = TextStyle(
                                fontSize = Typography.bodyLarge.fontSize,
                                lineHeight = Typography.bodyLarge.lineHeight,
                                fontWeight = Typography.bodyLarge.fontWeight,
                                letterSpacing = Typography.bodyLarge.letterSpacing,
                                color = MaterialTheme.colorScheme.secondary,
                            ),
                            modifier = Modifier.weight(1f),
                        )
//                        Text(
//                            text = subjectGradeProvider(subject),
//                            style = TextStyle(
//                                fontSize = Typography.titleLarge.fontSize,
//                                lineHeight = Typography.titleSmall.lineHeight,
//                                fontWeight = Typography.titleSmall.fontWeight,
//                                letterSpacing = Typography.titleSmall.letterSpacing,
//                                color = MaterialTheme.colorScheme.primary,
//                            ),
//                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            if (subject != subjectTerm.last()) {Divider(modifier = Modifier.padding(horizontal = 24.dp),color = MaterialTheme.colorScheme.surfaceTint, thickness = 0.8.dp)}
        }
    }
}

@Preview
@Composable
fun ListItemTermPreview() {

    var subjectsTerm = mutableListOf<TermSubjectsData>()

    subjectsTerm = subjectTermDummy

    UTrackerTheme() {
        ListItemTerm(subjectsTerm, {it.subject})
    }
}