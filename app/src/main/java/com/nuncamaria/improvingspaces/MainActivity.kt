package com.nuncamaria.improvingspaces

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nuncamaria.improvingspaces.ui.LandingView
import com.nuncamaria.improvingspaces.ui.MapView
import com.nuncamaria.improvingspaces.ui.rememberAppState
import com.nuncamaria.improvingspaces.ui.theme.ImprovingSpacesTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val appState = rememberAppState()

            ImprovingSpacesTheme {
                NavHost(navController = appState.navController, startDestination = "landing") {

                    composable("landing") {
                        LandingView(appState)
                    }

                    composable("map") {
                        MapView(appState)
                    }
                }
            }
        }
    }
}