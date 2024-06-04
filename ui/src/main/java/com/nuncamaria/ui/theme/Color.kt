package com.nuncamaria.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


object AppColor {

    val Primary = Color(0xFFACFB0C)
    val Background = Color(0xFFF2F7F1)
}

val glassRadialGradient = object : ShaderBrush() {
    override fun createShader(size: Size): Shader {
        val maxWidth = size.width
        val maxHeight = size.height
        val minDimension = minOf(maxHeight, maxWidth)

        return RadialGradientShader(
            colors = listOf(
                Color(0xFFDFEBC4).copy(alpha = 0.3F),
                Color.White,
                Color(0xFFDFEBC4).copy(alpha = 0.2F),
            ),
            center = Offset(maxWidth, .4f),
            radius = minDimension / .4f,
            colorStops = listOf(.2f, .6f, .8f)
        )
    }
}