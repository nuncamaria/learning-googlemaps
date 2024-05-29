package com.nuncamaria.streethero.ui.navigation

interface AppDestination {
    val route: String
}

object LandingView : AppDestination {
    override val route = "landing"
}

object MapView : AppDestination {
    override val route = "map"
}

object SurveyView : AppDestination {
    override val route = "survey"
}