package com.zj.composedouban.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.noRippleClickable(onClick: ()->Unit): Modifier = this.then(
    Modifier.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick
    )
)

fun Modifier.customBorder(
    color: Color =Color.Green,
    width: Dp = 1.dp,
    cornerRadius: Dp = 0.dp
): Modifier = this.then(
    Modifier.drawBehind {
        val strokeWidth = width.toPx()
        val halfStrokeWidth = strokeWidth / 2
        drawRoundRect(
            color = color,
            size = size.copy(
                width = size.width - strokeWidth,
                height = size.height - strokeWidth
            ),
            topLeft = Offset(halfStrokeWidth, halfStrokeWidth),
            cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx()),
            style = Stroke(strokeWidth)
        )
    }
)
