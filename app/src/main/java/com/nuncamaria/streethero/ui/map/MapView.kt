package com.nuncamaria.streethero.ui.map

import android.Manifest
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.nuncamaria.streethero.R
import com.nuncamaria.streethero.ui.AppState
import com.nuncamaria.streethero.ui.components.MapLocationBottomSheet
import com.nuncamaria.streethero.ui.navigation.SurveyView
import com.nuncamaria.streethero.ui.theme.AppColor
import com.nuncamaria.streethero.ui.utils.centerOnLocation
import com.nuncamaria.streethero.ui.utils.hasLocationPermission

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MapView(appState: AppState, viewModel: MapViewModel = hiltViewModel()) {
    val ctx = LocalContext.current

    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MediumTopAppBar(
                title = { Text(text = stringResource(R.string.mapview_toolbar_title)) },
                navigationIcon = {
                    IconButton(onClick = { appState.navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.GLOBAL_go_back)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColor.Background
                )
            )
        },
        bottomBar = {
            MapLocationBottomSheet {
                appState.navController.navigate(SurveyView.route) {
                    launchSingleTop = true
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {

            LaunchedEffect(!ctx.hasLocationPermission()) {
                permissionState.launchMultiplePermissionRequest()
            }

            when {
                permissionState.allPermissionsGranted -> {
                    LaunchedEffect(Unit) {
                        viewModel.getLocation(PermissionEvent.Granted)
                    }
                }

                permissionState.shouldShowRationale -> {
                    RationaleAlert(onDismiss = { }) {
                        permissionState.launchMultiplePermissionRequest()
                    }
                }

                !permissionState.allPermissionsGranted && !permissionState.shouldShowRationale -> {
                    LaunchedEffect(Unit) {
                        viewModel.getLocation(PermissionEvent.Revoked)
                    }
                }
            }

            with(viewState) {

                when (this) {
                    ViewState.Idle -> {}

                    ViewState.Loading -> CircularProgressIndicator()

                    ViewState.RevokedPermissions -> RationaleAlert({}) {}

                    is ViewState.Success -> {
                        viewModel.userLocation.value =
                            LatLng(
                                this.location?.latitude ?: 0.0,
                                this.location?.longitude ?: 0.0
                            )

                        val cameraState = rememberCameraPositionState()

                        LaunchedEffect(key1 = viewModel.userLocation.value) {
                            cameraState.centerOnLocation(viewModel.userLocation.value)
                        }

                        EmbeddedMap(viewModel.userLocation.value, cameraState)
                    }
                }
            }
        }
    }
}

@Composable
private fun EmbeddedMap(currentPosition: LatLng, cameraState: CameraPositionState) {
    val uiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                myLocationButtonEnabled = true,
                zoomControlsEnabled = true
            )
        )
    }

    val properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }

    val circleCenter by remember { mutableStateOf(currentPosition) }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraState,
        properties = properties,
        uiSettings = uiSettings,
        onMyLocationButtonClick = { true }
    ) {
        Circle(
            center = circleCenter,
            fillColor = Color.LightGray,
            strokeColor = Color.Blue,
            strokeWidth = 2f,
            radius = 200.0,
            tag = currentPosition
        )

        MarkerInfoWindow(
            draggable = true,
            state = MarkerState(position = currentPosition),
            //     tag = viewModel.userPlace.value,
            //    title = viewModel.userPlace.value.country
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .border(
                        BorderStroke(1.dp, Color.Black),
                        RoundedCornerShape(10)
                    )
                    .clip(RoundedCornerShape(10))
                    .background(Color.White)
                    .padding(20.dp)
            ) {
                Text("$currentPosition", fontWeight = FontWeight.Bold, color = Color.Black)
                Text("$currentPosition", fontWeight = FontWeight.Medium, color = Color.Black)
            }
        }
    }
}

@Composable
fun RationaleAlert(onDismiss: () -> Unit, onConfirm: () -> Unit) {

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties()
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "We need location permissions to use this app")

                Spacer(modifier = Modifier.height(24.dp))

                TextButton(
                    onClick = {
                        onConfirm()
                        onDismiss()
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {

                    Text("OK")
                }
            }
        }
    }
}