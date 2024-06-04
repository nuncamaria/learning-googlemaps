package com.nuncamaria.streethero

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nuncamaria.streethero.ui.LandingView
import com.nuncamaria.streethero.ui.map.MapView
import com.nuncamaria.streethero.ui.navigation.LandingView
import com.nuncamaria.streethero.ui.navigation.MapView
import com.nuncamaria.streethero.ui.navigation.SurveyView
import com.nuncamaria.streethero.ui.rememberAppState
import com.nuncamaria.streethero.ui.survey.SurveyView
import com.nuncamaria.streethero.ui.theme.ImprovingSpacesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val appState = rememberAppState()

            ImprovingSpacesTheme {
                NavHost(navController = appState.navController, startDestination = LandingView.route) {

                    composable(LandingView.route) {
                        LandingView {
                            appState.navController.navigate(MapView.route) {
                                launchSingleTop = true
                            }
                        }
                    }

                    composable(MapView.route) {
                        MapView(appState)
                    }

                    composable(SurveyView.route) {
                        SurveyView(appState)
                    }
                }
            }
        }
    }
}