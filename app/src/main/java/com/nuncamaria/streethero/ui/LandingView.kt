package com.nuncamaria.streethero.ui

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Report
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.nuncamaria.streethero.LocationManager
import com.nuncamaria.streethero.R
import com.nuncamaria.streethero.ui.components.FloatingButton
import com.nuncamaria.streethero.ui.navigation.MapView
import com.nuncamaria.streethero.ui.theme.AppColor
import com.nuncamaria.streethero.ui.theme.Spacing
import com.nuncamaria.streethero.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingView(appState: AppState) {
    val ctx = LocalContext.current

    val permissionGranted =
        remember { mutableStateOf(LocationManager.areLocationPermissionsGranted(ctx)) }
    val showAlertDialog = remember { mutableStateOf(false) }

    val permissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) ||
                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                appState.navController.navigate(MapView.route) {
                    launchSingleTop = true
                }
            }

            else -> {
                showAlertDialog.value = true
            }
        }
    }

    if (showAlertDialog.value) {
        AlertDialog(
            onDismissRequest = { showAlertDialog.value = false },
            confirmButton = {
                Button(onClick = {
                    permissionsLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                    showAlertDialog.value = false
                }) {
                    Text(text = "Accept")
                }
            },
            text = {
                Text(text = "It's needed to accept the location permissions to use the app.")
            }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        floatingActionButton = {
            FloatingButton(
                icon = Icons.Default.Report,
                label = "Hacer reporte"
            ) {
                permissionsLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        containerColor = AppColor.Background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            Column(
                modifier = Modifier.padding(vertical = Spacing.md),
                verticalArrangement = Arrangement.spacedBy(Spacing.s)
            ) {
                Text(
                    text = "Tu voz para una vía pública mejor.",
                    style = Typography.headlineLarge
                )
                Text(
                    text = "Con Street Hero, puedes reportar problemas en la vía pública como baches, aceras dañadas o señales de tráfico defectuosas de manera rápida y sencilla.",
                    style = Typography.bodyMedium
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

@Composable
private fun InfoCard(
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
                    //     .background(brush = glassRadialGradient, shape = RoundedCornerShape(Spacing.md))
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