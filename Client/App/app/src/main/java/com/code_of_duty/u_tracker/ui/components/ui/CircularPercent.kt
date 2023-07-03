package com.code_of_duty.u_tracker.ui.components.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme as MaterialTheme3
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.code_of_duty.u_tracker.ui.theme.UTrackerTheme

@Composable
fun PartialCircle(
    completed: Float,
    total: Float,
    cum: Float,
    scale: Float = 1f
) {
    val transition = remember { Animatable(initialValue = 0f) }

    LaunchedEffect(completed) {
        transition.animateTo(
            targetValue = completed,
            animationSpec = tween(durationMillis = 2000)
        )
    }
    val sweepAngle = (transition.value / total) * 360f
    val colorArc = if (completed == total)
        MaterialTheme3.colorScheme.secondary
    else
        MaterialTheme3.colorScheme.primary
    val fontColor = MaterialTheme3.colorScheme.onSurface

    val stroke = 30f
    Box(modifier = Modifier
        .size(150.dp)
        .padding(8.dp)
        .scale(scale)
    ){
        Canvas(modifier = Modifier.fillMaxSize()
            ) {
            drawArc(
                color = colorArc,
                startAngle = 90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = stroke)
            )

            drawIntoCanvas { canvas ->
                val paintValue = Paint().asFrameworkPaint()
                paintValue.textSize = 30.sp.toPx()
                paintValue.color = fontColor.toArgb()
                paintValue.textAlign = android.graphics.Paint.Align.CENTER
                canvas.nativeCanvas.drawText(
                    cum.toString(),
                    size.width / 2,
                    size.height / 2 + 10,
                    paintValue
                )

                val paint = Paint().asFrameworkPaint()
                paint.textSize = 12.sp.toPx()
                paint.color = fontColor.toArgb()
                paint.textAlign = android.graphics.Paint.Align.CENTER
                canvas.nativeCanvas.drawText(
                    "CUM",
                    size.width / 2,
                    size.height / 2 + 70,
                    paint
                )
            }
        }
    }
}



@Preview
@Composable
fun PreviewPartialCircle(){
    UTrackerTheme() {
        PartialCircle(completed = 50f, total = 10f, cum = 7.8f)
    }
}