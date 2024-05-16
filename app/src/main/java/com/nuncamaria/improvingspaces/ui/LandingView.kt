package com.nuncamaria.improvingspaces.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.Report
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import com.nuncamaria.improvingspaces.R
import com.nuncamaria.improvingspaces.ui.theme.AppColor
import com.nuncamaria.improvingspaces.ui.theme.Spacing
import com.nuncamaria.improvingspaces.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingView(appState: AppState) {
    val ctx = LocalContext.current

    val fineLocationPermissionGranted = ContextCompat.checkSelfPermission(
        ctx,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    val coarseLocationPermissionGranted = ContextCompat.checkSelfPermission(
        ctx,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    val permissionGranted =
        remember { mutableStateOf(fineLocationPermissionGranted || coarseLocationPermissionGranted) }
    val showAlertDialog = remember { mutableStateOf(false) }

    val permissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) ||
                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                appState.navController.navigate("map") {
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
            Button(
                onClick = {
                    permissionsLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        )
                    )
                },
                colors = ButtonDefaults.elevatedButtonColors()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Spacing.xs)
                ) {
                    Icon(imageVector = Icons.Default.Report, contentDescription = "Report")
                    Text(text = "Report")
                }
            }
        }, floatingActionButtonPosition = FabPosition.Center, containerColor = AppColor.Background
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

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.outlinedCardColors()
            ) {
                Row(
                    modifier = Modifier.padding(Spacing.s),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.s)
                ) {
                    Icon(imageVector = Icons.Default.LocationCity, contentDescription = null)

                    Column {
                        Text(
                            text = "Selecciona el lugar de la incidencia",
                            style = Typography.titleMedium
                        )
                        Text(
                            text = "¡Ponte en el mapa! Selecciona el punto exacto donde necesitamos tu ayuda para mejorar nuestras calles.",
                            style = Typography.bodyMedium
                        )
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(), colors = CardDefaults.outlinedCardColors()
            ) {
                Row(
                    modifier = Modifier.padding(Spacing.s),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.s)
                ) {
                    Icon(imageVector = Icons.Default.Photo, contentDescription = null)

                    Column {
                        Text(
                            text = "Sube una foto",
                            style = Typography.titleMedium
                        )
                        Text(
                            text = "Una imagen vale más que mil palabras. ¡Captura el problema con tu cámara y compártelo con nosotros!",
                            style = Typography.bodyMedium
                        )
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(), colors = CardDefaults.outlinedCardColors()
            ) {
                Row(
                    modifier = Modifier.padding(Spacing.s),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.s)
                ) {
                    Icon(imageVector = Icons.Default.Description, contentDescription = null)

                    Column {
                        Text(
                            text = "Describe la incidencia",
                            style = Typography.titleMedium
                        )
                        Text(
                            text = "Tu voz importa. Cuéntanos los detalles para que podamos abordar el problema de manera efectiva.",
                            style = Typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}