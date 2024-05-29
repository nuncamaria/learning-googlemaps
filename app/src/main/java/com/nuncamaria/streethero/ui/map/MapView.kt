package com.nuncamaria.streethero.ui.map

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.nuncamaria.streethero.R
import com.nuncamaria.streethero.domain.model.PlaceModel
import com.nuncamaria.streethero.ui.AppState
import com.nuncamaria.streethero.ui.components.MapLocationBottomSheet
import com.nuncamaria.streethero.ui.navigation.SurveyView
import com.nuncamaria.streethero.ui.theme.AppColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapView(appState: AppState) {
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
        Box(modifier = Modifier.padding(innerPadding)) {
            EmbeddedMap()
        }
    }
}

@Composable
private fun EmbeddedMap() {
    val ctx = LocalContext.current

    val place = PlaceModel(
        country = "Spain",
        latLng = LatLng(40.463667, -3.74922),
        address = LatLng(40.463667, -3.74922)
    )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(place.latLng, 15f)
    }

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

    val circleCenter by remember { mutableStateOf(place.latLng) }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = properties,
        uiSettings = uiSettings,
        onMyLocationButtonClick = { true }
    ) {
        Circle(
            center = circleCenter,
            fillColor = Color.LightGray,
            strokeColor = Color.Blue,
            radius = 200.0,
        )

        Marker(
            contentDescription = place.country,
            state = MarkerState(position = place.latLng),
            draggable = true,
            tag = place,
            title = place.country
        )
    }
}