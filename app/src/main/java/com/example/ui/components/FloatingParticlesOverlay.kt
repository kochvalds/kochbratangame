package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodel.ClickParticle
import kotlin.math.roundToInt

@Composable
fun FloatingParticlesOverlay(
    particles: List<ClickParticle>,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        particles.forEach { particle ->
            SingleFloatingParticle(particle = particle)
        }
    }
}

@Composable
private fun SingleFloatingParticle(particle: ClickParticle) {
    val offsetY = remember { Animatable(0f) }
    val alphaAnim = remember { Animatable(1f) }

    LaunchedEffect(particle.id) {
        offsetY.animateTo(
            targetValue = -90f,
            animationSpec = tween(durationMillis = 900, easing = LinearOutSlowInEasing)
        )
    }

    LaunchedEffect(particle.id) {
        alphaAnim.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 900, delayMillis = 200)
        )
    }

    Text(
        text = particle.text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Black,
        color = Color(particle.colorHex),
        modifier = Modifier
            .offset {
                IntOffset(
                    x = particle.x.roundToInt() + 80,
                    y = (particle.y + offsetY.value).roundToInt() + 180
                )
            }
            .alpha(alphaAnim.value)
    )
}
