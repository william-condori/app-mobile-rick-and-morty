package com.willydev.rickandmortyapp.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.willydev.rickandmortyapp.ui.theme.GlassBackground
import com.willydev.rickandmortyapp.ui.theme.GlassBorder
import com.willydev.rickandmortyapp.ui.theme.PortalGreen
import com.willydev.rickandmortyapp.ui.theme.SpaceDark


@Composable
fun SciFiBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(SpaceDark)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Drawing stylized portals (ellipses with radial gradients)
            val portalSize = size.width * 0.8f

            // Top right portal
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(PortalGreen.copy(alpha = 0.4f), Color.Transparent),
                    center = Offset(size.width, 0f),
                    radius = portalSize
                ),
                center = Offset(size.width, 0f),
                radius = portalSize
            )

            // Mid left portal
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(PortalGreen.copy(alpha = 0.3f), Color.Transparent),
                    center = Offset(0f, size.height * 0.4f),
                    radius = portalSize * 0.7f
                ),
                center = Offset(0f, size.height * 0.4f),
                radius = portalSize * 0.7f
            )

            // Bottom right portal
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(PortalGreen.copy(alpha = 0.5f), Color.Transparent),
                    center = Offset(size.width, size.height),
                    radius = portalSize * 1.2f
                ),
                center = Offset(size.width, size.height),
                radius = portalSize * 1.2f
            )

            // Adding some star-like dots
            val starColor = Color.White.copy(alpha = 0.3f)
            repeat(50) {
                drawCircle(
                    color = starColor,
                    radius = 2f,
                    center = Offset(
                        x = (0..size.width.toInt()).random().toFloat(),
                        y = (0..size.height.toInt()).random().toFloat()
                    )
                )
            }
        }

        content()
    }
}

@Composable
fun GlassmorphismCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(GlassBackground)
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(GlassBorder, GlassBorder.copy(alpha = 0.1f))
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(1.dp) // Glow effect simulation
    ) {
        Box(
            modifier = Modifier
                .padding(12.dp)
        ) {
            content()
        }
    }
}

@Composable
fun SciFiTopBar(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.statusBars)
            .height(64.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Rick and Morty",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold,
            color = PortalGreen
        )
    }
}
