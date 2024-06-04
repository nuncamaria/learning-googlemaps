package com.nuncamaria.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun FloatingButton(
    icon: ImageVector,
    label: String,
    maxWith: Boolean = false,
    onClick: () -> Unit
) {
    Button(
        modifier = if (maxWith) Modifier.fillMaxWidth() else Modifier.wrapContentWidth(),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = com.nuncamaria.ui.theme.AppColor.Primary,
            contentColor = Color.Black
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(com.nuncamaria.ui.theme.Spacing.xs)
        ) {
            Icon(imageVector = icon, contentDescription = null)
            Text(text = label)
        }
    }
}