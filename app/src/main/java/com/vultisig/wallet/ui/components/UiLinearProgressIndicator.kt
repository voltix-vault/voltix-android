package com.vultisig.wallet.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vultisig.wallet.ui.theme.Theme

@Composable
internal fun UiLinearProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Theme.colors.oxfordBlue400,
    indicatorBrush: Brush = Brush.horizontalGradient(
        colorStops = arrayOf(
            0.0f to Theme.colors.persianBlue600Main,
            progress to Theme.colors.turquoise600Main,
            1.0f to Theme.colors.turquoise600Main,
        ),
    ),
) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1000),
        label = "UiLinearProgressIndicator"
    )

    Canvas(
        modifier = modifier
            .height(10.dp)
            .fillMaxWidth()
    ) {
        val (width, height) = size
        val cornerRadius = CornerRadius(height / 2)
        drawRoundRect(
            color = backgroundColor,
            size = Size(width, height),
            cornerRadius = cornerRadius,
        )
        drawRoundRect(
            brush = indicatorBrush,
            size = Size(
                width * (animatedProgress.coerceAtLeast(0.05f)),
                height
            ),
            cornerRadius = cornerRadius,
        )
    }
}