package com.code_of_duty.u_tracker.ui.components.profile

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.code_of_duty.u_tracker.ui.theme.UTrackerTheme


@Composable
fun ProfileCard(
    markCard: MutableState<Boolean> = mutableStateOf(false),
    myModifier: Modifier = Modifier,
    code: String = "00401722",
    username: String = "username",
    career: String = "Arquitectura",
    pensum: String = "2023",
    image: String,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (!markCard.value) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        modifier = myModifier.width(360.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .build(),
                contentDescription = "Profile pic",
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(CircleShape).size(112.dp)
            )
            Text(
                text = username,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 24.sp
            )
            Divider(
                modifier = Modifier.padding(4.dp),
                startIndent = 8.dp,
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = buildAnnotatedString {
                    append("Carrera: ")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(career)
                    }
                },
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = buildAnnotatedString {
                    append("Plan: ")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(pensum)
                    }
                },
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = buildAnnotatedString {
                    append("Carnet: ")
                    withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                        append(code)
                    }
                },
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview
@Composable
fun ProfileCardPreview() {
    UTrackerTheme {
        ProfileCard(
            code = "00401722",
            career = "Arquitectura",
            pensum = "2019",
            username = "edwin",
            image = ""
        )
    }
}