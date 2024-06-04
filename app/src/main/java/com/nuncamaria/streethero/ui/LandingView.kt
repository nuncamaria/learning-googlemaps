package com.nuncamaria.streethero.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Report
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.nuncamaria.streethero.R
import com.nuncamaria.ui.components.FloatingButton
import com.nuncamaria.ui.components.InfoCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingView(onClickContinue: () -> Unit) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        floatingActionButton = {
            FloatingButton(
                icon = Icons.Default.Report,
                label = "Hacer reporte"
            ) {
                onClickContinue()
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        containerColor = com.nuncamaria.ui.theme.AppColor.Background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = com.nuncamaria.ui.theme.Spacing.md),
            verticalArrangement = Arrangement.spacedBy(com.nuncamaria.ui.theme.Spacing.md)
        ) {
            Column(
                modifier = Modifier.padding(vertical = com.nuncamaria.ui.theme.Spacing.md),
                verticalArrangement = Arrangement.spacedBy(com.nuncamaria.ui.theme.Spacing.s)
            ) {
                Text(
                    text = "Tu voz para una vía pública mejor.",
                    style = com.nuncamaria.ui.theme.Typography.headlineLarge
                )
                Text(
                    text = "Con Street Hero, puedes reportar problemas en la vía pública como baches, aceras dañadas o señales de tráfico defectuosas de manera rápida y sencilla.",
                    style = com.nuncamaria.ui.theme.Typography.bodyMedium
                )
            }

            InfoCard(
                image = painterResource(id = R.drawable.img_landing_location),
                title = stringResource(id = R.string.landingview_infocard_location_title),
                description = stringResource(id = R.string.landingview_infocard_location_description),
            )

            InfoCard(
                image = painterResource(id = R.drawable.img_landing_uploadphoto),
                title = "Sube una foto",
                description = "Una imagen vale más que mil palabras. ¡Captura el problema con tu cámara!",
                inverted = true
            )

            InfoCard(
                image = painterResource(id = R.drawable.img_landing_writedescription),
                title = "Describe la incidencia",
                description = "Tu voz importa. Cuéntanos los detalles para que podamos abordar el problema de manera efectiva."
            )
        }
    }
}