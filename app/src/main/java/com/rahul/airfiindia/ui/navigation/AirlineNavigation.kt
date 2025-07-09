package com.rahul.airfiindia.ui.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rahul.airfiindia.ui.screen.AirlineDetailScreen
import com.rahul.airfiindia.ui.screen.AirlineListScreen

@Composable
fun AirlineNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "airline_list"
    ) {
        composable("airline_list") {
            AirlineListScreen(
                onAirlineClick = { airline ->
                    navController.navigate("airline_detail/${airline.id}")
                }
            )
        }

        composable("airline_detail/{airlineId}") { backStackEntry ->
            val airlineId = backStackEntry.arguments?.getString("airlineId") ?: ""
            AirlineDetailScreen(
                airlineId = airlineId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}