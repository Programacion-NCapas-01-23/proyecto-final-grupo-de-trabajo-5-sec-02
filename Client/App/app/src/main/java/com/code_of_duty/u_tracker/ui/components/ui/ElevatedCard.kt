@file:OptIn(ExperimentalMaterial3Api::class)

package com.code_of_duty.u_tracker.ui.components.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
fun CustomELevatedCard(
    title: String,
    buttonText: String,
    editFields: List<@Composable () -> Unit>,
    onButtonClick: () -> Unit,
    ) {
    Column(modifier = Modifier.fillMaxWidth().wrapContentSize(Alignment.Center).padding(vertical = 16.dp)) {
        ElevatedCard(
            modifier = Modifier
                .size(width = 350.dp, height = 380.dp),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
            content = {
                // Content of the card
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .wrapContentSize(Alignment.Center)) {
                    Row(horizontalArrangement = Arrangement.Center,modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)) {
                        Text(text = title, style = TextStyle(
                            fontSize = Typography.headlineSmall.fontSize,
                            lineHeight = Typography.headlineSmall.lineHeight,
                            fontWeight = Typography.headlineSmall.fontWeight,
                            letterSpacing = Typography.headlineSmall.letterSpacing,
                            color = MaterialTheme.colorScheme.primary,
                        ))
                    }
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)) {
                        editFields.forEach { editField ->
                            editField()
                        }
                    }
                    Button(onClick = { onButtonClick() }) {
                        Text(buttonText)
                    }
                }
            }
        )
    }

}

@Preview
@Composable

fun CustomELevatedCardPreview() {

    var subjectsTerm = mutableListOf<TermSubjectsData>()

    subjectsTerm = subjectTermDummy

    UTrackerTheme() {
        CustomELevatedCard(
            title = "CICLO I",
            buttonText = "Ver",
            editFields = listOf { ListItemTerm(subjectsTerm, { it.subject }, { it.grade }) },
            onButtonClick = {}
        )
    }

}