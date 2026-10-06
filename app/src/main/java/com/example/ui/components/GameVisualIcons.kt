package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * High-quality procedural game icons that completely replace emojis across the entire UI.
 */

@Composable
fun GoldAuraIcon(modifier: Modifier = Modifier, size: Dp = 20.dp) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val center = Offset(w / 2f, h / 2f)
        val radius = w / 2f

        // Outer rim
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color(0xFFFFEA00), Color(0xFFFF8F00)),
                center = center,
                radius = radius
            ),
            radius = radius
        )
        drawCircle(
            color = Color(0xFFFFA000),
            radius = radius,
            style = Stroke(width = w * 0.08f)
        )

        // Inner coin core
        drawCircle(
            color = Color(0xFFFFD54F),
            radius = radius * 0.75f
        )

        // Lightning bolt icon
        val boltPath = Path().apply {
            moveTo(w * 0.52f, h * 0.20f)
            lineTo(w * 0.35f, h * 0.52f)
            lineTo(w * 0.50f, h * 0.52f)
            lineTo(w * 0.44f, h * 0.82f)
            lineTo(w * 0.68f, h * 0.45f)
            lineTo(w * 0.52f, h * 0.45f)
            close()
        }
        drawPath(boltPath, color = Color(0xFFD84315), style = Fill)
    }
}

@Composable
fun GemDiamondIcon(modifier: Modifier = Modifier, size: Dp = 20.dp) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        val gemPath = Path().apply {
            moveTo(w * 0.25f, h * 0.20f)
            lineTo(w * 0.75f, h * 0.20f)
            lineTo(w * 0.92f, h * 0.45f)
            lineTo(w * 0.50f, h * 0.88f)
            lineTo(w * 0.08f, h * 0.45f)
            close()
        }

        drawPath(
            gemPath,
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF80DEEA), Color(0xFF00B0FF), Color(0xFF0091EA))
            )
        )
        drawPath(gemPath, color = Color(0xFFE0F7FA), style = Stroke(width = w * 0.06f))

        // Center table facet line
        val facetPath = Path().apply {
            moveTo(w * 0.25f, h * 0.20f)
            lineTo(w * 0.38f, h * 0.45f)
            lineTo(w * 0.62f, h * 0.45f)
            lineTo(w * 0.75f, h * 0.20f)
        }
        drawPath(facetPath, color = Color(0xFFB2EBF2), style = Stroke(width = w * 0.04f))
    }
}

@Composable
fun BrawlTicketIcon(modifier: Modifier = Modifier, size: Dp = 20.dp) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        // Ticket body with notched corners
        val path = Path().apply {
            moveTo(w * 0.15f, h * 0.20f)
            lineTo(w * 0.85f, h * 0.20f)
            lineTo(w * 0.85f, h * 0.40f)
            arcTo(
                rect = androidx.compose.ui.geometry.Rect(w * 0.75f, h * 0.40f, w * 0.95f, h * 0.60f),
                startAngleDegrees = -90f,
                sweepAngleDegrees = -180f,
                forceMoveTo = false
            )
            lineTo(w * 0.85f, h * 0.80f)
            lineTo(w * 0.15f, h * 0.80f)
            lineTo(w * 0.15f, h * 0.60f)
            arcTo(
                rect = androidx.compose.ui.geometry.Rect(w * 0.05f, h * 0.40f, w * 0.25f, h * 0.60f),
                startAngleDegrees = 90f,
                sweepAngleDegrees = -180f,
                forceMoveTo = false
            )
            close()
        }

        drawPath(
            path,
            brush = Brush.horizontalGradient(
                colors = listOf(Color(0xFFFFD600), Color(0xFFFF9100))
            )
        )
        drawPath(path, color = Color(0xFFFF6D00), style = Stroke(width = w * 0.06f))

        // Star in center
        val starCenter = Offset(w / 2f, h / 2f)
        drawCircle(color = Color.White, radius = w * 0.14f, center = starCenter)
        drawCircle(color = Color(0xFFE65100), radius = w * 0.07f, center = starCenter)
    }
}

@Composable
fun TrophyCupIcon(modifier: Modifier = Modifier, size: Dp = 20.dp) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        // Cup bowl
        val cupPath = Path().apply {
            moveTo(w * 0.22f, h * 0.15f)
            lineTo(w * 0.78f, h * 0.15f)
            lineTo(w * 0.72f, h * 0.55f)
            arcTo(
                rect = androidx.compose.ui.geometry.Rect(w * 0.28f, h * 0.40f, w * 0.72f, h * 0.65f),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 180f,
                forceMoveTo = false
            )
            lineTo(w * 0.28f, h * 0.15f)
            close()
        }
        drawPath(
            cupPath,
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFFFFEA00), Color(0xFFFFB300))
            )
        )
        drawPath(cupPath, color = Color(0xFFFF8F00), style = Stroke(width = w * 0.05f))

        // Stem & Base
        drawRect(
            color = Color(0xFFFF8F00),
            topLeft = Offset(w * 0.44f, h * 0.65f),
            size = Size(w * 0.12f, h * 0.15f)
        )
        drawRoundRect(
            color = Color(0xFFFF6F00),
            topLeft = Offset(w * 0.25f, h * 0.78f),
            size = Size(w * 0.50f, h * 0.12f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(w * 0.04f, w * 0.04f)
        )
    }
}

@Composable
fun BanyaBroomIcon(modifier: Modifier = Modifier, size: Dp = 20.dp) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height

        // Handle
        drawLine(
            color = Color(0xFF8D6E63),
            start = Offset(w * 0.20f, h * 0.85f),
            end = Offset(w * 0.45f, h * 0.55f),
            strokeWidth = w * 0.10f
        )

        // Leaf fan
        val leafFan = Path().apply {
            moveTo(w * 0.42f, h * 0.58f)
            lineTo(w * 0.85f, h * 0.18f)
            lineTo(w * 0.70f, h * 0.10f)
            lineTo(w * 0.35f, h * 0.45f)
            close()
        }
        drawPath(
            leafFan,
            brush = Brush.linearGradient(
                colors = listOf(Color(0xFF4CAF50), Color(0xFF2E7D32)),
                start = Offset(w * 0.35f, h * 0.45f),
                end = Offset(w * 0.85f, h * 0.15f)
            )
        )
        drawCircle(color = Color(0xFF81C784), radius = w * 0.16f, center = Offset(w * 0.65f, h * 0.30f))
        drawCircle(color = Color(0xFF66BB6A), radius = w * 0.14f, center = Offset(w * 0.50f, h * 0.38f))
    }
}
