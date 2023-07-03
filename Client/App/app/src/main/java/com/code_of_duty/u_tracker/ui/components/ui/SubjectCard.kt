package com.code_of_duty.u_tracker.ui.components.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme as MaterialTheme3
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.code_of_duty.u_tracker.ui.theme.UTrackerTheme

@Composable
fun SubjectCard(
    sort: Int = 0,
    subjectName: String = "Subject Name",
    prerequisites: List<Int>? = List(0) { 0 },
    uv: Int = 0,
    passed: Boolean = false,
) {
    val prerequisitesString = remember { if(prerequisites.isNullOrEmpty()){ "bachillerato" } else {
        prerequisites.joinToString(", ")
    }}
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme3.colorScheme.surfaceVariant,
            contentColor = MaterialTheme3.colorScheme.onSurfaceVariant,
            disabledContainerColor = MaterialTheme3.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme3.colorScheme.onSurfaceVariant,
        ),
        modifier = Modifier.width(180.dp)
    ){
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = buildAnnotatedString {
                    append("Orden: ")
                    withStyle(style = SpanStyle(color = MaterialTheme3.colorScheme.primary)) {
                        append(sort.toString())
                    }
                },
                    color = MaterialTheme3.colorScheme.onSurface
                )
                Checkbox(checked = passed, onCheckedChange = {}, enabled = false)
            }
            Text(
                text = subjectName,
                modifier = Modifier.padding(horizontal = 4.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme3.colorScheme.onSurface
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = buildAnnotatedString {
                    append("pre: ")
                    withStyle(style = SpanStyle(color = MaterialTheme3.colorScheme.primary)) {
                        append(prerequisitesString)
                    }
                },
                    color = MaterialTheme3.colorScheme.onSurface
                )
                Text(text = buildAnnotatedString {
                    append("UV's: ")
                    withStyle(style = SpanStyle(color = MaterialTheme3.colorScheme.primary)) {
                        append(uv.toString())
                    }
                },
                    color = MaterialTheme3.colorScheme.onSurface
                )
            }
        }
    }
}

@Preview
@Composable
fun SubjectCardPreview() {
    UTrackerTheme() {
        SubjectCard(
            sort = 1,
            subjectName = "Precalculo",
            uv = 4,
            passed = false,
        )
    }
}