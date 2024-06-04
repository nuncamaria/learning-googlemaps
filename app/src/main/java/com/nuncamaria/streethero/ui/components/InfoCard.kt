package com.nuncamaria.streethero.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import com.nuncamaria.streethero.ui.theme.Spacing
import com.nuncamaria.streethero.ui.theme.Typography

@Composable
fun InfoCard(
    inverted: Boolean = false,
    title: String,
    description: String,
    image: Painter
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.outlinedCardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (inverted.not()) {
                Image(
                    modifier = Modifier
                        .heightIn(max = Spacing.mega)
                        .weight(1F),
                    painter = image,
                    contentDescription = null,
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier
                    .heightIn(min = Spacing.mega)
                    .padding(horizontal = Spacing.lg, vertical = Spacing.md)
                    .weight(2F),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = if (inverted.not()) Alignment.End else Alignment.Start
            ) {
                Text(
                    text = title,
                    textAlign = if (inverted.not()) TextAlign.End else TextAlign.Start,
                    style = Typography.titleMedium
                )
                Text(
                    text = description,
                    textAlign = if (inverted.not()) TextAlign.End else TextAlign.Start,
                    style = Typography.bodyMedium
                )
            }

            if (inverted) {
                Image(
                    modifier = Modifier
                        .heightIn(max = Spacing.mega)
                        .weight(1F),
                    painter = image,
                    contentDescription = null,
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}